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
     * 重置会员组ID
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:08
     */
    @Modifying
    @Query(value = "UPDATE member set group_id=:targetGroupId where group_id=:sourceGroupId", nativeQuery = true)
    int resetMembersGroup(@Param("sourceGroupId") int sourceGroupId, @Param("targetGroupId") int targetGroupId);

    /**
     * 清除栏目浏览权限和会员组之间的关系
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:16
     */
    @Modifying
    @Query(value = "delete from channel_member_group_view_ship where group_id=:groupId", nativeQuery = true)
    int clearChannelGroupViewShip(@Param("groupId") int groupId);

    /**
     * 清除栏目投稿权限和会员组之间的管理
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:22
     */
    @Modifying
    @Query(value = "delete from channel_member_group_contri_ship where group_id=:groupId", nativeQuery = true)
    int clearChannelGroupContriShip(@Param("groupId") int groupId);
}
