package com.bfly.core.base.dao.impl;


import com.bfly.core.base.dao.AbstractHibernateDao;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.util.MyBeanUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * hibernate DAO基类
 * <p>
 * 提供hql分页查询，拷贝更新等一些常用功能。
 *
 * @param <T>  entity class
 * @param <ID> entity id
 * @author andy_hulibo@163.com
 * @date 2018/11/23 11:40
 */
public abstract class AbstractHibernateBaseDao<T, ID extends Serializable> extends AbstractHibernateDao {

    /**
     * 根据主键查询对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:44
     */
    protected T get(ID id) {
        return get(id, false);
    }

    /**
     * @param id   主键
     * @param lock 是否锁定，使用LockMode.UPGRADE
     * @return 持久化对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:44
     */
    protected T get(ID id, boolean lock) {
        Object entity;
        if (lock) {
            entity = getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
        } else {
            entity = getSession().get(getEntityClass(), id);
        }
        return (T)entity;
    }

    /**
     * 按属性查找对象列表
     *
     * @param property 属性名称
     * @param value    属性值
     * @return 对象集合
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:51
     */
    protected List<T> findByProperty(String property, Object value) {
        Assert.hasText(property, "the property is null");
        return createCriteria(Restrictions.eq(property, value)).list();
    }

    /**
     * 按属性查找唯一对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:53
     */
    protected T findUniqueByProperty(String property, Object value) {
        Assert.hasText(property, "the property is null");
        Assert.notNull(value, String.format("the %s property of value is null", property));
        return (T) createCriteria(Restrictions.eq(property, value))
                .uniqueResult();
    }

    /**
     * 按Criterion查询列表数据.
     *
     * @param criterion 数量可变的Criterion.
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:53
     */
    protected List findByCriteria(Criterion... criterion) {
        return createCriteria(criterion).list();
    }

    /**
     * 通过Updater更新对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:36
     */
    public T updateByUpdater(Updater<T> updater) {
        ClassMetadata cm = sessionFactory.getClassMetadata(getEntityClass());
        T bean = updater.getBean();
        T po = (T) getSession().get(getEntityClass(), cm.getIdentifier(bean, (SessionImplementor) sessionFactory.getCurrentSession()));
        updaterCopyToPersistentObject(updater, po, cm);
        return po;
    }

    /**
     * 将更新对象拷贝至实体对象，并处理many-to-one的更新。
     *
     * @param updater 更新对象
     * @param po      实体对象
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:53
     */
    private void updaterCopyToPersistentObject(Updater<T> updater, T po,
                                               ClassMetadata cm) {
        String[] propNames = cm.getPropertyNames();
        String identifierName = cm.getIdentifierPropertyName();
        T bean = updater.getBean();
        Object value;
        for (String propName : propNames) {
            if (propName.equals(identifierName)) {
                continue;
            }
            try {
                value = MyBeanUtils.getSimpleProperty(bean, propName);
                if (!updater.isUpdate(propName, value)) {
                    continue;
                }
                cm.setPropertyValue(po, propName, value);
            } catch (Exception e) {
                throw new RuntimeException(
                        "copy property to persistent object failed: '"
                                + propName + "'", e);
            }
        }
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     *
     * @param criterion 数量可变的Criterion.
     * @return Criterion
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:54
     */
    protected Criteria createCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(getEntityClass());
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        //设置可查询缓存
        criteria.setCacheable(true);
        return criteria;
    }

    /**
     * 获得Dao对应的实体类
     *
     * @return 实体类类型
     * @author andy_hulibo@163.com
     * @date 2018/11/23 14:55
     */
    abstract protected Class<T> getEntityClass();
}
