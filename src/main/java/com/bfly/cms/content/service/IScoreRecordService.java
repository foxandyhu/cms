package com.bfly.cms.content.service;

import com.bfly.cms.content.entity.ScoreRecord;
import com.bfly.core.base.service.IBaseService;

/**
 * 评分记录业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/8 17:17
 */
public interface IScoreRecordService extends IBaseService<ScoreRecord, Integer> {

    /**
     * 新增评分记录
     * @author andy_hulibo@163.com
     * @date 2019/9/8 17:18
     */
    void saveScoreRecord(int articleId, int scoreItemId);
}
