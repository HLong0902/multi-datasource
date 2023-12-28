package com.lpbank.balancefluctuation.service.impl;

import com.lpbank.balancefluctuation.common.SendStatus;
import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;
import com.lpbank.balancefluctuation.repository.datasource1.ActbDailyLogRepo;
import com.lpbank.balancefluctuation.repository.datasource2.SendDataTransRepo;
import com.lpbank.balancefluctuation.repository.datasource2.VTSAccountRepo;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportDebitServiceImpl implements ReportDebitService {

    private final SendDataTransRepo sendDataTransRepo;
    private final ActbDailyLogRepo actbDailyLogRepo;
    private final VTSAccountRepo vtsAccountRepo;

    @Value("${batch.size}")
    private Integer batchSize;

    @Value("${retry-num}")
    private Integer retryNum;

    @Override
    public void sendDataTransVTS(){
        List<SendDataTransactionViettelStore> sendDataTransVTSs = sendDataTransRepo.findAllToSend(batchSize, retryNum);
        sendDataTransVTSs.forEach(sendDataTransVTS -> {
            sendDataTransVTS.setStatus(SendStatus.SENDING.value());
            sendDataTransRepo.save(sendDataTransVTS);
        });


        sendDataTransVTSs.forEach(sendDataTransVTS -> {
            Optional<ActbDailyLog> actbDailyLogOpt = actbDailyLogRepo.findById(sendDataTransVTS.getAcEntrySrNo());
            Integer retryNum = sendDataTransVTS.getRetryNum() + 1;
            if (actbDailyLogOpt.isEmpty()) {
                sendDataTransVTS.setStatus(SendStatus.ACTIVE.value());
                sendDataTransVTS.setRetryNum(retryNum);
                sendDataTransVTS.setMessageLog("No ACTB_DAILY_LOG found.");

                sendDataTransRepo.save(sendDataTransVTS);
            } else {
                ActbDailyLog actbDailyLog = actbDailyLogOpt.get();
                sendDataTransVTS.setRetryNum(retryNum);
                try {
                    sendDataToVTS(actbDailyLog);
                    sendDataTransVTS.setStatus(SendStatus.SENT.value());
                    sendDataTransVTS.setMessageLog("Send successfully.");
                    sendDataTransRepo.save(sendDataTransVTS);
                } catch (Exception e) {
                    if (retryNum.equals(this.retryNum)) {
                        sendDataTransVTS.setStatus(SendStatus.FAIL.value());
                    } else {
                        sendDataTransVTS.setStatus(SendStatus.SENT.value());
                    }

                    sendDataTransVTS.setMessageLog("Send successfully.");
                    sendDataTransRepo.save(sendDataTransVTS);
                }
            }
        });
    }


    private void sendDataToVTS(ActbDailyLog actbDailyLog) {


    }

    @Override
    public List<VTSAccount> findAllVTSAccount() {
        return vtsAccountRepo.findAllByRecordStat("O");
    }

    @Override
    public void getTransFromCore() {
        List<SendDataTransactionViettelStore> sendDataTransVTSs = new ArrayList<>();
        List<String> vtsAccounts= vtsAccountRepo.findAllByRecordStat("O").stream().map(VTSAccount::getAccountNo).toList();
        List<Object[]> datas = actbDailyLogRepo.getTransFromCore(batchSize);
        datas.forEach(data -> {
            String acNo =  String.valueOf(data[1]);
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
