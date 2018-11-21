package com.bfly.cms.manager.assist;

import com.bfly.cms.entity.assist.CmsGuestbook;
import com.bfly.cms.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}