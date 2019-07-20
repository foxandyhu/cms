package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.MemberStatus;

/**
 * 系统会员业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:38
 */
public interface IMemberService extends IBaseService<Member, Integer> {

    /**
     * 会员登录
     *
     * @param password 密码
     * @param username 用户名
     * @return 会员信息
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:18
     */
    Member login(String username, String password);

    /**
     * 修改会员状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:39
     */
    boolean editMemberStatus(int memberId, MemberStatus status);

    /**
     * 修改会员密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:22
     */
    boolean editMemberPassword(int memberId, String password);
}
