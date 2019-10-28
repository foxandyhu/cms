package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.core.base.service.IBaseService;

/**
 * 会员组业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:16
 */
public interface IMemberGroupService extends IBaseService<MemberGroup, Integer> {

    /**
     * 得到默认会员组
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:47
     */
    MemberGroup getDefaultMemberGroup();

    /**
     * 设置默认会员组
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:47
     */
    boolean setDefaultMemberGroup(int groupId);
}
