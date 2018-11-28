package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentTxt;

public interface ContentTxtMng {
	 ContentTxt save(ContentTxt txt, Content content);

	 ContentTxt update(ContentTxt txt, Content content);
}