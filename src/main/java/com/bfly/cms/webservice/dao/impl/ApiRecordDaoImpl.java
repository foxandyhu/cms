package com.bfly.cms.webservice.dao.impl;

import com.bfly.cms.webservice.dao.ApiRecordDao;
import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 10:33
 */
@Repository
public class ApiRecordDaoImpl extends AbstractHibernateBaseDao<ApiRecord, Long> implements ApiRecordDao {

    @Override
    public Pagination getPage(int pageNo, int pageSize) {
        Criteria c = createCriteria();
        return findByCriteria(c, pageNo, pageSize);
    }

    @Override
    public ApiRecord findBySign(String sign, String appId) {
        String hql = "from ApiRecord bean where bean.sign=:sign and bean.apiAccount.appId=:appId";
        Finder f = Finder.create(hql).setParam("sign", sign).setParam("appId", appId);
        List<ApiRecord> li = find(f);
        return li.size() > 0 ? li.get(0) : null;
    }

    @Override
    public ApiRecord findById(Long id) {
        return get(id);
    }

    @Override
    public ApiRecord save(ApiRecord bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public ApiRecord deleteById(Long id) {
        ApiRecord entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<ApiRecord> getEntityClass() {
        return ApiRecord.class;
    }
}