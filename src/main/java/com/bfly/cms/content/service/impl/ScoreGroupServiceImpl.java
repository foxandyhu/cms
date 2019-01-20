package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.ScoreGroup;
import com.bfly.cms.content.service.IScoreGroupService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:54
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ScoreGroupServiceImpl extends BaseServiceImpl<ScoreGroup, Integer> implements IScoreGroupService {

}
