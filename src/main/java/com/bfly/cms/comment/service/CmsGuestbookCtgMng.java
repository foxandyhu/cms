package com.bfly.cms.comment.service;

import java.util.List;

import com.bfly.cms.comment.entity.CmsGuestbookCtg;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:47
 */
public interface CmsGuestbookCtgMng {
	 List<CmsGuestbookCtg> getList();

	 CmsGuestbookCtg findById(Integer id);

	 CmsGuestbookCtg save(CmsGuestbookCtg bean);

	 CmsGuestbookCtg update(CmsGuestbookCtg bean);

	 CmsGuestbookCtg deleteById(Integer id);

	 CmsGuestbookCtg[] deleteByIds(Integer[] ids);
}