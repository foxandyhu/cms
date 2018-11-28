package com.bfly.cms.comment.service;

import java.util.List;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

public interface CmsCommentMng {
	 Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);

	 Pagination getNewPage(Integer siteId, Integer contentId,
			Short checked, Boolean recommend,int pageNo, int pageSize);
	
	 Pagination getPageForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);
	
	/**
	 * 
	 * @param siteId
	 * @param contentId
	 * @param toUserId 写评论的用户
	 * @param fromUserId 投稿的信息接收到的相关评论
	 * @param greaterThen
	 * @param checked
	 * @param recommend
	 * @param desc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize);
	
	 List<CmsComment> getListForMember(Integer siteId, Integer contentId,
			Integer toUserId,Integer fromUserId,Integer greaterThen,
			Short checked, Boolean recommend,
			boolean desc, Integer first, Integer count);
	/**
	 * 
	 * @param siteId
	 * @param userId 发表信息用户id
	 * @param commentUserId 评论用户id
	 * @param ip  评论来访ip
	 * @return
	 */
	 List<CmsComment> getListForDel(Integer siteId, Integer userId,Integer commentUserId,String ip);

	 List<CmsComment> getListForTag(Integer siteId, Integer contentId,
			Integer parentId,Integer greaterThen, Short checked, Boolean recommend,
			boolean desc, Integer first,int count);

	 CmsComment findById(Integer id);

	 CmsComment comment(Integer score,String text, String ip, Integer contentId,
			Integer siteId, Integer userId, short checked, boolean recommend,Integer parentId);

	 CmsComment update(CmsComment bean, CmsCommentExt ext);

	 int deleteByContentId(Integer contentId);

	 CmsComment deleteById(Integer id);

	 CmsComment[] deleteByIds(Integer[] ids);

	 void ups(Integer id);

	 void downs(Integer id);

	 CmsComment[] checkByIds(Integer[] ids, CmsUser user, short checked);
}