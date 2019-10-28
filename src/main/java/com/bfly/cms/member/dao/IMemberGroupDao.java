package com.bfly.cms.member.dao;

import com.bfly.cms.member.entity.MemberGroup;
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
public interface IMemberGroupDao extends IBaseDao<MemberGroup, Integer> {

    /**
     * 更新会员的组Id
     * 把所属sourceGroupId 的会员组ID更新为targetGroupId
     *
     * @param sourceGroupId 原组ID
     * @param targetGroupId 要修改的组ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:08
     */
    @Modifying
    @Query(value = "UPDATE member set group_id=:targetGroupId where group_id=:sourceGroupId", nativeQuery = true)
    int editMembersGroup(@Param("sourceGroupId") int sourceGroupId, @Param("targetGroupId") int targetGroupId);

    /**
     * 清除文章浏览权限和会员组之间的管理
     * @param groupId 会员组ID
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:22
     */
    @Modifying
    @Query(value = "delete from article_group_view where group_id=:groupId", nativeQuery = true)
    int clearArticleGroupViewShip(@Param("groupId") int groupId);

    /**
     * 获得默认的会员组
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:43
     */
    @Query("select group from MemberGroup as group where defaults=true")
    MemberGroup getDefaultMemberGroup();

    /**
     * 清空所有默认会员组
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:46
     */
    @Modifying
    @Query("update MemberGroup set defaults=false where defaults=true")
    int clearDefaultMemberGroup();
}
