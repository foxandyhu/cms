package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCheck;

/**
 * 内容审核Manager接口
 * 
 * '内容'数据存在，则'内容审核'数据必须存在。
 */
public interface ContentCheckMng {
	 ContentCheck save(ContentCheck check, Content content);

	 ContentCheck update(ContentCheck bean);
}