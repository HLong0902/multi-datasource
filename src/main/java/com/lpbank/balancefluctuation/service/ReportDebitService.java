package com.lpbank.balancefluctuation.service;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.model.entityDatasource2.JobConfig;
import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;

import java.util.List;

public interface ReportDebitService {
    Runnable sendDataToVTS(JobConfig jobConfig);
    void getTransFromCore();
}
