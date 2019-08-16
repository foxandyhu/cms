package com.bfly.core.base.service.impl;

import com.bfly.common.page.Pager;
import com.bfly.core.base.dao.IBaseDao;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.context.PagerThreadLocal;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public abstract class BaseServiceImpl<T, ID> extends BaseJdbcServiceImpl implements IBaseService<T, ID> {

    private IBaseDao<T, ID> baseDao;

    @Autowired
    public void setBaseDao(IBaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T get(ID id) {
        Optional<T> optional = baseDao.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public T get(Map<String, Object> property) {
        Optional<T> optional = baseDao.findOne(getExactQuery(property));
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(ID... ids) {
        int count = 0;
        for (ID id : ids) {
            baseDao.deleteById(id);
            count++;
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(T t) {
        baseDao.save(t);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T t) {
        baseDao.save(t);
        return true;
    }

    @Override
    public List<T> getList() {
        return baseDao.findAll();
    }

    @Override
    public List<T> getList(Map<String, Object> property) {
        if (property == null || property.isEmpty()) {
            return getList();
        }
        Specification specification = getExactQuery(property);
        if (specification != null) {
            specification = specification.and(getDefaultSpec());
        } else {
            specification = getDefaultSpec();
        }
        List<T> list = baseDao.findAll(specification);
        return list;
    }

    @Override
    public List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification sortSpec;
        if (sortQueryProperty != null) {
            sortSpec = getSortQuery(sortQueryProperty);
        } else {
            sortSpec = getDefaultSpec();
        }
        if (specification != null) {
            specification = specification.and(unExactSpec).and(sortSpec);
        } else {
            specification = sortSpec.and(unExactSpec);
        }
        List<T> list = baseDao.findAll(specification);
        return list;
    }

    @Override
    public List<T> getList(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty) {
        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification groupSpec = getGroupQuery(groupProperty);
        Specification sortSpec;
        if (sortQueryProperty != null) {
            sortSpec = getSortQuery(sortQueryProperty);
        } else {
            sortSpec = getDefaultSpec();
        }
        if (specification != null) {
            specification = specification.and(unExactSpec).and(sortSpec).and(groupSpec);
        } else {
            specification = sortSpec.and(unExactSpec).and(groupSpec);
        }
        List<T> list = baseDao.findAll(specification);
        return list;
    }

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Specification specification = getExactQuery(property);
        if (specification != null) {
            specification = specification.and(getDefaultSpec());
        } else {
            specification = getDefaultSpec();
        }
        Page<T> page = baseDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification sortSpec;
        if (sortQueryProperty != null) {
            sortSpec = getSortQuery(sortQueryProperty);
        } else {
            sortSpec = getDefaultSpec();
        }
        if (specification != null) {
            specification = specification.and(unExactSpec).and(sortSpec);
        } else {
            specification = sortSpec.and(unExactSpec);
        }
        Page<T> page = baseDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty, Map<String, String> groupProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification groupSpec = getGroupQuery(groupProperty);
        Specification sortSpec;
        if (sortQueryProperty != null) {
            sortSpec = getSortQuery(sortQueryProperty);
        } else {
            sortSpec = getDefaultSpec();
        }
        if (specification != null) {
            specification = specification.and(unExactSpec).and(sortSpec).and(groupSpec);
        } else {
            specification = sortSpec.and(unExactSpec).and(groupSpec);
        }
        Page<T> page = baseDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    public long getCount() {
        return baseDao.count();
    }

    @Override
    public long getCount(Map<String, Object> property) {
        return baseDao.count(getExactQuery(property));
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty) {
        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        if (specification != null) {
            specification = specification.and(unExactSpec);
        } else {
            specification = unExactSpec;
        }
        return baseDao.count(specification);
    }

    @Override
    public long getCount(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, String> groupProperty) {
        Specification specification = getExactQuery(exactQueryProperty);
        Specification unExactSpec = getUnExactQuery(unExactQueryProperty);
        Specification groupSpec = getGroupQuery(groupProperty);
        if (specification != null) {
            specification = specification.and(unExactSpec).and(groupSpec);
        } else {
            specification = unExactSpec.and(groupSpec);
        }
        return baseDao.count(specification);
    }

    /**
     * 多条件精确查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:13
     */
    protected Specification getExactQuery(Map<String, Object> queryProperty) {
        if (MapUtils.isEmpty(queryProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (queryProperty != null) {
                for (String key : queryProperty.keySet()) {
                    if (queryProperty.get(key) == null) {
                        predicates.add(criteriaBuilder.isNull(root.get(key)));
                        continue;
                    }
                    if (queryProperty.get(key) instanceof String) {
                        if (queryProperty.get(key).toString().length() == 0) {
                            continue;
                        }
                    }
                    if (queryProperty.get(key) instanceof Collection) {
                        Expression<Collection> expression = root.join(key);
                        Predicate in = expression.in(queryProperty.get(key));
                        predicates.add(in);
                        continue;
                    }
                    predicates.add(criteriaBuilder.equal(root.get(key), queryProperty.get(key)));
                }
            }
            return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
        };
    }

    /**
     * 多条件模糊查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:09
     */
    protected Specification getUnExactQuery(Map<String, String> queryProperty) {
        if (MapUtils.isEmpty(queryProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (queryProperty != null) {
                for (String key : queryProperty.keySet()) {
                    if (queryProperty.get(key) == null || queryProperty.get(key).length() == 0) {
                        continue;
                    }
                    predicates.add(criteriaBuilder.like(root.get(key), "%" + queryProperty.get(key) + "%"));
                }
            }
            return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
        };
    }

    /**
     * 多条件排序查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:14
     */
    protected Specification getSortQuery(Map<String, Sort.Direction> sortProperty) {
        if (MapUtils.isEmpty(sortProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (sortProperty != null) {
                for (String key : sortProperty.keySet()) {
                    if (sortProperty.get(key) == null) {
                        continue;
                    }
                    if (sortProperty.get(key).isAscending()) {
                        Order order = criteriaBuilder.asc(root.get(key));
                        criteriaQuery.orderBy(order);
                    } else {
                        Order order = criteriaBuilder.desc(root.get(key));
                        criteriaQuery.orderBy(order);
                    }
                }
            }
            return (Predicate) criteriaQuery.getSelection();
        };
    }

    /**
     * 多条件分组查询
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 20:14
     */
    protected Specification getGroupQuery(Map<String, String> groupProperty) {
        if (MapUtils.isEmpty(groupProperty)) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (groupProperty != null) {
                for (String key : groupProperty.keySet()) {
                    if (groupProperty.get(key) == null) {
                        continue;
                    }
                    criteriaQuery.groupBy(root.get(key));
                }
            }
            return (Predicate) criteriaQuery.getSelection();
        };
    }

    /**
     * 默认Id降序
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/28 21:04
     */
    private Specification getDefaultSpec() {
        Specification specification = getSortQuery(new HashMap<String, Sort.Direction>(1) {
            private static final long serialVersionUID = -3371120713938289395L;

            {
                put("id", Sort.Direction.DESC);
            }
        });
        return specification;
    }
}
