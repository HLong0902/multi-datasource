package com.lpbank.balancefluctuation.schedule;

import com.lpbank.balancefluctuation.model.entityDatasource2.VTSAccount;
import com.lpbank.balancefluctuation.repository.datasource2.JobConfigRepo;
import com.lpbank.balancefluctuation.service.ReportDebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportDebit {
    private final ReportDebitService reportDebitService;
    private final TaskExecutor sendDataTask;
    private final JobConfigRepo jobConfigRepo;
    
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void getTransFromCore(){
        reportDebitService.getTransFromCore();
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void sendDataTransVTS(){
        var jobconfigs = jobConfigRepo.findAllByStatus(1);
        jobconfigs.forEach(jobConfig -> sendDataTask.execute(reportDebitService.sendDataToVTS(jobConfig)));
    }

}
