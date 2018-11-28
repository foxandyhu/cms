package com.bfly.cms.siteconfig.service.impl;

import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.siteconfig.service.FtpMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.siteconfig.dao.FtpDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 14:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FtpMngImpl implements FtpMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<Ftp> getList() {
        return dao.getList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Ftp findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Ftp save(Ftp bean) {
        return dao.save(bean);
    }

    @Override
    public Ftp update(Ftp bean) {
        Updater<Ftp> updater = new Updater<>(bean);
        if (StringUtils.isBlank(bean.getPassword())) {
            updater.exclude("password");
        }
        return dao.updateByUpdater(updater);
    }

    @Override
    public Ftp deleteById(Integer id) {
        return dao.deleteById(id);
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