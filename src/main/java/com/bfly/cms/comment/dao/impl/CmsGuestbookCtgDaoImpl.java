package com.bfly.cms.comment.dao.impl;

import com.bfly.cms.comment.dao.CmsGuestbookCtgDao;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/4 14:48
 */
@Repository
public class CmsGuestbookCtgDaoImpl extends AbstractHibernateBaseDao<CmsGuestbookCtg, Integer> implements CmsGuestbookCtgDao {

    @Override
    public List<CmsGuestbookCtg> getList() {
        String hql = "from CmsGuestbookCtg bean order by bean.priority asc";
        return find(hql);
    }

    @Override
    public CmsGuestbookCtg findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsGuestbookCtg save(CmsGuestbookCtg bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsGuestbookCtg deleteById(Integer id) {
        CmsGuestbookCtg entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsGuestbookCtg> getEntityClass() {
        return CmsGuestbookCtg.class;
    }
}