package com.bfly.cms.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.content.dao.ContentTxtDao;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.cms.content.service.ContentTxtMng;
import com.bfly.common.hibernate4.Updater;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentTxtMngImpl implements ContentTxtMng {

	@Override
    public ContentTxt save(ContentTxt txt, Content content) {
		if (txt.isAllBlank()) {
			return null;
		} else {
			txt.setContent(content);
			txt.init();
			dao.save(txt);
			content.setContentTxt(txt);
			return txt;
		}
	}

	@Override
    public ContentTxt update(ContentTxt txt, Content content) {
		ContentTxt entity = dao.findById(content.getId());
		if (entity == null) {
			entity = save(txt, content);
			content.setContentTxt(entity);
			return entity;
		} else {
			if (txt.isAllBlank()) {
				content.setContentTxt(null);
				return null;
			} else {
				Updater<ContentTxt> updater = new Updater<ContentTxt>(txt);
				entity = dao.updateByUpdater(updater);
				entity.blankToNull();
				return entity;
			}
		}
	}
	@Autowired
	private ContentTxtDao dao;
}