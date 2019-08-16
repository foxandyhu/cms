package com.bfly.cms.message.service;

import com.bfly.cms.message.entity.GuestBook;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.GuestBookStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 留言管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 15:48
 */
public interface IGuestBookService extends IBaseService<GuestBook, Integer> {

    /**
     * 审核留言
     *
     * @param guestBookId 帖子ID
     * @param status      状态
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:44
     */
    void verifyGuestBook(GuestBookStatus status, Integer... guestBookId);

    /**
     * 修改留言是否推荐
     *
     * @param guestBookId 帖子ID
     * @param recommend   是否推荐
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:46
     */
    void recommendGuestBook(int guestBookId, boolean recommend);

    /**
     * 回复留言
     *
     * @param userName    用户名(管理员)
     * @param content     内容
     * @param guestBookId 留言ID
     * @author andy_hulibo@163.com
     * @date 2018/12/12 13:40
     */
    void replyGuestBook(String userName, int guestBookId, String content);

    /**
     * 统计当天留言总数和总留言数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:47
     */
    Map<String, BigInteger> getTodayAndTotalGuestBook();

    /**
     * 获得最新的前几条评论
     *
     * @param limit 返回最大条数
     * @return Map
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:21
     */
    List<Map<String, Object>> getLatestGuestBook(int limit);
}
