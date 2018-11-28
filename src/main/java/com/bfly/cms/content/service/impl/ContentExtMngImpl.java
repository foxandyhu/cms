package com.bfly.cms.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.content.dao.ContentExtDao;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentExt;
import com.bfly.cms.content.service.ContentExtMng;
import com.bfly.common.hibernate4.Updater;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:40
 */
@Service
@Transactional(rollbackFor = Exception.class)
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