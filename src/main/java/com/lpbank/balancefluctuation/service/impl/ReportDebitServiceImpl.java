package com.lpbank.balancefluctuation.service.impl;

import com.lpbank.balancefluctuation.common.SendStatus;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.feign.CallAPITokenVST;
import com.lpbank.balancefluctuation.model.dto.ActbDailyLogDTO;
import com.lpbank.balancefluctuation.model.dto.Trans;
import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.JobConfig;
import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;
import com.lpbank.balancefluctuation.module.request.*;
import com.lpbank.balancefluctuation.module.respone.ResTransactionSync;
import com.lpbank.balancefluctuation.repository.datasource1.ActbDailyLogRepo;
import com.lpbank.balancefluctuation.repository.datasource2.SendDataTransRepo;
import com.lpbank.balancefluctuation.repository.datasource2.VTSAccountRepo;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.*;
import java.util.logging.XMLFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportDebitServiceImpl implements ReportDebitService {

    private final SendDataTransRepo sendDataTransRepo;
    private final ActbDailyLogRepo actbDailyLogRepo;
    private final VTSAccountRepo vtsAccountRepo;
    private final CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST;
    private final CallAPITokenVST callAPITokenVST;
    private final RedisTemplate<String, String> redisTemplate;


    @Value("${batch.size}")
    private Integer batchSize;

    @Value("${retry-num:5000}")
    private Integer retryNum;

    @Value("${spring.profiles.active}")
    private String configSpring;

    @Override
    @SneakyThrows
    public Runnable sendDataToVTS(JobConfig jobConfig) {
        return () -> {
            List<SendDataTransactionViettelStore> sendDataTransVTSs = sendDataTransRepo.findAllToSend(batchSize, retryNum);
            sendDataTransVTSs.forEach(sendDataTransVTS -> {
                sendDataTransVTS.setStatus(SendStatus.SENDING.value());
                sendDataTransRepo.save(sendDataTransVTS);
            });
            Map<String, List<ActbDailyLogDTO>> dataSend = new HashMap<>();


            sendDataTransVTSs.forEach(sendDataTransVTS -> {
                Optional<ActbDailyLog> actbDailyLogOpt = actbDailyLogRepo.findById(sendDataTransVTS.getAcEntrySrNo());
                if (actbDailyLogOpt.isEmpty()) {
                    sendDataTransVTS.setStatus(SendStatus.ACTIVE.value());
                    sendDataTransVTS.setRetryNum(retryNum);
                    sendDataTransVTS.setMessageLog("No ACTB_DAILY_LOG found.");

                    sendDataTransRepo.save(sendDataTransVTS);
                    return;
                }

                ActbDailyLog actbDailyLog = actbDailyLogOpt.get();
                ActbDailyLogDTO actbDailyLogDTO = new ActbDailyLogDTO();
                BeanUtils.copyProperties(actbDailyLog, actbDailyLogDTO);

                actbDailyLogDTO.setSendDataVTS(sendDataTransVTS);

                if (dataSend.containsKey(actbDailyLog.getAcNo())) {
                    var temp = new ArrayList<>(dataSend.get(actbDailyLog.getAcNo()));
                    temp.add(actbDailyLogDTO);
                    dataSend.put(actbDailyLog.getAcNo(), temp);
                } else dataSend.put(actbDailyLog.getAcNo(), List.of(actbDailyLogDTO));
            });

            dataSend.forEach((acNo, actbDailyLogs) -> {
                try {
                    ResTransactionSync resTransactionSync = sendDataToVTS(acNo, actbDailyLogs, jobConfig);

                    String refDesc = resTransactionSync.getResCode().getRefDesc();
                    Pattern pattern = Pattern.compile("<tran><trnRefno>(.*?)</trnRefno><trnDT>(.*?)</trnDT><status>(.*?)</status></tran>");
                    Matcher matcher = pattern.matcher(refDesc);

                    while (matcher.find()) {
                        String trnRefno = matcher.group(1);
                        String status = matcher.group(3);

                        ActbDailyLogDTO actbDailyLogDTO = actbDailyLogs.stream().filter(a -> a.getTrnRefNo().equals(trnRefno)).findFirst().get();
                        SendDataTransactionViettelStore sendDataTransactionViettelStore = actbDailyLogDTO.getSendDataVTS();

                        Integer retryNum = sendDataTransactionViettelStore.getRetryNum() + 1;
                        sendDataTransactionViettelStore.setRetryNum(retryNum);

                        if (status.equals("F")) {
                            if (this.retryNum.equals(retryNum)) {
                                sendDataTransactionViettelStore.setStatus(SendStatus.FAIL.value());
                            } else sendDataTransactionViettelStore.setStatus(SendStatus.ACTIVE.value());
                            sendDataTransactionViettelStore.setMessageLog( resTransactionSync.getResCode().getErrorDesc());
                        } else {
                            sendDataTransactionViettelStore.setStatus(SendStatus.SENT.value());
                            sendDataTransactionViettelStore.setMessageLog("Send successfully.");
                        }
                        sendDataTransRepo.save(sendDataTransactionViettelStore);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    actbDailyLogs.forEach(actbDailyLogDTO -> {
                        SendDataTransactionViettelStore sendDataTransactionViettelStore = actbDailyLogDTO.getSendDataVTS();

                        Integer retryNum = sendDataTransactionViettelStore.getRetryNum() + 1;
                        sendDataTransactionViettelStore.setRetryNum(retryNum);

                        if (this.retryNum.equals(retryNum)) {
                            sendDataTransactionViettelStore.setStatus(SendStatus.FAIL.value());
                        } else sendDataTransactionViettelStore.setStatus(SendStatus.ACTIVE.value());
                        sendDataTransactionViettelStore.setMessageLog(e.getLocalizedMessage());

                        sendDataTransRepo.save(sendDataTransactionViettelStore);
                    });
                }
            });
        };
    }

    @SneakyThrows
    private ResTransactionSync sendDataToVTS(String acNo, List<ActbDailyLogDTO> actbDailyLogs, JobConfig jobConfig) {
        String token = redisTemplate.opsForValue().get(TokenServiceImpl.TOKEN_KEY_PREFIX);
        if (!StringUtils.hasText(token)) {
            token = getAuthToken();
        }

        HeaderReq h = new HeaderReq(String.valueOf(Math.random()), jobConfig.getService(), jobConfig.getOperation(), jobConfig.getUserId());
        ServiceB serviceB = new ServiceB(jobConfig.getServiceId(), jobConfig.getProductCode(), actbDailyLogs.get(0).getSendDataVTS().getSourceAcc(), null, jobConfig.getMerchantId());
        Partner partner = new Partner(null, null, null, jobConfig.getChanel());

        List<BillInfo> billInfos = new ArrayList<>();
        actbDailyLogs.forEach(actbDailyLog -> billInfos.add(new BillInfo(actbDailyLog.getSendDataVTS().getTrnDesc(), String.valueOf(actbDailyLog.getLcyAmount()), String.valueOf(actbDailyLog.getLcyAmount()), actbDailyLog.getTrnRefNo(), actbDailyLog.getTxnDtTime().toString())));

        if (billInfos.size() == 1) {
            serviceB.setReceiveAccount(acNo);
        }

        SettleBill settleBill = new SettleBill("N", actbDailyLogs.get(0).getSendDataVTS().getCustNo(), actbDailyLogs.get(0).getAcBranch(), null, serviceB, partner, billInfos);
        ReqTransactionSync transactionSync = new ReqTransactionSync(h, new BodyReq(settleBill));

        return callAPIBalanceFluctuationVST.transactionSync(token, transactionSync);
    }

    @Override
    public void getTransFromCore() {
        List<SendDataTransactionViettelStore> sendDataTransVTSs = new ArrayList<>();
        List<String> vtsAccounts = vtsAccountRepo.findAllByRecordStat("O").stream().map(VTSAccount::getAccountNo).toList();

        List<Object[]> datas = new ArrayList<>();
        if (configSpring.equals("dev")) {
            datas = actbDailyLogRepo.getTransFromCore();
        } else datas = actbDailyLogRepo.getTransFromDWH();
        datas.forEach(data -> {
            String acNo = String.valueOf(data[1]);
            if (vtsAccounts.contains(acNo) && !sendDataTransRepo.existsById(Long.parseLong(data[0].toString()))) {
                sendDataTransVTSs.add(SendDataTransactionViettelStore.builder()
                        .acEntrySrNo(Long.parseLong(data[0].toString()))
                        .status(SendStatus.ACTIVE.value())
                        .trnDesc(String.valueOf(data[2]))
                        .custNo(String.valueOf(data[3]))
                        .sourceAcc(String.valueOf(data[4]))
                        .retryNum(0)
                        .build());
            }
        });
        sendDataTransRepo.saveAll(sendDataTransVTSs);
    }

    public String getAuthToken() {
        ReqAuthen reqAuthen = new ReqAuthen();
        reqAuthen.setPassword("oV6/taBNIwtOPeAbBxZAUQVdmT6AYJyyk2XV9Fe5Dwy92wQnizIPs/nCk8Hfi41y4hrlgdxBLfkVw78SEcneLwyjaBy+u4KOmSNvDeDNAFujNPO6+5PzxAyiPKaCNfn5wmEV23s7Px74h6AzONasHo8PHALOngDCoWsCTF9RPkM=");
        reqAuthen.setUsername("135_LPB_DEV");
        String token = "";
        try {
            ResponseEntity<String> resAuthenJSON = callAPITokenVST.getAuthToken(reqAuthen);
            log.info("Token from VTS: {}", resAuthenJSON.getBody());
            if (Objects.nonNull(resAuthenJSON.getBody())) {
                token = Objects.requireNonNull(resAuthenJSON.getHeaders().get("Authorization")).get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return token;
    }
}
