package com.bfly.cms.sms.service.impl;

import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.sms.service.CmsSmsMng;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.sms.dao.CmsSmsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSmsMngImpl implements CmsSmsMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(Byte source, int pageNo, int pageSize) {
        return dao.getPage(source, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsSms> getList() {
        return dao.getList();
    }

    @Override
    public CmsSms findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public CmsSms save(CmsSms bean) {
        return dao.save(bean);
    }

    @Override
    public CmsSms update(CmsSms bean) {
        Updater<CmsSms> updater = new Updater<>(bean);
        if (StringUtils.isBlank(bean.getAccessKeyId())) {
            updater.exclude("accessKeyId");
        }
        if (StringUtils.isBlank(bean.getAccessKeySecret())) {
            updater.exclude("accessKeySecret");
        }
        return dao.updateByUpdater(updater);
    }

    @Override
    public CmsSms deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public CmsSms[] deleteByIds(Integer[] ids) {
        CmsSms[] beans = new CmsSms[ids.length];
        for (int i = 0; i < beans.length; i++) {
            //查询设置中是否引用了当前配置
            CmsConfig cmsConfig = manager.get();
            Long smsID = cmsConfig.getSmsID();
            if (smsID != null) {
                if (Integer.valueOf(cmsConfig.getSmsID().toString()).equals(ids[i])) {
                    cmsConfig.setSmsID(null);
                    manager.update(cmsConfig);
                }
            }
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsSmsDao dao;

    @Autowired
    private CmsConfigMng manager;
}
