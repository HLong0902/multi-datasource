package com.lpbank.balancefluctuation.schedule;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpbank.balancefluctuation.service.impl.BalanceFluctuationServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class ScheduleBalanceFluctuation {

    private final  BalanceFluctuationServiceImpl balanceFluctuationService;

    public ScheduleBalanceFluctuation(BalanceFluctuationServiceImpl balanceFluctuationService) {
        this.balanceFluctuationService = balanceFluctuationService;
    }

    @Scheduled(fixedDelay = 60)
//    @Scheduled(cron = "0 0/5 * * * ?") // Chạy mỗi 5 phút
//    @Scheduled(cron = "0 * * * * ?") // Chạy mỗi 5 phút
   //@PostConstruct
    public void scheduleDelayTask() throws JsonProcessingException {
        balanceFluctuationService.reportDebit();
        System.out.println("done");
    }
}
