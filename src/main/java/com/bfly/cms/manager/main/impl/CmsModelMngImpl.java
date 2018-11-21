package com.bfly.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.main.CmsModelDao;
import com.bfly.cms.entity.main.CmsModel;
import com.bfly.cms.manager.main.CmsModelMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsModelMngImpl implements CmsModelMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent,Integer siteId) {
		return dao.getList(containDisabled,hasContent,siteId);
	}

	@Override
    public CmsModel getDefModel() {
		return dao.getDefModel();
	}

	@Override
    @Transactional(readOnly = true)
	public CmsModel findById(Integer id) {
		CmsModel entity = dao.findById(id);
		return entity;
	}
	
	@Override
    @Transactional(readOnly = true)
	public CmsModel findByPath(String path){
		CmsModel entity = dao.findByPath(path);
		return entity;
	}

	@Override
    public CmsModel save(CmsModel bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsModel update(CmsModel bean) {
		Updater<CmsModel> updater = new Updater<CmsModel>(bean);
		CmsModel entity = dao.updateByUpdater(updater);
		return entity;
	}

	@Override
    public CmsModel deleteById(Integer id) {
		CmsModel bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsModel[] deleteByIds(Integer[] ids) {
		CmsModel[] beans = new CmsModel[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
                                     Boolean[] disabled, Integer defId) {
		int len = ids.length;
		CmsModel[] beans = new CmsModel[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
			beans[i].setDisabled(disabled[i]);
			if (beans[i].getId().equals(defId)) {
				beans[i].setDef(true);
			} else {
				beans[i].setDef(false);
			}
		}
		return beans;
	}
	@Autowired
	private CmsModelDao dao;
}