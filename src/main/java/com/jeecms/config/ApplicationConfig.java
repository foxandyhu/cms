package com.jeecms.config;

import com.jeecms.common.hibernate4.TreeIntercptor;
import com.jeecms.common.image.ImageScaleImpl;
import com.jeecms.common.ipseek.IPSeekerImpl;
import com.jeecms.common.web.session.HttpSessionProvider;
import com.jeecms.common.web.session.SessionProvider;
import com.jeecms.common.web.springmvc.SimpleFreeMarkerView;
import org.hibernate.SessionFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
*  @Description: 系统总体配置
*  @Author: andy_hulibo@163.com
*  @CreateDate: 2018/11/10 9:41
*/
@Configuration
@EnableTransactionManagement
@EnableCaching
public class ApplicationConfig {
	
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}
	
	@Bean
	public TreeIntercptor treeInterceptor() {
		TreeIntercptor tor=new TreeIntercptor();
		return tor;
	}
	
	@Bean
    public HibernateTransactionManager txManager(SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

	/**
	 * 图片处理
	 * @author andy_hulibo@163.com
	 * @date 2018/11/14 14:22
	 */
	@Bean(initMethod="init")
	public ImageScaleImpl imageScale() {
		ImageScaleImpl image=new ImageScaleImpl();
		image.setTryMagick(true);
		return image;
	}

	@Bean
	public SessionProvider sessionProvider(){
		SessionProvider provider=new HttpSessionProvider();
		return provider;
	}

	/**
	 * IP解析器
	 * @author andy_hulibo@163.com
	 * @date 2018/11/14 14:30
	 */
	@Bean(initMethod = "init")
	public IPSeekerImpl ipSeeker(){
		IPSeekerImpl ipSeeker=new IPSeekerImpl();
		ipSeeker.setDir("/");
		ipSeeker.setFilename("QQWry.Dat");
		return ipSeeker;
	}
	
	@Bean
	public CommandLineRunner customFreemarkerView(FreeMarkerViewResolver resolver) {
		//设置Freemarker解析视图;
		return (String ... args) ->resolver.setViewClass(SimpleFreeMarkerView.class);
	}
}