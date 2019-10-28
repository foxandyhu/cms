package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.dao.IMemberGroupDao;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:20
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberGroupServiceImpl extends BaseServiceImpl<MemberGroup, Integer> implements IMemberGroupService {

    @Autowired
    private IMemberGroupDao memberGroupDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        for (Integer id : integers) {
            memberGroupDao.editMembersGroup(id, 0);
            memberGroupDao.clearArticleGroupViewShip(id);
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberGroup getDefaultMemberGroup() {
        return memberGroupDao.getDefaultMemberGroup();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDefaultMemberGroup(int groupId) {
        memberGroupDao.clearDefaultMemberGroup();
        MemberGroup group = get(groupId);
        Assert.notNull(group, "不存在的会员组!");
        group.setDefaults(true);
        return edit(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MemberGroup group) {
        if (group.isDefaults()) {
            // 如果新增的会员组设为默认会员组则先重置默认的会员组
            memberGroupDao.clearDefaultMemberGroup();
        }
        return super.save(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(MemberGroup group) {
        MemberGroup defaultGroup = getDefaultMemberGroup();
        if (group.isDefaults() && defaultGroup.getId() != group.getId()) {
            // 如果编辑的会员组设为默认会员组则先重置默认的会员组
            memberGroupDao.clearDefaultMemberGroup();
        }
        return super.edit(group);
    }
}
