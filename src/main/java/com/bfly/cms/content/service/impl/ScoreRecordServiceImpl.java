package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IScoreRecordDao;
import com.bfly.cms.content.entity.ScoreRecord;
import com.bfly.cms.content.service.IScoreRecordService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:54
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ScoreRecordServiceImpl extends BaseServiceImpl<ScoreRecord, Integer> implements IScoreRecordService {

    @Autowired
    private IScoreRecordDao scoreRecordDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveScoreRecord(int articleId, int scoreItemId) {
        ScoreRecord record = scoreRecordDao.getScoreRecord(articleId, scoreItemId);
        if (record == null) {
            scoreRecordDao.saveScoreRecord(articleId, scoreItemId, 1);
        } else {
            scoreRecordDao.incrementScoreRecord(articleId, scoreItemId);
        }
    }
}
