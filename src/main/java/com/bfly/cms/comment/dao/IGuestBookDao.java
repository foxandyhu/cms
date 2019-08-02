package com.bfly.cms.comment.dao;

import com.bfly.cms.comment.entity.GuestBook;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:48
 */
public interface IGuestBookDao extends IBaseDao<GuestBook, Integer> {

    /**
     * 修改留言状态
     *
     * @param status      状态
     * @param guestBookId 留言ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/2 20:53
     */
    @Modifying
    @Query("UPDATE GuestBook set status=:status where id=:guestBookId")
    int editGuestBookStatus(@Param("guestBookId") int guestBookId, @Param("status") int status);

    /**
     * 修改留言的推荐状态
     *
     * @param guestBookId 留言ID
     * @param recommend   是否推荐
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/2 20:55
     */
    @Modifying
    @Query("UPDATE GuestBook set recommend=:recommend where id=:guestBookId")
    int editGuestBookRecommend(@Param("guestBookId") int guestBookId, @Param("recommend") boolean recommend);
}
