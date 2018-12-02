package com.bfly.cms.system.service.impl;

import com.bfly.core.Constants;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.util.DateUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.service.CmsUserExtMng;
import net.sf.ehcache.Ehcache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import java.util.*;

/**
 * @Description 每日任务(执行每日pv、每日访客量清空)
 * @author tom
 */
public class SiteDayJob{
	private static final Logger log = LoggerFactory.getLogger(SiteDayJob.class);
	
	public void execute() {
		clearDayPvAndVisitor();
		clearApiUserLogin();
		clearDayCount();
	}
	
	private void clearDayPvAndVisitor(){
		List<CmsSite>sites=cmsSiteMng.getList();
		Map<String,String>dayPv=new HashMap<>();
		dayPv.put(CmsSite.DAY_PV_TOTAL, "0");
		Map<String,String>dayVisitor=new HashMap<>();
		dayVisitor.put(CmsSite.DAY_VISITORS, "0");
		for(CmsSite site:sites){
			cmsSiteMng.updateAttr(site.getId(), dayPv,dayVisitor);
		}
		dayPvTotalCache.removeAll();
		dayVisitorTotalCache.removeAll();
		log.info("Clear Day Pv And Visitor Job success!");
	}
	
	//清除API用户(超时登陆信息)
	private void clearApiUserLogin(){
		Date end=DateUtils.getMinuteBeforeTime(Calendar.getInstance().getTime(),
				Constants.USER_OVER_TIME);
		apiUserLoginMng.clearByDate(end);
	}
	//清零用户当日评论数、当日留言数
	private void clearDayCount(){
		userExtMng.clearDayCount();
	}

	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private ApiUserLoginMng apiUserLoginMng;
	@Autowired
	private CmsUserExtMng userExtMng;
	
	private Ehcache dayPvTotalCache;
	private Ehcache dayVisitorTotalCache;

	@Autowired
	public void setCache(EhCacheCacheManager cacheManager) {
		EhCacheCache ehcache = (EhCacheCache) cacheManager.getCache("cmsDayPvTotalCache");
		dayPvTotalCache = ehcache.getNativeCache();

		ehcache = (EhCacheCache) cacheManager.getCache("cmsDayVisitorTotalCache");
		dayVisitorTotalCache = ehcache.getNativeCache();
	}
}
