package com.jeecms.config;

import com.jeecms.core.security.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Shiro配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/14 14:50
 */
@Configuration
public class ShiroConfig {

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new DelegatingFilterProxy());
        filter.addUrlPatterns("/*");
        filter.addInitParameter("targetFilterLifecycle", "true");
        filter.setName("shiroFilter");
        return filter;
    }

    @Bean
    public FilterRegistrationBean bbsUserFilterRegistration(CmsUserFilter userFilter) {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(userFilter);
        //不注册到FilterChain中
        filter.setEnabled(false);
        return filter;
    }

    @Bean
    public FilterRegistrationBean authcFilterRegistration(CmsAuthenticationFilter authcFilter) {
        FilterRegistrationBean filter = new FilterRegistrationBean(authcFilter);
        filter.setFilter(authcFilter);
        //不注册到FilterChain中
        filter.setEnabled(false);
        return filter;
    }

    @Bean
    public FilterRegistrationBean logoutFilterRegistration(CmsLogoutFilter logoutFilter) {
        FilterRegistrationBean filter = new FilterRegistrationBean(logoutFilter);
        filter.setFilter(logoutFilter);
        //不注册到FilterChain中
        filter.setEnabled(false);
        return filter;
    }

    @Bean
    public CmsAdminUrl adminUrlBean() {
        CmsAdminUrl admin = new CmsAdminUrl();
        admin.setAdminLogin("/jeeadmin/jeecms/login.html");
        admin.setAdminPrefix("/jeeadmin/jeecms/");
        return admin;
    }

    @Bean
    public CmsUserFilter userFilter(CmsAdminUrl adminUrlBean) {
        CmsUserFilter user = new CmsUserFilter();
        user.setAdminLogin(adminUrlBean.getAdminLogin());
        user.setAdminPrefix(adminUrlBean.getAdminPrefix());
        return user;
    }

    @Bean
    public CmsLogoutFilter logoutFilter(CmsAdminUrl adminUrlBean) {
        CmsLogoutFilter logout = new CmsLogoutFilter();
        logout.setAdminLogin(adminUrlBean.getAdminLogin());
        logout.setAdminPrefix(adminUrlBean.getAdminPrefix());
        return logout;
    }

    @Bean
    public CmsAuthenticationFilter authcFilter(CmsAdminUrl adminUrlBean) {
        CmsAuthenticationFilter authc = new CmsAuthenticationFilter();
        authc.setAdminIndex("/jeeadmin/jeecms/index.html");
        authc.setAdminPrefix(adminUrlBean.getAdminPrefix());
        authc.setRememberMeParam("rememberMe");
        return authc;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(WebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login.html");
        bean.setSuccessUrl("/");
        bean.setFilterChainDefinitionMap(new LinkedHashMap<String, String>() {
            private static final long serialVersionUID = -4794995092802667876L;
            {
                put("/login.html", "authc");
                put("/logout.html", "logout");
                put("/jeeadmin/**", "authc");
                put("/member/forgot_password.html", "anon");
                put("/member/password_reset.html", "anon");
                put("/member/jobapply.html", "anon");
                put("/member/**", "user");
                put("/**","anon");
            }
        });
        return bean;
    }

    @Bean
    public WebSecurityManager securityManager(EhCacheManager cacheManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(cacheManager);
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }

    @Bean
    public EhCacheManager shiroEhcacheManager(EhCacheCacheManager cacheManager) {
        EhCacheManager manager = new EhCacheManager();
        manager.setCacheManager(cacheManager.getCacheManager());
        return manager;
    }

    @Bean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setStoredCredentialsHexEncoded(true);
        matcher.setHashIterations(1);
        return matcher;
    }

    @Bean
    public Cookie rememberMeCookie() {
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(31536000);
        return cookie;
    }

    /**
     * rememberMe管理器
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/14 15:07
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(Cookie cookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie);
        manager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(WebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        DefaultWebSecurityManager securityManager = context.getBean(DefaultWebSecurityManager.class);

        CmsAuthorizingRealm realm = context.getBean(CmsAuthorizingRealm.class);
        CmsAuthenticationFilter authcFilter = context.getBean(CmsAuthenticationFilter.class);
        CmsUserFilter userFilter = context.getBean(CmsUserFilter.class);
        CmsLogoutFilter logoutFilter = context.getBean(CmsLogoutFilter.class);

        ShiroFilterFactoryBean shiroFilter = context.getBean(ShiroFilterFactoryBean.class);
        shiroFilter.setFilters(new HashMap<String, Filter>() {
            private static final long serialVersionUID = -8348672305577977754L;

            {
                put("user", userFilter);
                put("logout", logoutFilter);
                put("authc", authcFilter);
            }
        });
        securityManager.setRealm(realm);
    }
}
