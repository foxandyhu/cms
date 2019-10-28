package com.bfly.core.config;

import com.bfly.core.Constants;
import com.bfly.core.security.MemberRealm;
import com.bfly.core.servlet.MemberAuthcFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/16 16:53
 */
@Configuration
public class ShiroConfig {

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();

        Cookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Cookie.ONE_YEAR);
        cookie.setName(Constants.MEMBER_LOGIN_COOKIE_NAME);
        rememberMeManager.setCookie(cookie);
        // KEY: CMS_BFLYKEY_2019
        rememberMeManager.setCipherKey(Base64.decode("Q01TX0JGTFlLRVlfMjAxOQ=="));
        return rememberMeManager;
    }

    @Bean
    public SecurityManager securityManager(@Autowired org.springframework.cache.CacheManager cacheManager, @Autowired RememberMeManager rememberMeManager, @Autowired MemberRealm memberRealm) {
        EhCacheManager manager = new EhCacheManager();
        EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
        manager.setCacheManager(ehCacheCacheManager.getCacheManager());

        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setCacheManager(manager);
        webSecurityManager.setRememberMeManager(rememberMeManager);
        webSecurityManager.setRealm(memberRealm);
        return webSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login.html");
        bean.setSuccessUrl("/");

        Map<String, Filter> filters = new HashMap<>(1);
        filters.put("memberAuthc", new MemberAuthcFilter());
        bean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/member/**", "memberAuthc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
}
