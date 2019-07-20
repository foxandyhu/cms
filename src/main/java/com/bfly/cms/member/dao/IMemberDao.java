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
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:42
     */
    @Modifying
    @Query("update Member set status=:status where id=:memberId")
    int editMemberStatus(@Param("memberId") int memberId, @Param("status") int status);

    /**
     * 修改会员账户密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:27
     */
    @Modifying
    @Query("update Member set password=:password,salt=:salt where id=:memberId")
    int editMemberPassword(@Param("memberId") int memberId, @Param("password") String password, @Param("salt") String salt);
}
