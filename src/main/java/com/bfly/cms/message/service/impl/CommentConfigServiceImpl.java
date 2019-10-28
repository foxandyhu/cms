package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.dao.ICommentConfigDao;
import com.bfly.cms.message.entity.CommentConfig;
import com.bfly.cms.message.service.ICommentConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/9/15 13:30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CommentConfigServiceImpl extends BaseServiceImpl<CommentConfig, Integer> implements ICommentConfigService {

    @Autowired
    private ICommentConfigDao configDao;

    @Override
    public CommentConfig getConfig() {
        List<CommentConfig> list = configDao.findAll();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CommentConfig commentConfig) {
        CommentConfig config = getConfig();
        if (config != null) {
            commentConfig.setId(config.getId());
        }
        configDao.save(commentConfig);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(CommentConfig commentConfig) {
        return save(commentConfig);
    }
}
