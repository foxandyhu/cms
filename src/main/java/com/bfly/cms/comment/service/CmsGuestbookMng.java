package com.bfly.cms.comment.service;

import java.util.List;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookExt;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:42
 */
public interface CmsGuestbookMng {
	 Pagination getPage(Integer ctgId, Integer ctgIds[], Integer userId, Boolean recommend,Short checked, boolean desc, boolean cacheable, int pageNo,int pageSize);

	 List<CmsGuestbook> getList(Integer ctgId, Integer userId,Boolean recommend, Short checked, boolean desc, boolean cacheable, int first, int max);

	 CmsGuestbook findById(Integer id);

	 CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId, String ip);

	 CmsGuestbook save(CmsUser member,Integer ctgId, String ip, String title, String content, String email, String phone, String qq);

	 CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId);

	 CmsGuestbook deleteById(Integer id);

	 CmsGuestbook[] deleteByIds(Integer[] ids);

	 CmsGuestbook[] checkByIds(Integer[] ids, CmsAdmin admin, Short checkStatus);
}