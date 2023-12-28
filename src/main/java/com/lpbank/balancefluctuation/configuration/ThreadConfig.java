package com.lpbank.balancefluctuation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {
    @Bean(name = "getTransFromCore")
    public TaskExecutor getTransFromCore() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("getTransFromCore");
        executor.initialize();

        return executor;
    }
    @Bean(name = "sendDataTask")
    public TaskExecutor sendDataTask() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("sendDataTask");
        executor.initialize();

        return executor;
    }
}
