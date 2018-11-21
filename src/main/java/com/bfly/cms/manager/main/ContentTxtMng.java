package com.bfly.cms.manager.main;

import com.bfly.cms.entity.main.Content;
import com.bfly.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}