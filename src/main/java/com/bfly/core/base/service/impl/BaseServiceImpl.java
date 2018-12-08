package com.bfly.core.base.service.impl;

import com.bfly.core.base.dao.IBaseDao;
import com.bfly.core.base.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
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
        Optional<T> optional = baseDao.findOne(getSpecification(property));
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int del(ID... ids) {
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
    public long getCount() {
        return baseDao.count();
    }

    @Override
    public long getCount(Map<String, Object> property) {
        return baseDao.count(getSpecification(property));
    }

    private Specification getSpecification(Map<String, Object> property) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate[] predicates = new Predicate[property.size()];
            int index = 0;
            for (String key : property.keySet()) {
                predicates[index] = criteriaBuilder.equal(root.get(key), property.get(key));
                index++;
            }
            return criteriaBuilder.and(predicates);
        };
    }
}
