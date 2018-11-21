package com.bfly.cms.manager.assist;

import com.bfly.cms.entity.assist.CmsComment;
import com.bfly.cms.entity.assist.CmsCommentExt;

public interface CmsCommentExtMng {
	public CmsCommentExt save(String ip, String text, CmsComment comment);

	public CmsCommentExt update(CmsCommentExt bean);

	public int deleteByContentId(Integer contentId);
}