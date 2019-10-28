package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.dao.ISmsRecordDao;
import com.bfly.cms.system.entity.SmsProvider;
import com.bfly.cms.system.entity.SmsRecord;
import com.bfly.cms.system.service.ISmsRecordService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.PagerThreadLocal;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SmsRecordServiceImpl extends BaseServiceImpl<SmsRecord, Integer> implements ISmsRecordService {

    @Autowired
    private ISmsRecordDao smsRecordDao;

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        String beginSendTime = "beginTime", endSendTime = "endTime";
        Object beginTime = exactQueryProperty == null ? null : exactQueryProperty.get(beginSendTime), endTime = exactQueryProperty == null ? null : exactQueryProperty.get(endSendTime);
        if (exactQueryProperty != null) {
            exactQueryProperty.remove(beginSendTime);
            exactQueryProperty.remove(endSendTime);
        }
        Specification specification = getExactQuery(exactQueryProperty);
        if (specification != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>(2);
                if (beginTime != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("sendTime"), (Date) beginTime));
                }
                if (endTime != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("sendTime"), (Date) endTime));
                }
                if (predicates.size() == 0) {
                    return null;
                }
                return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
            });
            specification = specification.and(getUnExactQuery(unExactQueryProperty));
        } else {
            specification = getUnExactQuery(unExactQueryProperty);
        }
        Page<SmsRecord> page = smsRecordDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(pager.getPageNo(), pager.getPageSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resend(int recordId) {
        SmsRecord record = get(recordId);
        Assert.notNull(record, "短信记录不存在!");

        SmsProvider provider = record.getProvider();
        Assert.notNull(provider, "该记录没有对应的服务商信息!");
        //此处应该调用服务商API发送短信

        //修改发送次数
        record.setCount(record.getCount() + 1);
        record.setSendTime(new Date());
        return edit(record);
    }
}
