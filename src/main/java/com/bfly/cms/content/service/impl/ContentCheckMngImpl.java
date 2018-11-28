package com.bfly.cms.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.content.dao.ContentCheckDao;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCheck;
import com.bfly.cms.content.service.ContentCheckMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class ContentCheckMngImpl implements ContentCheckMng {
	@Override
    public ContentCheck save(ContentCheck check, Content content) {
		check.setContent(content);
		check.init();
		dao.save(check);
		content.setContentCheck(check);
		return check;
	}

	@Override
    public ContentCheck update(ContentCheck bean) {
		Updater<ContentCheck> updater = new Updater<ContentCheck>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}
	@Autowired
	private ContentCheckDao dao;
}