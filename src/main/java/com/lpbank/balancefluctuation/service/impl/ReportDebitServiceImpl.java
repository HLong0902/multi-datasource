package com.lpbank.balancefluctuation.service.impl;

import com.lpbank.balancefluctuation.common.SendStatus;
import com.lpbank.balancefluctuation.feign.CallAPIBalanceFluctuationVST;
import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.JobConfig;
import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;
import com.lpbank.balancefluctuation.module.request.*;
import com.lpbank.balancefluctuation.repository.datasource1.ActbDailyLogRepo;
import com.lpbank.balancefluctuation.repository.datasource2.SendDataTransRepo;
import com.lpbank.balancefluctuation.repository.datasource2.VTSAccountRepo;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportDebitServiceImpl implements ReportDebitService {

    private final SendDataTransRepo sendDataTransRepo;
    private final ActbDailyLogRepo actbDailyLogRepo;
    private final VTSAccountRepo vtsAccountRepo;
    private final CallAPIBalanceFluctuationVST callAPIBalanceFluctuationVST;

    @Value("${batch.size}")
    private Integer batchSize;

    @Value("${retry-num}")
    private Integer retryNum;

    @Value("${spring.profiles.active}")
    private String configSpring;

    @Override
    public Runnable sendDataToVTS(JobConfig jobConfig) {
        return () -> {
            List<SendDataTransactionViettelStore> sendDataTransVTSs = sendDataTransRepo.findAllToSend(batchSize, retryNum);
            sendDataTransVTSs.forEach(sendDataTransVTS -> {
                sendDataTransVTS.setStatus(SendStatus.SENDING.value());
                sendDataTransRepo.save(sendDataTransVTS);
            });
            Map<String, List<ActbDailyLog>> dataSend = new HashMap<>();


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
                if (dataSend.containsKey(actbDailyLog.getAcNo())) {
                    dataSend.get(actbDailyLog.getAcNo()).add(actbDailyLog);
                } else dataSend.put(actbDailyLog.getAcNo(), List.of(actbDailyLog));

                Integer retryNum = sendDataTransVTS.getRetryNum() + 1;
//             else {
//                ActbDailyLog actbDailyLog = actbDailyLogOpt.get();
//                sendDataTransVTS.setRetryNum(retryNum);
//                try {
//                    sendDataToVTS(actbDailyLog);
//                    sendDataTransVTS.setStatus(SendStatus.SENT.value());
//                    sendDataTransVTS.setMessageLog("Send successfully.");
//                    sendDataTransRepo.save(sendDataTransVTS);
//                } catch (Exception e) {
//                    if (retryNum.equals(this.retryNum)) {
//                        sendDataTransVTS.setStatus(SendStatus.FAIL.value());
//                    } else {
//                        sendDataTransVTS.setStatus(SendStatus.SENT.value());
//                    }
//
//                    sendDataTransVTS.setMessageLog("Send successfully.");
//                    sendDataTransRepo.save(sendDataTransVTS);
//                }
//            }
            });

            dataSend.forEach((acNo, actbDailyLogs) -> {
                sendDataToVTS(actbDailyLogs);
            });
        };
    }

    private void sendDataToVTS(List<ActbDailyLog> actbDailyLogs) {
        String msgid = String.valueOf(Math.random());
        try {
            Header h = new Header(String.valueOf(Math.random()), "PAYMENT_VTS", "REQUEST_TXN", "135_LPB_DE");
            ServiceB serviceB = new ServiceB("030005_36", "PAYMENT_VTS", "021638280001", null, "PAYMENT_VTS");
            Partner partner = new Partner(null, null, null, "J031");
            BillInfo billInfo = new BillInfo("825821-Begin97318End", "10101", "10101", "0011lz3232330001", "2023-09-08 00:00:00");
            List<BillInfo> billInfos = new ArrayList<>();
            billInfos.add(billInfo);
            SettleBill settleBill = new SettleBill("N", "00056248", "001", "Test Loi", serviceB, partner, billInfos);

            ReqTransactionSync transactionSync = new ReqTransactionSync(h, new Body(settleBill));


//            ResponseEntity<Map<String, Object>> reqTransactionSyncJSON = callAPIBalanceFluctuationVST.transactionSync(token, transactionSync);
//            String responseBody = reqTransactionSyncJSON.getBody().toString();
//            ObjectMapper objectMapper = new ObjectMapper();
//            ResTransactionSync resAuthen = objectMapper.readValue(responseBody, ResTransactionSync.class);
//            System.out.println(resAuthen.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getTransFromCore() {
        List<SendDataTransactionViettelStore> sendDataTransVTSs = new ArrayList<>();
        List<String> vtsAccounts = vtsAccountRepo.findAllByRecordStat("O").stream().map(VTSAccount::getAccountNo).toList();

        List<Object[]> datas = new ArrayList<>();
        if (configSpring.equals("dev")) {
            datas = actbDailyLogRepo.getTransFromCore(batchSize);
        } else datas = actbDailyLogRepo.getTransFromDWH(batchSize);
        datas.forEach(data -> {
            String acNo = String.valueOf(data[1]);
            if (vtsAccounts.contains(acNo) && !sendDataTransRepo.existsById(Long.parseLong(data[0].toString()))) {
                sendDataTransVTSs.add(SendDataTransactionViettelStore.builder()
                        .acEntrySrNo(Long.parseLong(data[0].toString()))
                        .status(SendStatus.ACTIVE.value())
                        .retryNum(0)
                        .build());
            }
        });
        sendDataTransRepo.saveAll(sendDataTransVTSs);
    }
}
