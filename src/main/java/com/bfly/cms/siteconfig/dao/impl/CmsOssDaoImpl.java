package com.bfly.cms.siteconfig.dao.impl;

import com.bfly.cms.siteconfig.dao.CmsOssDao;
import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:33
 */
@Repository
public class CmsOssDaoImpl extends AbstractHibernateBaseDao<CmsOss, Integer> implements CmsOssDao {

    @Override
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria c = createCriteria();
        return findByCriteria(c, pageNo, pageSize);
    }

    @Override
    public List<CmsOss> getList() {
        String hql = "from CmsOss";
        Finder f = Finder.create(hql);
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public CmsOss findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsOss save(CmsOss bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsOss deleteById(Integer id) {
        CmsOss entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsOss> getEntityClass() {
        return CmsOss.class;
    }
}