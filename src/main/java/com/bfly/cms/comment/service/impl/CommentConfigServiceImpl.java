package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.entity.CommentConfig;
import com.bfly.cms.comment.service.ICommentConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:04
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CommentConfigServiceImpl extends BaseServiceImpl<CommentConfig, Integer> implements ICommentConfigService {


    @Override
    public CommentConfig getCommentConfig() {
        List<CommentConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(CommentConfig commentConfig) {
        CommentConfig config = getCommentConfig();
        if (config == null) {
            return super.save(commentConfig);
        }
        commentConfig.setId(config.getId());
        return super.edit(commentConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CommentConfig commentConfig) {
        return edit(commentConfig);
    }
}