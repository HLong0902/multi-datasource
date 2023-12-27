package com.lpbank.balancefluctuation.service.impl;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.SendDataTransactionViettelStore;
import com.lpbank.balancefluctuation.repository.datasource1.ActbDailyLogRepo;
import com.lpbank.balancefluctuation.repository.datasource2.SendDataTransRepo;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportDebitServiceImpl implements ReportDebitService {
    private final SendDataTransRepo sendDataTransRepo;
    private final ActbDailyLogRepo actbDailyLogRepo;

    public ActbDailyLog test(){
        SendDataTransactionViettelStore o = sendDataTransRepo.getById(1L);
        ActbDailyLog b = actbDailyLogRepo.getById(3091253174L);
        return b;
    }
}
