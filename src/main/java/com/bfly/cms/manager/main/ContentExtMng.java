package com.bfly.cms.manager.main;

import com.bfly.cms.entity.main.Content;
import com.bfly.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}