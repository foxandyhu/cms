package com.bfly.cms.comment.dao;

import java.util.List;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsCommentDao{
	Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize, boolean cacheable);

	Pagination getNewPage(Integer siteId, Integer contentId,Short checked, Boolean recommend
			,int pageNo, int pageSize, boolean cacheable);

	List<CmsComment> getList(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, Integer first,int count, boolean cacheable);
	
	CmsComment findById(Integer id);

	int deleteByContentId(Integer contentId);

	CmsComment save(CmsComment bean);

	CmsComment updateByUpdater(Updater<CmsComment> updater);

	CmsComment deleteById(Integer id);

	Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize,boolean cacheable);
	
	List<CmsComment> getListForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, Integer first,Integer count,boolean cacheable);

	List<CmsComment> getListForDel(Integer siteId, Integer userId,
			Integer commentUserId, String ip);
}