package com.bfly.core.config;

import com.bfly.cms.system.entity.SysTask;
import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.common.DateUtil;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.enums.TaskStatus;
import com.bfly.core.tasks.IScheduled;
import com.bfly.core.tasks.ScheduledInfo;
import com.bfly.core.tasks.ScheduledTaskExecCompleteEvent;
import com.bfly.core.tasks.ScheduledTaskExecResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 计划任务器配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/27 13:44
 */
@Configuration
@EnableAsync
@EnableScheduling
public class TaskScheduleConfig implements SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(TaskScheduleConfig.class);

    @Autowired
    private ISysTaskService taskService;

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
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.initialize();
        return executor;
    }

    @Bean(destroyMethod = "destroy")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler pool = new ThreadPoolTaskScheduler();
        pool.setPoolSize(10);
        pool.setThreadNamePrefix("TaskScheduler-");
        pool.setAwaitTerminationSeconds(60);
        pool.setWaitForTasksToCompleteOnShutdown(false);
        return pool;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    /**
     * 应用启动后执行把系统所有的计划任务同步到数据库
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:18
     */
    @EventListener
    public void initScheduleTask(ApplicationStartedEvent context) {
        //得到所有计划任务类
        Map<String, IScheduled> beansMap = context.getApplicationContext().getBeansOfType(IScheduled.class);
        Map<Method,Set<Scheduled>> taskMethods=new HashMap<>(8);
        beansMap.forEach((name, bean) -> {
            //得到所有计划任务类中执行的方法
            Map<Method, Set<Scheduled>> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<Set<Scheduled>>) method -> {
                        Set<Scheduled> scheduledMethods = AnnotatedElementUtils.getMergedRepeatableAnnotations(
                                method, Scheduled.class, Schedules.class);
                        return (!scheduledMethods.isEmpty() ? scheduledMethods : null);
                    });

            taskMethods.putAll(annotatedMethods);
        });

        clearRedundantTask(taskMethods);

        taskMethods.forEach((method, schedules) -> {
            //每个方法就是每一个执行计划任务并把计划任务写入数据库
            ScheduledInfo info = ReflectUtils.getActionAnnotationValue(method, ScheduledInfo.class);
            if (info != null) {
                SysTask sysTask = taskService.getTask(info.name());
                Scheduled scheduled = schedules.iterator().next();
                Date nextExec = DateUtil.getNextDateByCron(scheduled.cron());
                if (sysTask != null) {
                    if (sysTask.getStatus() == TaskStatus.START.getId()) {
                        sysTask.setNextExecTime(nextExec);
                    }
                    sysTask.setPeriod(scheduled.cron());
                    sysTask.setRemark(info.remark());
                    taskService.edit(sysTask);
                } else {
                    sysTask = new SysTask();
                    sysTask.setName(info.name());
                    sysTask.setPeriod(scheduled.cron());
                    sysTask.setNextExecTime(nextExec);
                    sysTask.setStatus(TaskStatus.START.getId());
                    sysTask.setRemark(info.remark());
                    taskService.save(sysTask);
                }
            }
        });
    }

    /**
     * 移除数据库多余的任务记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 20:43
     */
    private void clearRedundantTask(Map<Method, Set<Scheduled>> annotatedMethods) {
        List<SysTask> tasks = taskService.getList();
        if (tasks == null) {
            return;
        }
        tasks.forEach(sysTask -> {
            boolean flag = false;
            for (Method method : annotatedMethods.keySet()) {
                ScheduledInfo info = ReflectUtils.getActionAnnotationValue(method, ScheduledInfo.class);
                if (info != null) {
                    if (sysTask.getName().equalsIgnoreCase(info.name())) {
                        flag = true;
                        break;
                    }
                }
            }
            //无效的任务记录
            if (!flag) {
                taskService.remove(sysTask.getId());
                logger.warn("删除无效计划任务:" + sysTask.getName());
            }
        });
    }

    /**
     * 计划任务执行完毕后调用监听器
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 20:30
     */
    @EventListener
    public void scheduleTaskExecCompleteListener(ScheduledTaskExecCompleteEvent event) {
        ScheduledTaskExecResult result = event.getExecResult();
        Date date = DateUtil.getNextDateByCron(result.getCron());
        SysTask task = taskService.getTask(result.getTaskName());

        task.setPreExecTime(result.getCompleteDate());
        task.setNextExecTime(date);
        task.setPeriod(result.getCron());
        task.setCount(task.getCount() + 1);
        task.setPreExecResult(result.getExecResult());
        taskService.edit(task);
    }
}
