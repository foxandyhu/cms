package com.bfly.cms.vote.service.impl;

import com.bfly.cms.vote.dao.IVoteRecordDao;
import com.bfly.cms.vote.entity.VoteRecord;
import com.bfly.cms.vote.service.IVoteRecordService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2019/9/11 22:38
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VoteRecordService extends BaseServiceImpl<VoteRecord, Integer> implements IVoteRecordService {

    @Autowired
    private IVoteRecordDao voteRecordDao;

    @Override
    public void save(int memberId, int voteTopicId, String cookie) {
        VoteRecord record = new VoteRecord();
        record.setMemberId(memberId);
        record.setVoteTopicId(voteTopicId);
        record.setTime(new Date());
        record.setIp(IpThreadLocal.get());
        record.setCookie(cookie);
        save(record);
    }

    @Override
    public long getRecordCount(int memberId, int voteTopicId, Date date) {
        return voteRecordDao.getRecordCount(memberId, voteTopicId, date);
    }

    @Override
    public long getRecordCount(String cookie, int voteTopicId, Date date) {
        return voteRecordDao.getRecordCount(cookie, voteTopicId, date);
    }
}
