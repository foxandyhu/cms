package com.bfly.core.manager.impl;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.dao.FtpDao;
import com.bfly.core.entity.Ftp;
import com.bfly.core.manager.FtpMng;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FtpMngImpl implements FtpMng {
	@Override
    @Transactional(readOnly = true)
	public List<Ftp> getList() {
		return dao.getList();
	}

	@Override
    @Transactional(readOnly = true)
	public Ftp findById(Integer id) {
		Ftp entity = dao.findById(id);
		return entity;
	}

	@Override
    public Ftp save(Ftp bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public Ftp update(Ftp bean) {
		Updater<Ftp> updater = new Updater<Ftp>(bean);
		if (StringUtils.isBlank(bean.getPassword())) {
			updater.exclude("password");
		}
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public Ftp deleteById(Integer id) {
		Ftp bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public Ftp[] deleteByIds(Integer[] ids) {
		Ftp[] beans = new Ftp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private FtpDao dao;
}