package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.CmsSearchWordsDao;
import com.bfly.cms.words.entity.CmsSearchWords;
import com.bfly.core.web.CmsThreadVariable;
import com.bfly.cms.words.service.CmsSearchWordsMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.ChineseCharToEn;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSearchWordsMngImpl implements CmsSearchWordsMng {

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Pagination getPage(Integer siteId, String name, Integer recommend,
                              Integer orderBy, int pageNo, int pageSize) {
        Pagination page = dao.getPage(siteId, name, recommend, orderBy, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<CmsSearchWords> getList(Integer siteId, String name,
                                        Integer recommend, Integer orderBy, Integer first,
                                        Integer count, boolean cacheable) {
        return dao.getList(siteId, name, recommend, orderBy, first, count, cacheable);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsSearchWords findById(Integer id) {
        CmsSearchWords entity = dao.findById(id);
        return entity;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CmsSearchWords findByName(String name) {
        CmsSearchWords entity = dao.findByName(name);
        return entity;
    }

    @Override
    public CmsSearchWords save(CmsSearchWords bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public CmsSearchWords update(CmsSearchWords bean) {
        Updater<CmsSearchWords> updater = new Updater<CmsSearchWords>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public CmsSearchWords deleteById(Integer id) {
        CmsSearchWords bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public CmsSearchWords[] deleteByIds(Integer[] ids) {
        CmsSearchWords[] beans = new CmsSearchWords[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int freshCacheToDB(Ehcache cache) {
        int count = 0;
        List<String> list = cache.getKeys();
        for (String word : list) {
            Element element = cache.get(word);
            if (element == null) {
                return count;
            }
            count = (Integer) element.getObjectValue();
            CmsSearchWords searchWord = findByName(word);
            if (searchWord != null) {
                searchWord.setHitCount(count + searchWord.getHitCount());
                update(searchWord);
            } else {
                searchWord = new CmsSearchWords();
                searchWord.setHitCount(count);
                searchWord.setName(word);
                searchWord.setNameInitial(ChineseCharToEn.getAllFirstLetter(word));
                searchWord.setPriority(CmsSearchWords.DEFAULT_PRIORITY);
                searchWord.setRecommend(false);
                searchWord.setSite(CmsThreadVariable.getSite());
                save(searchWord);
            }
        }
        return count;
    }

    @Autowired
    private CmsSearchWordsDao dao;
}