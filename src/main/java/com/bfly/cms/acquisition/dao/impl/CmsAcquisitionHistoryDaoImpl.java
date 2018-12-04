package com.bfly.cms.acquisition.dao.impl;

import com.bfly.cms.acquisition.dao.CmsAcquisitionHistoryDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/12/4 9:57
 */
@Repository
public class CmsAcquisitionHistoryDaoImpl extends AbstractHibernateBaseDao<CmsAcquisitionHistory, Integer> implements CmsAcquisitionHistoryDao {

    @Override
    public List<CmsAcquisitionHistory> getList(Integer acquId) {
        Finder f = Finder.create("from CmsAcquisitionHistory bean where 1=1");
        if (acquId != null) {
            f.append(" and bean.acquisition.id=:acquId");
            f.setParam("acquId", acquId);
        }
        f.append(" order by bean.id asc");
        return find(f);
    }

    @Override
    public Pagination getPage(Integer acquId, Integer pageNo, Integer pageSize) {
        Finder f = Finder.create("from CmsAcquisitionHistory bean where 1=1");
        if (acquId != null) {
            f.append(" and bean.acquisition.id=:acquId");
            f.setParam("acquId", acquId);
        }
        f.append(" order by bean.id desc");
        return find(f, pageNo, pageSize);
    }

    @Override
    public CmsAcquisitionHistory findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsAcquisitionHistory save(CmsAcquisitionHistory bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsAcquisitionHistory deleteById(Integer id) {
        CmsAcquisitionHistory entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    public void deleteByAcquisition(Integer acquId) {
        String hql = "delete from CmsAcquisitionHistory bean where bean.acquisition.id=:acquId";
        Query query = getSession().createQuery(hql).setParameter("acquId", acquId);
        query.executeUpdate();
    }

    @Override
    public Boolean checkExistByProperties(Boolean title, String value) {
        Criteria crit = createCriteria();
        if (title) {
            crit.add(Restrictions.eq("title", value));
        } else {
            crit.add(Restrictions.eq("contentUrl", value));
        }
        return crit.list().size() > 0;
    }

    @Override
    protected Class<CmsAcquisitionHistory> getEntityClass() {
        return CmsAcquisitionHistory.class;
    }

}