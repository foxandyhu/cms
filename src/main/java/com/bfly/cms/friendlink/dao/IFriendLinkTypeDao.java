package com.bfly.cms.friendlink.dao;

import com.bfly.cms.friendlink.entity.FriendLinkType;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
public interface IFriendLinkTypeDao extends IBaseDao<FriendLinkType, Integer> {

    /**
     * 修改友情链接类型排序
     *
     * @param seq    排序序号
     * @param typeId 友情链接类型ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/6 18:33
     */
    @Modifying
    @Query("update FriendLinkType set seq=:seq where id=:typeId")
    int editFriendLinkTypeSeq(@Param("typeId") int typeId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from friendlink_type", nativeQuery = true)
    int getMaxSeq();
}
