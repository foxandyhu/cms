package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.ScoreGroup;
import com.bfly.cms.content.entity.ScoreItem;
import com.bfly.cms.content.service.IScoreGroupService;
import com.bfly.cms.content.service.IScoreItemService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:54
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ScoreGroupServiceImpl extends BaseServiceImpl<ScoreGroup, Integer> implements IScoreGroupService {

    @Autowired
    private IScoreItemService itemService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ScoreGroup scoreGroup) {
        long count = getCount(new HashMap<String, Object>(1) {{
            put("name", scoreGroup.getName());
        }});
        Assert.isTrue(count == 0, "评分组名称已存在");
        return super.save(scoreGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(ScoreGroup scoreGroup) {
        ScoreGroup group = get(scoreGroup.getId());
        Assert.notNull(group, "评分组信息不存在!");

        long count = getCount(new HashMap<String, Object>(1) {{
            put("name", scoreGroup.getName());
        }});
        Assert.isTrue(count == 0 || group.getName().equals(scoreGroup.getName()), "评分组名称已存在!");
        return super.edit(scoreGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        for (Integer id : integers) {
            itemService.removeScoreItem(id);
        }
        return super.remove(integers);
    }
}
