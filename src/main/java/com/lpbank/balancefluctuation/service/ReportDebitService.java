package com.lpbank.balancefluctuation.service;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;

import java.util.List;

public interface ReportDebitService {
    List<VTSAccount> findAllVTSAccount();
    void getTransFromCore();
    void sendDataTransVTS();
}
