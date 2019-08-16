package com.bfly.cms.member.dao;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 会员数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:21
 */
public interface IMemberDao extends IBaseDao<Member, Integer> {

    /**
     * 修改会员账户状态
     *
     * @param memberId 会员ID
     * @param status   状态
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:42
     */
    @Modifying
    @Query("update Member set status=:status where id=:memberId")
    int editMemberStatus(@Param("memberId") int memberId, @Param("status") int status);

    /**
     * 修改会员账户密码
     *
     * @param memberId 会员ID
     * @param password 密码
     * @param salt     密码盐
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:27
     */
    @Modifying
    @Query("update Member set password=:password,salt=:salt where id=:memberId")
    int editMemberPassword(@Param("memberId") int memberId, @Param("password") String password, @Param("salt") String salt);

    /**
     * 根据用户名查找用户信息
     *
     * @param userName 用户名
     * @return 用户对象
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:33
     */
    @Query("select m from Member as m where m.userName=:userName")
    Member getMember(@Param("userName") String userName);

    /**
     * 统计当天会员注册总数和总会员数
     *
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    @Query(value = "select count(1) as total,temp.today FROM member,(select count(1) as today from member where date_format(register_time, '%Y-%m-%d')=date_format(NOW(), '%Y-%m-%d')) as temp", nativeQuery = true)
    Map<String, BigInteger> getTodayAndTotalMember();

    /**
     * 获得最新注册的前几名会员
     *
     * @param limit
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:17
     */
    @Query(value = "select m_ext.face,user_name as userName,register_time as registerTime,register_ip as registerIp,`status` from member as m LEFT JOIN member_ext as m_ext on m.id=m_ext.member_id order by register_time DESC LIMIT 0,:limit", nativeQuery = true)
    List<Map<String, Object>> getLatestMember(@Param("limit") int limit);
}
