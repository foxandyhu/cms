package com.bfly.cms.comment.service;

import com.bfly.cms.comment.entity.GuestBook;
import com.bfly.core.base.service.IBaseService;
import com.bfly.core.enums.GuestBookStatus;

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
}
