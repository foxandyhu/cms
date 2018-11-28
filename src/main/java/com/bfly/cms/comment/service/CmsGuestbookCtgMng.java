package com.bfly.cms.comment.service;

import java.util.List;

import com.bfly.cms.comment.entity.CmsGuestbookCtg;

public interface CmsGuestbookCtgMng {
	 List<CmsGuestbookCtg> getList(Integer siteId);

	 CmsGuestbookCtg findById(Integer id);

	 CmsGuestbookCtg save(CmsGuestbookCtg bean);

	 CmsGuestbookCtg update(CmsGuestbookCtg bean);

	 CmsGuestbookCtg deleteById(Integer id);

	 CmsGuestbookCtg[] deleteByIds(Integer[] ids);
}