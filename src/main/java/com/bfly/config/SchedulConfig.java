package com.bfly.config;

import com.bfly.cms.task.service.impl.LoadTask;
import com.bfly.cms.task.service.impl.ContentDayJob;
import com.bfly.cms.task.service.impl.SiteDayJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 计划任务配置
 * @author andy_hulibo@163.com
 * @date 2018/11/14 15:24
 */
@Configuration
public class SchedulConfig {

	@Bean
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(500);
		return executor;
	}

	/**
	 * 每日任务(内容相关)
	 * @author andy_hulibo@163.com
	 * @date 2018/11/14 15:13
	 */
	@Bean
	public ContentDayJob contentDayJob(){
		return new ContentDayJob();
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean contentDayJobDetail(ContentDayJob contentDayJob) {
		MethodInvokingJobDetailFactoryBean factory=new MethodInvokingJobDetailFactoryBean();
		factory.setTargetObject(contentDayJob);
		factory.setTargetMethod("execute");
		return factory;
	}

	@Bean("contentDayJobTrigger")
	public CronTriggerFactoryBean contentDayJobTrigger(@Qualifier("contentDayJobDetail") MethodInvokingJobDetailFactoryBean contentDayJobDetail) {
		CronTriggerFactoryBean factory=new CronTriggerFactoryBean();
		factory.setCronExpression("0 0 0 * * ?");
		factory.setJobDetail(contentDayJobDetail.getObject());
		return factory;
	}

	@Bean
	public SiteDayJob siteDayJob(){
		return new SiteDayJob();
	}

	@Bean
	public MethodInvokingJobDetailFactoryBean siteDayJobDetail(SiteDayJob siteDayJob) {
		MethodInvokingJobDetailFactoryBean factory=new MethodInvokingJobDetailFactoryBean();
		factory.setTargetObject(siteDayJob);
		factory.setTargetMethod("execute");
		return factory;
	}

	@Bean("siteDayJobTrigger")
	public CronTriggerFactoryBean siteDayJobTrigger(@Qualifier("siteDayJobDetail") MethodInvokingJobDetailFactoryBean siteDayJobDetail) {
		CronTriggerFactoryBean factory=new CronTriggerFactoryBean();
		factory.setCronExpression("0 0 0 * * ?");
		factory.setJobDetail(siteDayJobDetail.getObject());
		return factory;
	}

	/**
	 * 调度器
	 * @author andy_hulibo@163.com
	 * @date 2018/11/14 15:18
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(ThreadPoolTaskExecutor executor,@Qualifier("contentDayJobTrigger") CronTriggerFactoryBean contentDayJobTrigger,@Qualifier("siteDayJobTrigger") CronTriggerFactoryBean siteDayJobTrigger) {
		SchedulerFactoryBean bean=new SchedulerFactoryBean();
		bean.setApplicationContextSchedulerContextKey("applicationContext");
		bean.setTriggers(contentDayJobTrigger.getObject(),siteDayJobTrigger.getObject());
		bean.setTaskExecutor(executor);
		return bean;
	}

	/**
	 * 加载数据库任务
	 * @author andy_hulibo@163.com
	 * @date 2018/11/14 15:23
	 */
	@Bean(initMethod = "loadTask")
	public LoadTask loadTask(){
		return new LoadTask();
	}
}
