package com.bfly.cms.words.service.impl;

import com.bfly.cms.words.dao.ITagsDao;
import com.bfly.cms.words.entity.Tags;
import com.bfly.cms.words.service.ITagsService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:46
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TagsServiceImpl extends BaseServiceImpl<Tags, Integer> implements ITagsService {

    @Autowired
    private ITagsDao tagsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Tags tags) {
        long count = getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -8780499205785202697L;

            {
                put("name", tags.getName());
            }
        });
        Assert.isTrue(count == 0, tags.getName() + " 标签已存在!");
        tagsDao.save(tags);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Tags tags) {
        Tags tag = get(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = -8780499205785202697L;

            {
                put("name", tags.getName());
            }
        });
        boolean flag = tag != null && tags.getName().equalsIgnoreCase(tag.getName()) && tags.getId() != tag.getId();
        Assert.isTrue(!flag, tags.getName() + " 标签已存在!");
        tagsDao.save(tags);
        return true;
    }
}
