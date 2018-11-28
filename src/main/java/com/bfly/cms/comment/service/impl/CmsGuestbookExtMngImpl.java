package com.bfly.cms.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.comment.dao.CmsGuestbookExtDao;
import com.bfly.cms.comment.entity.CmsGuestbook;
import com.bfly.cms.comment.entity.CmsGuestbookExt;
import com.bfly.cms.comment.service.CmsGuestbookExtMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsGuestbookExtMngImpl implements CmsGuestbookExtMng {
	@Override
    public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook) {
		guestbook.setExt(ext);
		ext.setGuestbook(guestbook);
		ext.init();
		dao.save(ext);
		return ext;
	}

	@Override
    public CmsGuestbookExt update(CmsGuestbookExt ext) {
		Updater<CmsGuestbookExt> updater = new Updater<CmsGuestbookExt>(ext);
		CmsGuestbookExt entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	@Autowired
	private CmsGuestbookExtDao dao;
}