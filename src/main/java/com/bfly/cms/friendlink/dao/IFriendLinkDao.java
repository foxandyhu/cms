package com.bfly.cms.friendlink.dao;

import com.bfly.cms.friendlink.entity.FriendLink;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
public interface IFriendLinkDao extends IBaseDao<FriendLink, Integer> {

    /**
     * 修改友情链接排序
     *
     * @param seq          排序序号
     * @param friendLinkId 友情链接ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/6 18:33
     */
    @Modifying
    @Query("update FriendLink set seq=:seq where id=:friendLinkId")
    int editFriendLinkSeq(@Param("friendLinkId") int friendLinkId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from friendlink", nativeQuery = true)
    int getMaxSeq();

    /**
     * 查询评论分页数据
     *
     * @param typeId   类型
     * @param pageable 分页信息 JPA会自动识别 不需要在sql中写分页语句
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/8/4 17:15
     */
    @Query(value = "SELECT fl.id,fl.type_id as typeId,fl.`name`,fl.url,fl.logo,fl.is_enabled as enabled,fl.remark,fl.seq,flt.`name` as typeName FROM friendlink as fl LEFT JOIN friendlink_type as flt on(fl.type_id=flt.id) where (type_id=:typeId or :typeId is null) order by seq asc",
            countQuery = "SELECT count(1) FROM friendlink as fl LEFT JOIN friendlink_type as flt on(fl.type_id=flt.id) where (type_id=:typeId or :typeId is null)", nativeQuery = true)
    Page<Map<String, Object>> getFriendLinkPage(@Param("typeId") Integer typeId, Pageable pageable);
}
