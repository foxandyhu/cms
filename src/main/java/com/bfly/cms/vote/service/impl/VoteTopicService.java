package com.bfly.cms.vote.service.impl;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.vote.dao.IVoteTopicDao;
import com.bfly.cms.vote.entity.VoteItem;
import com.bfly.cms.vote.entity.VoteSubTopic;
import com.bfly.cms.vote.entity.VoteTopic;
import com.bfly.cms.vote.entity.dto.VoteSubmitDTO;
import com.bfly.cms.vote.service.IVoteRecordService;
import com.bfly.cms.vote.service.IVoteTopicService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.MemberThreadLocal;
import com.bfly.core.enums.VoteStatus;
import com.bfly.core.enums.VoteType;
import com.bfly.core.exception.UnAuthException;
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
    @Autowired
    private IVoteRecordService voteRecordService;

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

    @Override
    public long getVoteReplyCount(int voteSubTopicId) {
        return voteTopicDao.getVoteReplyCount(voteSubTopicId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void voteSubmit(int voteTopicId, List<VoteSubmitDTO> dtos, String cookie) {
        if (dtos == null || dtos.isEmpty()) {
            return;
        }
        checkIsVoted(voteTopicId, cookie);
        boolean hasVote = false;
        for (VoteSubmitDTO dto : dtos) {
            List<Integer> itemIds = dto.getItems();
            if (itemIds != null && !itemIds.isEmpty()) {
                hasVote = true;
                itemIds.forEach(itemId -> voteTopicDao.voteSubmitItem(voteTopicId, dto.getSubTopicId(), itemId));
            } else {
                if (StringUtils.hasLength(dto.getText())) {
                    hasVote = true;
                    voteTopicDao.voteSubmitReply(dto.getSubTopicId(), dto.getText());
                }
            }
        }
        if (hasVote) {
            // 已经投票完成了 记录投票记录
            Member member = MemberThreadLocal.get();
            voteTopicDao.incrementVoteTotal(voteTopicId);
            voteRecordService.save(member == null ? 0 : member.getId(), voteTopicId, cookie);
        }
    }

    /**
     * 检查投票的条件
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:28
     */
    private void checkIsVoted(int voteTopicId, String cookie) {
        VoteTopic vote = get(voteTopicId);

        Assert.notNull(vote, "不存在的问卷调查!");
        Assert.isTrue(vote.isEnabled(), "该问卷调查未开启!");
        Assert.isTrue(vote.getStatus() != VoteStatus.FINISHED.getId(), "该问卷调查已结束!");
        Assert.isTrue(vote.getStatus() != VoteStatus.NO_START.getId(), "该问卷调查未开始!");
        Member member = null;
        if (vote.isNeedLogin()) {
            member = MemberThreadLocal.get();
            if (member == null) {
                throw new UnAuthException("未登录!");
            }
        }

        long recordCount = 0;
        if (member == null) {
            recordCount = voteRecordService.getRecordCount(cookie, voteTopicId, new Date());
        } else {
            recordCount = voteRecordService.getRecordCount(member.getId(), voteTopicId, new Date());
        }
        if (vote.getRepeatHour() == 0) {
            // 禁止重复投票
            Assert.isTrue(recordCount < 1, "您已经投过票了!");
        } else if (vote.getRepeatHour() > 0) {
            // 指定投票次数
            Assert.isTrue(recordCount < vote.getRepeatHour(), "您投票次数已满了!");
        } else {
            // 无限制投票
        }
    }
}
