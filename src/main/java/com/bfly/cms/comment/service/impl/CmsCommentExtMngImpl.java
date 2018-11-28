package com.bfly.cms.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.comment.dao.CmsCommentExtDao;
import com.bfly.cms.comment.entity.CmsComment;
import com.bfly.cms.comment.entity.CmsCommentExt;
import com.bfly.cms.comment.service.CmsCommentExtMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsCommentExtMngImpl implements CmsCommentExtMng {
	@Override
    public CmsCommentExt save(String ip, String text, CmsComment comment) {
		CmsCommentExt ext = new CmsCommentExt();
		ext.setText(text);
		ext.setIp(ip);
		ext.setComment(comment);
		comment.setCommentExt(ext);
		dao.save(ext);
		return ext;
	}

	@Override
    public CmsCommentExt update(CmsCommentExt bean) {
		Updater<CmsCommentExt> updater = new Updater<CmsCommentExt>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public int deleteByContentId(Integer contentId) {
		return dao.deleteByContentId(contentId);
	}

	@Autowired
	private CmsCommentExtDao dao;
}