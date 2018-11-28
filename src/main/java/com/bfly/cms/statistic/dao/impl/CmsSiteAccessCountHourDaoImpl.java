package com.bfly.cms.statistic.dao.impl;

import com.bfly.cms.statistic.entity.CmsSiteAccessCountHour;
import com.bfly.cms.statistic.dao.CmsSiteAccessCountHourDao;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/27 9:58
 */
@Repository
public class CmsSiteAccessCountHourDaoImpl extends AbstractHibernateBaseDao<CmsSiteAccessCountHour, Integer> implements CmsSiteAccessCountHourDao {

    @Override
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria crit = createCriteria();
        Pagination page = findByCriteria(crit, pageNo, pageSize);
        return page;
    }

    @Override
    public List<CmsSiteAccessCountHour> getList(Date date) {
        String hql = "from  CmsSiteAccessCountHour bean ";
        Finder f = Finder.create(hql);
        if (date != null) {
            f.append(" where bean.accessDate=:date").setParam("date", date);
        }
        f.setCacheable(true);
        return find(f);
    }

    @Override
    public CmsSiteAccessCountHour findById(Integer id) {
        CmsSiteAccessCountHour entity = get(id);
        return entity;
    }

    @Override
    public CmsSiteAccessCountHour save(CmsSiteAccessCountHour bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsSiteAccessCountHour deleteById(Integer id) {
        CmsSiteAccessCountHour entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsSiteAccessCountHour> getEntityClass() {
        return CmsSiteAccessCountHour.class;
    }
}