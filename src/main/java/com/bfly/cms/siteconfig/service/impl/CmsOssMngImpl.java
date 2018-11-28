package com.bfly.cms.siteconfig.service.impl;

import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.cms.siteconfig.service.CmsOssMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.siteconfig.dao.CmsOssDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsOssMngImpl implements CmsOssMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsOss> getList() {
        return dao.getList();
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsOss findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsOss save(CmsOss bean) {
        return dao.save(bean);
    }

    @Override
    public CmsOss update(CmsOss bean) {
        Updater<CmsOss> updater = new Updater<>(bean);
        if (StringUtils.isBlank(bean.getSecretId())) {
            updater.exclude("secretId");
        }
        if (StringUtils.isBlank(bean.getAppKey())) {
            updater.exclude("appKey");
        }
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsOss deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsOss[] deleteByIds(Integer[] ids) {
        CmsOss[] beans = new CmsOss[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsOssDao dao;
}