package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.ContentType;
import com.bfly.cms.content.service.ContentTypeMng;
import com.bfly.cms.content.dao.ContentTypeDao;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentTypeMngImpl implements ContentTypeMng {
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<ContentType> getList(Boolean isDisabled) {
		return dao.getList(isDisabled);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ContentType getDef() {
		return dao.getDef();
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public ContentType findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public ContentType save(ContentType bean) {
		return dao.save(bean);
	}

	@Override
    public ContentType update(ContentType bean) {
		Updater<ContentType> updater = new Updater<>(bean);
		return dao.updateByUpdater(updater);
	}

	@Override
    public ContentType deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
    public ContentType[] deleteByIds(Integer[] ids) {
		ContentType[] beans = new ContentType[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private ContentTypeDao dao;
}