package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.CmsSensitivityDao;
import com.bfly.cms.words.entity.CmsSensitivity;
import com.bfly.cms.words.service.CmsSensitivityMng;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSensitivityMngImpl implements CmsSensitivityMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String replaceSensitivity(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        List<CmsSensitivity> list = getList(true);
        for (CmsSensitivity sen : list) {
            s = StringUtils.replace(s, sen.getSearch(), sen.getReplacement());
        }
        return s;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public boolean haveSensitivity(String... arrays) {
        if (arrays == null || arrays.length <= 0) {
            return false;
        }
        boolean have = false;
        List<CmsSensitivity> list = getList(true);
        for (String s : arrays) {
            for (CmsSensitivity sen : list) {
                if (s.indexOf(sen.getSearch()) != -1) {
                    have = true;
                    break;
                }
            }
            if (have) {
                break;
            }
        }
        return have;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsSensitivity> getList(boolean cacheable) {
        return dao.getList(cacheable);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsSensitivity findById(Integer id) {
        CmsSensitivity entity = dao.findById(id);
        return entity;
    }

    @Override
    public CmsSensitivity save(CmsSensitivity bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public void updateEnsitivity(Integer[] ids, String[] searchs,
                                 String[] replacements) {
        CmsSensitivity ensitivity;
        for (int i = 0, len = ids.length; i < len; i++) {
            ensitivity = findById(ids[i]);
            ensitivity.setSearch(searchs[i]);
            ensitivity.setReplacement(replacements[i]);
        }
    }

    @Override
    public CmsSensitivity deleteById(Integer id) {
        CmsSensitivity bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsSensitivity[] deleteByIds(Integer[] ids) {
        CmsSensitivity[] beans = new CmsSensitivity[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    private CmsSensitivityDao dao;
}