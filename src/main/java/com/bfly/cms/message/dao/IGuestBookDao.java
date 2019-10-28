package com.bfly.cms.message.dao;

import com.bfly.cms.message.entity.GuestBook;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
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
    @Query(value = "SELECT gb.id,m_ext.face,gb.post_user_name as postUserName,gb.reply_user_name as replyUserName,gb.post_date as postDate,gb.reply_date as replyDate,gb.`status`,gb.is_recommend as recommend,gb.is_reply as reply,gb_ext.ip,gb_ext.area,gb_ext.title,gb_ext.content,gb_ext.email,gb_ext.phone,gb_ext.qq,gb_ext.reply_content as replyContent,gb_ext.reply_ip as replyIp,dir.`name` as typeName FROM GUESTBOOK as gb LEFT JOIN GUESTBOOK_EXT as gb_ext ON gb.id=gb_ext.guest_book_id LEFT JOIN D_DICTIONARY as dir ON gb.type_id=dir.`value` LEFT JOIN member as m on gb.post_user_name=m.user_name LEFT JOIN member_ext as m_ext on m.id=m_ext.member_id WHERE dir.type='" + GuestBook.GUESTBOOK_TYPE_DIR + "' AND (gb.status=:status or :status is null) AND (is_recommend=:recommend or :recommend is null) AND (type_id=:typeId or :typeId is null) ORDER BY gb.post_date desc",
            countQuery = "SELECT count(1) FROM GUESTBOOK as gb LEFT JOIN GUESTBOOK_EXT as gb_ext ON gb.id=gb_ext.guest_book_id LEFT JOIN D_DICTIONARY as dir ON gb.type_id=dir.`value` WHERE dir.type='" + GuestBook.GUESTBOOK_TYPE_DIR + "' AND (status=:status or :status is null) AND (is_recommend=:recommend or :recommend is null) AND (type_id=:typeId or :typeId is null)", nativeQuery = true)
    Page<Map<String, Object>> getGuestBookPage(@Param("status") Integer status, @Param("recommend") Boolean recommend, @Param("typeId") Integer typeId, Pageable pageable);

    /**
     * 统计当天留言总数和总留言数
     *
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    @Query(value = "select count(1) as total,temp.today FROM guestbook,(select count(1) as today from guestbook where date_format(post_date, '%Y-%m-%d')=date_format(NOW(), '%Y-%m-%d')) as temp;", nativeQuery = true)
    Map<String, BigInteger> getTodayAndTotalGuestBook();

    /**
     * 获得最新的前几条留言
     *
     * @param limit 返回最大条数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:21
     */
    @Query(value = "select m_ext.face,g.post_user_name as userName,g.post_date as postDate,g_ext.ip,g_ext.content,g_ext.area,g.`status` from guestbook as g LEFT JOIN guestbook_ext as g_ext on g.id=g_ext.guest_book_id LEFT JOIN member as m on m.user_name=g.post_user_name LEFT JOIN member_ext as m_ext on m.id=m_ext.member_id WHERE g.post_user_name is not NULL order by g.post_date DESC LIMIT 0,:limit",nativeQuery = true)
    List<Map<String, Object>> getLatestGuestBook(int limit);
}
