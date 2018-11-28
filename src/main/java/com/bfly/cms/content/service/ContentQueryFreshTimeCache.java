package com.bfly.cms.content.service;

import java.util.Date;

/**
 * 内容查询时间缓存接口
 */
public interface ContentQueryFreshTimeCache {

	/**
	 * 内容查询时间缓存立即更新
	 */
	 void clearCache();

	/**
	 * 获取缓存时间
	 * @return
	 */
	 Date getTime();

	/**
	 * 修改缓存时间(分钟)
	 * @param interval
	 */
	 void setInterval(int interval);
}
