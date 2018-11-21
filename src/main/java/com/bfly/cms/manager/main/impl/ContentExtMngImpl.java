package com.bfly.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.main.ContentExtDao;
import com.bfly.cms.entity.main.Content;
import com.bfly.cms.entity.main.ContentExt;
import com.bfly.cms.manager.main.ContentExtMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class ContentExtMngImpl implements ContentExtMng {
	@Override
    public ContentExt save(ContentExt ext, Content content) {
		content.setContentExt(ext);
		ext.setContent(content);
		if (ext.getReleaseDate() == null) {
			ext.setReleaseDate(content.getSortDate());
		}
		ext.init();
		dao.save(ext);
		content.setContentExt(ext);
		return ext;
	}

	@Override
    public ContentExt update(ContentExt bean) {
		Updater<ContentExt> updater = new Updater<ContentExt>(bean);
		if (bean.getPigeonholeDate()==null) {
			updater.include("pigeonholeDate");
		}
		if(bean.getTopLevelDate()==null){
			updater.include("topLevelDate");
		}
		bean = dao.updateByUpdater(updater);
		bean.blankToNull();
		// 修改后需要重新生成静态页
		bean.setNeedRegenerate(true);
		return bean;
	}
	@Autowired
	private ContentExtDao dao;
}