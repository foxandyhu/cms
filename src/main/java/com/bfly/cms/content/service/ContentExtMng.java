package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentExt;

public interface ContentExtMng {
	 ContentExt save(ContentExt ext, Content content);

	 ContentExt update(ContentExt ext);
}