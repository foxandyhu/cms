package com.bfly.core.base.service.impl;

import com.bfly.core.context.ContextUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.dao.IBaseDao;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public abstract class BaseServiceImpl<T, ID> implements IBaseService<T, ID> {

    private IBaseDao<T, ID> baseDao;

    @Autowired
    public void setBaseDao(IBaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T get(ID id) {
        return baseDao.getOne(id);
    }

    @Override
    public T get(Map<String, Object> property) {
        Optional<T> optional = baseDao.findOne(getSpecification(property, true));
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
        List<T> list = baseDao.findAll(getSpecification(property, false));
        return list;
    }

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Page<T> page = baseDao.findAll(getSpecification(property, false), getPageRequest(pager));
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
        return baseDao.count(getSpecification(property, false));
    }

    /**
     * 条件组合
     * 如果是模糊匹配Map的Value值必须不能为空否则将会被忽略
     * 当property参数为空或null时Specification返回null
     *
     * @param property   多条件
     * @param exactMatch 是否精确匹配
     * @author andy_hulibo@163.com
     * @date 2018/12/10 10:18
     */
    protected Specification getSpecification(Map<String, Object> property, boolean exactMatch) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (property == null || property.isEmpty()) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            for (String key : property.keySet()) {
                if (exactMatch) {
                    predicates.add(criteriaBuilder.equal(root.get(key), property.get(key)));
                } else {
                    if (property.get(key) == null) {
                        continue;
                    }
                    predicates.add(criteriaBuilder.like(root.get(key), "%" + property.get(key) + "%"));
                }
            }
            return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
        };
    }
}
