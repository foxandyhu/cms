package com.bfly.core.base.dao;

import com.bfly.core.base.dao.impl.Finder;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.MyBeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * hibernate DAO基类
 * <p>
 * 提供hql分页查询，不带泛型，与具体实体类无关。
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 14:41
 */
public abstract class AbstractHibernateDao {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * HIBERNATE 的 order 属性
     */
    private static final String ORDER_ENTRIES = "orderEntries";


    /**
     * 通过HQL查询对象列表
     *
     * @param hql    hql语句
     * @param values 数量可变的参数
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:03
     */
    protected List find(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 通过HQL查询唯一对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:04
     */
    protected Object findUnique(String hql, Object... values) {
        return createQuery(hql, values).uniqueResult();
    }

    /**
     * 通过Finder获得分页数据
     *
     * @param finder   Finder对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return p 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:04
     */
    protected Pagination find(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    /**
     * 通过Finder获得分页数据
     *
     * @param finder   Finder对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @param hql      hql语句
     * @return p 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:04
     */
    protected Pagination find(Finder finder, String hql, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder, hql);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    /**
     * 根据查询总数de HQL预计查询总数
     *
     * @param finder Find对象
     * @param hql    HQL查询语句
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:08
     */
    protected int countQueryResult(Finder finder, String hql) {
        Query query = getSession().createQuery(hql);
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        Iterator iterator = query.iterate();
        if (iterator.hasNext()) {
            return ((Number) query.iterate().next()).intValue();
        } else {
            return 0;
        }

    }

    /**
     * 查询量大数据
     *
     * @param finder   Finder对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:11
     */
    protected Pagination findBigData(Finder finder, int pageNo, int pageSize) {
        int totalCount = pageNo * pageSize;
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        List list = query.list();
        p.setList(list);
        return p;
    }

    /**
     * 查询量大数据
     *
     * @param finder   Finder对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:11
     */
    protected Pagination findBigDataPage(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return p;
    }

    /**
     * 通过Finder获得列表数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:16
     */
    protected List find(Finder finder) {
        Query query = finder.createQuery(getSession());
        return query.list();
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:42
     */
    protected Query createQuery(String queryString, Object... values) {
        Assert.hasText(queryString, "the query string is null");
        Query queryObject = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    /**
     * 通过Criteria获得分页数据
     *
     * @param c        Criteria 对象
     * @param pageNo   页码
     * @param pageSize 每页条数
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:16
     */
    protected Pagination findByCriteria(Criteria c, int pageNo, int pageSize) {
        CriteriaImpl impl = (CriteriaImpl) c;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries;
        try {
            orderEntries = (List) MyBeanUtils
                    .getFieldValue(impl, ORDER_ENTRIES);
            MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList());
        } catch (Exception e) {
            throw new RuntimeException(
                    "cannot read/write 'orderEntries' from CriteriaImpl", e);
        }

        int totalCount = ((Number) c.setProjection(Projections.rowCount())
                .uniqueResult()).intValue();
        Pagination p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);
        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, orderEntries);
        } catch (Exception e) {
            throw new RuntimeException(
                    "set 'orderEntries' to CriteriaImpl faild", e);
        }
        c.setFirstResult(p.getFirstResult());
        c.setMaxResults(p.getPageSize());
        p.setList(c.list());
        return p;
    }

    /**
     * 获得Finder的记录总数
     *
     * @param finder Finder对象
     * @return 记录总数
     * @author andy_hulibo@163.com
     * @date 2018/11/23 15:19
     */
    protected int countQueryResult(Finder finder) {
        Query query = getSession().createQuery(finder.getRowCountHql());
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return ((Number) query.iterate().next()).intValue();
    }

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
