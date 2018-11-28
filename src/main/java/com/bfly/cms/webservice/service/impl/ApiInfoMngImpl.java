package com.bfly.cms.webservice.service.impl;

import com.bfly.cms.webservice.entity.ApiInfo;
import com.bfly.cms.webservice.dao.ApiInfoDao;
import com.bfly.cms.webservice.service.ApiInfoMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiInfoMngImpl implements ApiInfoMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(int pageNo, int pageSize) {
        return dao.getPage(pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiInfo findByUrl(String url) {
        return dao.findByUrl(url);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ApiInfo findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public ApiInfo save(ApiInfo bean) {
        return dao.save(bean);
    }

    @Override
    public ApiInfo update(ApiInfo bean) {
        Updater<ApiInfo> updater = new Updater<>(bean);
        return dao.updateByUpdater(updater);
    }

    @Override
    public ApiInfo deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public ApiInfo[] deleteByIds(Integer[] ids) {
        ApiInfo[] beans = new ApiInfo[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private ApiInfoDao dao;
}