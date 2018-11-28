package com.bfly.cms.content.service;

import net.sf.ehcache.Ehcache;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCount;

/**
 * 内容计数Manager接口
 * 
 * '内容'数据存在则'内容计数'数据必须存在。
 */
public interface ContentCountMng {

	 int contentUp(Integer id);

	 int contentDown(Integer id);

	 void downloadCount(Integer contentId);

	 void commentCount(Integer contentId);

	 int freshCacheToDB(Ehcache cache);

	 ContentCount findById(Integer id);

	 ContentCount save(ContentCount count, Content content);

	 ContentCount update(ContentCount bean);
}