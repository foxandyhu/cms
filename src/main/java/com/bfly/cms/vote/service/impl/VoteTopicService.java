package com.bfly.cms.vote.service.impl;

import com.bfly.cms.message.entity.Message;
import com.bfly.cms.vote.dao.IVoteTopicDao;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DateUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:04
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class VoteTopicService extends BaseServiceImpl<VoteTopic, Integer> implements IVoteTopicService {

    @Autowired
    private IVoteTopicDao voteTopicDao;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = ContextUtil.getPager();
        String statusStr = "status";
        Specification specification = getSpecification(property, false);
        if (specification != null) {
            specification.and((root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (property.containsKey(statusStr) && property.get(statusStr) != null) {
                    int status = (int) property.get("status");
                    switch (status) {
                        //未开始 开始时间大于当前时间
                        case 1:
                            predicates.add(criteriaBuilder.greaterThan(root.get("startTime"), String.valueOf(DateUtil.getCurrentDateTime())));
                            break;
                        //进行中 结束时间大于或等于当前时间
                        case 2:
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), String.valueOf(DateUtil.getCurrentDateTime())));
                            break;
                        //已结束 结束时间小于当前时间
                        case 3:
                            predicates.add(criteriaBuilder.lessThan(root.get("endTime"), String.valueOf(DateUtil.getCurrentDateTime())));
                            break;
                        default:
                    }
                }
                return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
            });
        }
        Page<Message> page = voteTopicDao.findAll(specification, getPageRequest(pager));
        pager = new Pager(page.getNumber(), page.getSize(), page.getTotalElements());
        pager.setData(page.getContent());
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultVoteTopic(int voteTopicId) {
        VoteTopic topic = get(voteTopicId);
        Assert.notNull(topic, "问卷调查信息不存在!");
        topic.setDef(true);
        //先清除默认的问卷主题然后设置新的默认问卷主题
        voteTopicDao.clearDefVoteTopic();
        voteTopicDao.save(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setEnableVoteTopic(int voteTopicId, boolean enable) {
        VoteTopic topic = get(voteTopicId);
        Assert.notNull(topic, "问卷调查信息不存在!");

        topic.setDisabled(!enable);
        voteTopicDao.save(topic);
    }
}
