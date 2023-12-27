package com.lpbank.balancefluctuation.schedule;

import com.lpbank.balancefluctuation.model.entityDatasource1.ActbDailyLog;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReportDebit {
    private final ReportDebitService reportDebitService;

    @Scheduled(fixedDelay = 100)
    public void report(){
        ActbDailyLog a = reportDebitService.test();
        System.out.println(a.toString());
    }


}
