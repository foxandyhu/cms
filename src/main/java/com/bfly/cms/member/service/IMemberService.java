package com.bfly.cms.member.service;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberExt;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.LoginType;
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
     * @param password   密码
     * @param username   用户名
     * @param type       登录方式
     * @param isRemember
     * @return 会员信息
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:18
     */
    Member login(String username, String password, boolean isRemember, LoginType type);

    /**
     * 会员注册
     *
     * @param member
     * @param isFromDynamicMobile 是否手机动态密码
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/18 11:07
     */
    Member register(Member member, boolean isFromDynamicMobile);

    /**
     * 重新发送账户激活邮件
     *
     * @param userName 重新发送账户激活邮件
     * @author andy_hulibo@163.com
     * @date 2019/9/25 13:27
     */
    void resendActiveMail(String userName);

    /**
     * 会员登出
     *
     * @param userName
     * @author andy_hulibo@163.com
     * @date 2019/9/6 17:14
     */
    void logout(String userName);

    /**
     * 修改会员状态
     *
     * @param memberId 会员ID
     * @param status   状态
     * @return true/false
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:39
     */
    boolean editMemberStatus(int memberId, MemberStatus status);

    /**
     * 修改会员密码
     *
     * @param memberId 会员ID
     * @param password 密码
     * @return true/false
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:22
     */
    boolean editMemberPassword(int memberId, String password);

    /**
     * 修改密码
     * @author andy_hulibo@163.com
     * @date 2019/10/21 18:00
     */
    boolean editMemberPassword(String userName,String password);

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
     * 根据sessionId 查找用户
     *
     * @param sessionId
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/18 12:44
     */
    Member getMemberBySession(String sessionId);

    /**
     * 统计当天会员注册总数和总会员数
     *
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    Map<String, BigInteger> getTodayAndTotalMember();

    /**
     * 获得最新注册的前几名会员
     *
     * @param limit 前几名
     * @return List
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:17
     */
    List<Map<String, Object>> getLatestMember(int limit);

    /**
     * 清除登录错误数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/18 17:09
     */
    void clearMemberLoginError();

    /**
     * 编辑Member扩展信息
     *
     * @param ext
     * @author andy_hulibo@163.com
     * @date 2019/9/19 11:21
     */
    void editMemberExtInfo(MemberExt ext);

    /**
     * 用户上传base64编码的头像
     *
     * @param userName
     * @param base64FaceData
     * @author andy_hulibo@163.com
     * @date 2019/9/19 20:20
     */
    void uploadMemberFace(String userName, String base64FaceData);

    /**
     * 用户邮箱激活
     *
     * @param certifyId
     * @param userName
     * @author andy_hulibo@163.com
     * @date 2019/9/24 14:48
     */
    void activeMemberForMail(String userName, String certifyId);

    /**
     * 通过邮件找回密码
     * @param userName 用户名
     * @author andy_hulibo@163.com
     * @date 2019/10/21 15:12
     */
    void forgetPwdForMail(String userName);
}
