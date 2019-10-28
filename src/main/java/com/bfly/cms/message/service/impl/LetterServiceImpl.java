package com.bfly.cms.message.service.impl;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.message.dao.ILetterDao;
import com.bfly.cms.message.entity.Letter;
import com.bfly.cms.message.entity.LetterTxt;
import com.bfly.cms.message.service.ILetterService;
import com.bfly.cms.message.service.ILetterTxtService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.context.UserThreadLocal;
import com.bfly.core.enums.LetterBox;
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
 * @date 2018/12/11 15:57
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class LetterServiceImpl extends BaseServiceImpl<Letter, Integer> implements ILetterService {

    @Autowired
    private ILetterDao letterDao;
    @Autowired
    private ILetterTxtService txtService;
    @Autowired
    private IMemberGroupService groupService;
    @Autowired
    private IMemberService memberService;

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        String beginSendTime = "beginSendTime", endSendTime = "endSendTime";
        Date begin = null, end = null;
        if (exactQueryProperty != null) {
            if (exactQueryProperty.containsKey(beginSendTime)) {
                begin = (Date) exactQueryProperty.get(beginSendTime);
            }
            if (exactQueryProperty.containsKey(endSendTime)) {
                end = (Date) exactQueryProperty.get(endSendTime);
            }
            exactQueryProperty.remove(beginSendTime);
            exactQueryProperty.remove(endSendTime);
        }
        Specification specification = getExactQuery(exactQueryProperty);
        if (specification != null) {
            final Date beginTime = begin;
            final Date endTime = end;
            if (beginTime != null || endTime != null) {
                specification = specification.and((root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (beginTime != null) {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("sendTime"), beginTime));
                    }
                    if (endTime != null) {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("sendTime"), endTime));
                    }
                    return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
                });
            }
            if (unExactQueryProperty != null) {
                specification = specification.and(getUnExactQuery(unExactQueryProperty));
            }
        } else {
            specification = getUnExactQuery(unExactQueryProperty);
        }
        if (specification == null) {
            specification = getSortQuery(sortQueryProperty);
        } else {
            specification = specification.and(getSortQuery(sortQueryProperty));
        }
        Page<Letter> page = letterDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(pager.getPageNo(), pager.getPageSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Letter letter) {
        Assert.hasLength(letter.getSender(), "发件人不能为空!");
        LetterTxt txt = letter.getLetterTxt();
        Assert.notNull(txt, "站内信数据为空!");
        Assert.hasLength(txt.getTitle(), "站内信标题为空!");
        Assert.hasLength(txt.getContent(), "站内信内容为空!");
        txtService.save(letter.getLetterTxt());

        //群发
        if (txt.getGroupId() != 0) {
            MemberGroup group = groupService.get(letter.getLetterTxt().getGroupId());
            Assert.notNull(group, "发送目标群组不存在!");
            letter.setReceiver("[群发组]" + group.getName());
            letter.setBox(LetterBox.SEND.getId());
            letter.setRead(false);
            letter.setSendTime(new Date());
            return super.save(letter);
        }

        //点对点 可以指定多个接收者 用","隔开用户ID
        Assert.hasLength(letter.getReceiver(), "接收者为空!");
        String[] receivers = letter.getReceiver().split(",");
        Assert.notEmpty(receivers, "接收者为空!");

        Date now = new Date();
        for (String receiverId : receivers) {
            Member member = memberService.get(DataConvertUtils.convertToInteger(receiverId));
            Assert.notNull(member, "接收者不存在!");

            Letter item = new Letter();
            item.setRead(false);
            item.setSendTime(now);
            item.setSender(letter.getSender());
            item.setFromAdmin(letter.isFromAdmin());
            item.setBox(LetterBox.RECEIVE.getId());
            item.setReceiver(member.getUserName());
            item.setLetterTxt(txt);
            super.save(item);
        }
        return true;
    }
}

