package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.dao.IMessageDao;
import com.bfly.cms.message.entity.Message;
import com.bfly.cms.message.service.IMessageService;
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
 * @date 2018/12/11 15:57
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MessageServiceImpl extends BaseServiceImpl<Message, Integer> implements IMessageService {

    @Autowired
    private IMessageDao messageDao;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        String beginSendTime = "beginSendTime", endSendTime = "endSendTime";
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
        Page<Message> page = messageDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }
}
