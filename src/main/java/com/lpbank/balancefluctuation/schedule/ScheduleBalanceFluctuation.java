package com.lpbank.balancefluctuation.schedule;


import com.lpbank.balancefluctuation.service.BalanceFluctuationServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
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

    //@Scheduled(fixedDelay = 300000)
   // @Scheduled(cron = "0 0/5 * * * ?") // Chạy mỗi 5 phút
    //@Scheduled(cron = "0 * * * * ?") // Chạy mỗi 5 phút
   @PostConstruct
    public void scheduleDelayTask() {
        System.out.println("5 phút 1 lần!" + new Date());
        balanceFluctuationService.reportDebit();
        System.out.println("done");
    }
}
