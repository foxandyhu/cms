package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	 CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	 CmsGuestbookExt update(CmsGuestbookExt ext);
}