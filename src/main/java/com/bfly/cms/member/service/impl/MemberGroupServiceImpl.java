package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.dao.IMemberDao;
import com.bfly.cms.member.dao.IMemberGroupDao;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.member.service.IMemberGroupService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            memberGroupDao.resetMembersGroup(id, 0);
            memberGroupDao.clearChannelGroupContriShip(id);
            memberGroupDao.clearChannelGroupViewShip(id);
        }
        return super.remove(integers);
    }
}
