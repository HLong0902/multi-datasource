package com.lpbank.balancefluctuation.controller;

import com.lpbank.balancefluctuation.module.respone.ResTransfer;
import com.lpbank.balancefluctuation.schedule.ScheduleBalanceFluctuation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance-fluctuation/")
public class BalanceFluctuationController {
//    private static final Logger logger = LoggerFactory.getLogger(BalanceFluctuationController.class);
//
//    private final ScheduleBalanceFluctuation scheduleBalanceFluctuation;
//
//    public BalanceFluctuationController(ScheduleBalanceFluctuation scheduleBalanceFluctuation) {
//        this.scheduleBalanceFluctuation = scheduleBalanceFluctuation;
//    }
//
//    @GetMapping(value = "test")
//    public Object test(){
//       // scheduleBalanceFluctuation.scheduleDelayTask();
//        return new ResTransfer("Thành công");
//    }
}
