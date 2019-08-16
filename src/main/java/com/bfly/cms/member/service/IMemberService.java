package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.MemberStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户名查找用户信息
     *
     * @param userName 用户名
     * @return 用户对象
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:32
     */
    Member getMember(String userName);

    /**
     * 统计当天会员注册总数和总会员数
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    Map<String, BigInteger> getTodayAndTotalMember();

    /**
     * 获得最新注册的前几名会员
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:17
     */
    List<Map<String, Object>> getLatestMember(int limit);
}
