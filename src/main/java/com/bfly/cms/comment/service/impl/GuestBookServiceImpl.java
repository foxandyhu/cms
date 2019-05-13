package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.comment.entity.GuestBook;
import com.bfly.cms.comment.entity.GuestBookExt;
import com.bfly.cms.comment.service.IGuestBookService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.ContextUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:37
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GuestBookServiceImpl extends BaseServiceImpl<GuestBook, Integer> implements IGuestBookService {

    @Autowired
    private IGuestBookService guestBookService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyGuestBook(int status, int... guestBookId) {
        Assert.notNull(guestBookId, "留言信息不存在!");
        for (int id : guestBookId) {
            GuestBook guestBook = get(id);
            Assert.notNull(guestBook, "留言信息不存在!");
            guestBook.setStatus(status);
            guestBookService.save(guestBook);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendGuestBook(int guestBookId, boolean recommend) {
        GuestBook guestBook = get(guestBookId);
        Assert.notNull(guestBook, "留言信息不存在!");
        Assert.isTrue(guestBook.getStatus() != Comment.WAIT_CHECK, "该留言尚未审核不能推荐!");
        Assert.isTrue(guestBook.getStatus() != Comment.UNPASSED, "审核不通过的留言不能推荐!");
        guestBook.setRecommend(recommend);
        guestBookService.save(guestBook);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyGuestBook(int guestBookId, String content, int replyerId) {
        GuestBook guestBook = get(guestBookId);
        Assert.notNull(guestBook, "留言信息不存在!");
        Assert.isTrue(guestBook.getStatus() != Comment.WAIT_CHECK, "该留言尚未审核不能回复!");
        Assert.isTrue(guestBook.getStatus() != Comment.UNPASSED, "审核不通过的留言不能回复!");

        GuestBook replyGuestBook = new GuestBook();
        replyGuestBook.setCreateTime(new Date());
        replyGuestBook.setIp(ContextUtil.getIpFromThreadLocal());
        replyGuestBook.setParent(guestBook);
        replyGuestBook.setType(guestBook.getType());

        GuestBookExt replyExt = new GuestBookExt();
        replyExt.setContent(content);
        replyGuestBook.setExt(replyExt);
        //会员回复的评论需要审核 管理员回复的评论不需要审核
        if (replyerId >= Member.MEMBER_ID_BEGIN) {
            replyGuestBook.setStatus(Comment.WAIT_CHECK);
            replyGuestBook.setMember(memberService.get(replyerId));
        } else {
            replyGuestBook.setStatus(Comment.PASSED);
            replyGuestBook.setUser(userService.get(replyerId));
        }
        guestBookService.save(replyGuestBook);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(int guestBookId, String content) {
        GuestBook guestBook = get(guestBookId);
        Assert.notNull(guestBook, "留言信息不存在!");

        guestBook.getExt().setContent(content);
        guestBookService.save(guestBook);
    }
}
