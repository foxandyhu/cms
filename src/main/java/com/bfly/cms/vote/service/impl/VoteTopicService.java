package com.bfly.cms.vote.service.impl;

import com.bfly.cms.vote.dao.IVoteTopicDao;
import com.bfly.cms.vote.entity.VoteItem;
import com.bfly.cms.vote.entity.VoteSubTopic;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.enums.VoteStatus;
import com.bfly.core.enums.VoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:04
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VoteTopicService extends BaseServiceImpl<VoteTopic, Integer> implements IVoteTopicService {

    @Autowired
    private IVoteTopicDao voteTopicDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(VoteTopic voteTopic) {
        Date now = new Date();
        if (voteTopic.getStartTime().after(now)) {
            voteTopic.setStatus(VoteStatus.NO_START.getId());
        } else if (voteTopic.getStartTime().before(now) && voteTopic.getEndTime().after(now)) {
            voteTopic.setStatus(VoteStatus.PROCESSING.getId());
        } else if (voteTopic.getEndTime().before(now)) {
            voteTopic.setStatus(VoteStatus.FINISHED.getId());
        }
        List<VoteSubTopic> subTopics = voteTopic.getSubtopics();
        subTopics.forEach(voteSubTopic -> {
            VoteType voteType = VoteType.getType(voteSubTopic.getType());
            Assert.notNull(voteType, "调查问卷子主题类型错误");

            if (voteSubTopic.getType() == VoteType.TEXT.getId()) {
                voteSubTopic.setVoteItems(null);
            } else {
                List<VoteItem> items = voteSubTopic.getVoteItems();
                Assert.notEmpty(items, "子主题答案选项不能为空!");

                items.forEach(item -> {
                    if (StringUtils.hasLength(item.getPicture())) {
                        String path = ResourceConfig.getUploadTempFileToDestDirForRelativePath(item.getPicture(), ResourceConfig.getVoteTopicDir());
                        item.setPicture(path);
                        item.setVoteCount(0);
                    }
                });
            }
        });
        voteTopic.setTotalCount(0);
        voteTopic.setEnabled(true);
        return super.save(voteTopic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setEnableVoteTopic(int voteTopicId, boolean enable) {
        VoteTopic topic = get(voteTopicId);
        Assert.isTrue(topic.getStatus() != VoteStatus.FINISHED.getId(), "已结束的问卷调查不能修改!");
        Assert.notNull(topic, "问卷调查信息不存在!");

        topic.setEnabled(enable);
        voteTopicDao.save(topic);
    }
}
