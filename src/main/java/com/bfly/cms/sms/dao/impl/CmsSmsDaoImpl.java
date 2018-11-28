package com.bfly.cms.sms.dao.impl;

import com.bfly.cms.sms.dao.CmsSmsDao;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:30
 */
@Repository
public class CmsSmsDaoImpl extends AbstractHibernateBaseDao<CmsSms, Integer> implements CmsSmsDao {

    @Override
    public Pagination getPage(Byte source, int pageNo, int pageSize) {
        Finder f = Finder.create(" from CmsSms bean where 1=1");
        if (source != null) {
            f.append(" and bean.source=:source").setParam("source", source);
        }
        f.append(" order by bean.createTime desc");
        return find(f, pageNo, pageSize);
    }

    @Override
    public List<CmsSms> getList() {
        String hql = "from CmsSms";
        Finder f = Finder.create(hql);
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public CmsSms findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsSms save(CmsSms bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsSms deleteById(Integer id) {
        CmsSms entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsSms> getEntityClass() {
        return CmsSms.class;
    }
}
