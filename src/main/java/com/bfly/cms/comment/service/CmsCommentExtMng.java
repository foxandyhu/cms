package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;

public interface CmsCommentExtMng {
	 CmsCommentExt save(String ip, String text, CmsComment comment);

	 CmsCommentExt update(CmsCommentExt bean);

	 int deleteByContentId(Integer contentId);
}