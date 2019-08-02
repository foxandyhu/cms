package com.bfly.cms.member.dao;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
