package com.bfly.cms.sms.dao.impl;

import com.bfly.cms.sms.dao.CmsSmsRecordDao;
import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.common.page.Pagination;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import com.bfly.core.base.dao.impl.Finder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:46
 */
@Repository
public class CmsSmsRecordDaoImpl extends AbstractHibernateBaseDao<CmsSmsRecord, Integer> implements CmsSmsRecordDao {

    @Override
    public List<CmsSmsRecord> getList(Integer smsId) {
        Finder f = Finder.create(" from CmsSmsRecord bean where 1=1");
        if (smsId != null) {
            f.append(" and bean.sms.id=:smsId").setParam("smsId", smsId);
        }
        f.append(" order by bean.sendTime desc");
        return find(f);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CmsSmsRecord> findByPhone(String phone, Date startTime, Date endTime) {
        Finder f = Finder.create(" from CmsSmsRecord bean where 1=1");
        f.append(" and bean.phone=:phone").setParam("phone", phone);
        f.append(" and bean.sendTime between :startTime and :endTime");
        f.setParam("startTime", startTime);
        f.setParam("endTime", endTime);
        f.append(" order by bean.sendTime desc");
        return find(f);
    }

    @Override
    public CmsSmsRecord findById(Integer id) {
        return get(id);
    }

    @Override
    public CmsSmsRecord save(CmsSmsRecord bean) {
        getSession().save(bean);
        return bean;
    }

    @Override
    public CmsSmsRecord deleteById(Integer id) {
        CmsSmsRecord entity = super.get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
        return entity;
    }

    @Override
    protected Class<CmsSmsRecord> getEntityClass() {
        return CmsSmsRecord.class;
    }

    @Override
    public Pagination getPage(Byte sms, int pageNo, int pageSize, String phone, Integer validateType,
                              String username, Date drawTimeBegin, Date drawTimeEnd) {
        Finder f = Finder.create(" from CmsSmsRecord bean where 1=1");
        if (sms != null) {
            f.append(" and bean.sms.source=:sms").setParam("sms", sms);
        }
        if (StringUtils.isNotBlank(phone)) {
            f.append(" and bean.phone=:phone").setParam("phone", phone);
        }
        if (validateType != null) {
            f.append(" and bean.validateType=:validateType").setParam("validateType", validateType);
        }
        if (StringUtils.isNotBlank(username)) {
            f.append(" and bean.user.username=:username").setParam("username", username);
        }
        if (drawTimeBegin != null && drawTimeEnd != null) {
            f.append(" and bean.sendTime >= :drawTimeBegin").setParam("drawTimeBegin", drawTimeBegin);
            f.append(" and bean.sendTime <= :drawTimeEnd").setParam("drawTimeEnd", drawTimeEnd);
        }
        f.append(" order by bean.sendTime desc");
        return find(f, pageNo, pageSize);
    }

}
