package com.bfly.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 计划任务器配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/27 13:44
 */
@Configuration
@EnableAsync
public class TaskSchedulConfig implements SchedulingConfigurer {

    /**
     * 任务执行器比如日志记录 在目标方法上加上注释@Async 自动使用
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 14:10
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("TaskExecutor-");
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler pool = new ThreadPoolTaskScheduler();
        pool.setPoolSize(10);
        pool.setThreadNamePrefix("TaskSchedul-");
        pool.setAwaitTerminationSeconds(60);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }
}
