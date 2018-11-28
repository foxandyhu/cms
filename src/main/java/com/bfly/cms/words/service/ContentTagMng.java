package com.bfly.cms.words.service;

import java.util.Collection;
import java.util.List;

import com.bfly.cms.words.entity.ContentTag;
import com.bfly.common.page.Pagination;

public interface ContentTagMng {

	 List<ContentTag> getListForTag(Integer first,Integer count);

	 Pagination getPageForTag(int pageNo, int pageSize);

	 Pagination getPage(String name, int pageNo, int pageSize);

	 ContentTag findById(Integer id);

	 ContentTag findByName(String name);

	 ContentTag findByNameForTag(String name);

	/**
	 * 保存标签。不存在的保存，存在的数量加一。
	 * 
	 * @param tagArr
	 *            标签名数组
	 * @return 标签列表
	 */
	 List<ContentTag> saveTags(String[] tagArr);

	 ContentTag saveTag(String name);

	 List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr);

	 void removeTags(Collection<ContentTag> tags);

	 ContentTag save(ContentTag bean);

	 ContentTag update(ContentTag bean);

	 ContentTag deleteById(Integer id);

	 ContentTag[] deleteByIds(Integer[] ids);
}