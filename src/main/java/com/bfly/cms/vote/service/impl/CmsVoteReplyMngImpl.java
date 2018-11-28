package com.bfly.cms.vote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.vote.dao.CmsVoteReplyDao;
import com.bfly.cms.vote.entity.CmsVoteReply;
import com.bfly.cms.vote.service.CmsVoteReplyMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

@Service
@Transactional
public class CmsVoteReplyMngImpl implements CmsVoteReplyMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize){
		return dao.getPage(subTopicId, pageNo, pageSize);
	}
	@Override
    @Transactional(readOnly = true)
	public CmsVoteReply findById(Integer id) {
		CmsVoteReply entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsVoteReply save(CmsVoteReply bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsVoteReply update(CmsVoteReply bean) {
		Updater<CmsVoteReply> updater = new Updater<CmsVoteReply>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}


	@Override
    public CmsVoteReply deleteById(Integer id) {
		CmsVoteReply bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsVoteReply[] deleteByIds(Integer[] ids) {
		CmsVoteReply[] beans = new CmsVoteReply[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	@Autowired
	private CmsVoteReplyDao dao;

}