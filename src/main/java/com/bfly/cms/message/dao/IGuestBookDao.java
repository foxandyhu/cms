package com.bfly.cms.message.dao;

import com.bfly.cms.message.entity.GuestBook;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

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

    /**
     * 查询留言分页数据
     *
     * @param recommend 是否推荐
     * @param status    状态
     * @param typeId    类型ID
     * @param pageable  分页信息 JPA会自动识别 不需要在sql中写分页语句
     * @return 分页对象
     * @author andy_hulibo@163.com
     * @date 2019/8/4 17:48
     */
    @Query(value = "SELECT gb.id,gb.post_user_name as postUserName,gb.reply_user_name as replyUserName,gb.post_date as postDate,gb.reply_date as replyDate,gb.`status`,gb.is_recommend as recommend,gb.is_reply as reply,gb_ext.ip,gb_ext.area,gb_ext.title,gb_ext.content,gb_ext.email,gb_ext.phone,gb_ext.qq,gb_ext.reply_content as replyContent,gb_ext.reply_ip as replyIp,dir.`name` as typeName FROM GUESTBOOK as gb LEFT JOIN GUESTBOOK_EXT as gb_ext ON gb.id=gb_ext.guest_book_id LEFT JOIN D_DICTIONARY as dir ON gb.type_id=dir.`value` WHERE dir.type='" + GuestBook.GUESTBOOK_TYPE_DIR + "' AND (status=:status or :status is null) AND (is_recommend=:recommend or :recommend is null) AND (type_id=:typeId or :typeId is null) ORDER BY gb.post_date desc",
            countQuery = "SELECT count(1) FROM GUESTBOOK as gb LEFT JOIN GUESTBOOK_EXT as gb_ext ON gb.id=gb_ext.guest_book_id LEFT JOIN D_DICTIONARY as dir ON gb.type_id=dir.`value` WHERE dir.type='" + GuestBook.GUESTBOOK_TYPE_DIR + "' AND (status=:status or :status is null) AND (is_recommend=:recommend or :recommend is null) AND (type_id=:typeId or :typeId is null)", nativeQuery = true)
    Page<Map<String, Object>> getGuestBookPage(@Param("status") Integer status, @Param("recommend") Boolean recommend, @Param("typeId") Integer typeId, Pageable pageable);
}
