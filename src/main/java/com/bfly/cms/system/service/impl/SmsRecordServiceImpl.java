package com.bfly.cms.system.service.impl;

import com.bfly.cms.message.entity.Message;
import com.bfly.cms.system.dao.ISmsRecordDao;
import com.bfly.cms.system.entity.SmsRecord;
import com.bfly.cms.system.service.ISmsRecordService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmsRecordServiceImpl extends BaseServiceImpl<SmsRecord, Integer> implements ISmsRecordService {

    @Autowired
    private ISmsRecordDao smsRecordDao;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        String beginSendTime = "beginTime", endSendTime = "endTime";
        Object beginTime = property == null ? null : property.get(beginSendTime), endTime = property == null ? null : property.get(endSendTime);
        if (property != null) {
            property.remove(beginSendTime);
            property.remove(endSendTime);
        }
        Specification specification = getExactQuery(property);
        if (specification != null) {
            specification.and((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (beginTime != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sendTime"), String.valueOf(property.get(beginSendTime))));
                }
                if (endTime != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sendTime"), String.valueOf(property.get(endSendTime))));
                }
                return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
            });
        }
        Page<Message> page = smsRecordDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }
}
