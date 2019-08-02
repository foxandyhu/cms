package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.IGuestBookDao;
import com.bfly.cms.comment.entity.GuestBook;
import com.bfly.cms.comment.entity.GuestBookExt;
import com.bfly.cms.comment.service.IGuestBookService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.GuestBookStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:37
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class GuestBookServiceImpl extends BaseServiceImpl<GuestBook, Integer> implements IGuestBookService {

    @Autowired
    private IGuestBookDao guestBookDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyGuestBook(GuestBookStatus status, Integer... guestBookIds) {
        for (int id : guestBookIds) {
            GuestBook guestBook = get(id);
            Assert.notNull(guestBook, "留言信息不存在!");
            guestBookDao.editGuestBookStatus(id, status.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendGuestBook(int guestBookId, boolean recommend) {
        GuestBook guestBook = get(guestBookId);
        Assert.notNull(guestBook, "留言信息不存在!");
        Assert.isTrue(guestBook.getStatus() == CommentStatus.PASSED.getId(), guestBook.getStatusName() + "状态的留言不能推荐!");
        guestBookDao.editGuestBookRecommend(guestBookId, recommend);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyGuestBook(String userName, int guestBookId, String content) {
        GuestBook guestBook = get(guestBookId);
        Assert.notNull(guestBook, "留言信息不存在!");
        Assert.isTrue(!guestBook.isReply(), "该留言已回复不允许再次回复!");
        Assert.isTrue(guestBook.getStatus() == CommentStatus.PASSED.getId(), guestBook.getStatusName() + "状态的留言不能回复!");

        guestBook.setReplyDate(new Date());
        guestBook.setReplyUserName(userName);
        guestBook.setReply(true);

        GuestBookExt ext = guestBook.getExt();
        Assert.hasLength(content, "回复内容不能为空!");
        ext.setReplyContent(content);
        ext.setReplyIp(IpThreadLocal.get());
        guestBookDao.save(guestBook);
    }


    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        String sqlList = "SELECT gb.id,gb.post_user_name as postUserName,gb.reply_user_name as replyUserName,gb.post_date as postDate,gb.reply_date as replyDate,gb.`status`,gb.is_recommend as recommend,gb.is_reply as reply,gb_ext.ip,gb_ext.area,gb_ext.title,gb_ext.content,gb_ext.email,gb_ext.phone,gb_ext.qq,gb_ext.reply_content as replyContent,gb_ext.reply_ip as replyIp,dir.`name` as typeName";
        String sqlCount = "SELECT COUNT(1)";
        String sql = " FROM GUESTBOOK as gb LEFT JOIN GUESTBOOK_EXT as gb_ext ON gb.id=gb_ext.guest_book_id LEFT JOIN D_DICTIONARY as dir ON gb.type_id=dir.`value` WHERE dir.type='" + GuestBook.GUESTBOOK_TYPE_DIR + "'";

        Object[] params = new Object[0];
        String status = "status", recommend = "recommend", type = "type";
        if (property != null) {
            if (property.containsKey(status)) {
                sql = sql.concat(" AND status=?");
                params = ArrayUtils.add(params, property.get(status));
            }
            if (property.containsKey(recommend)) {
                sql = sql.concat(" AND is_recommend=?");
                params = ArrayUtils.add(params, property.get(recommend));
            }
            if (property.containsKey(type)) {
                sql = sql.concat(" AND type_id=?");
                params = ArrayUtils.add(params, property.get(type));
            }
        }
        sql = sql.concat(" ORDER BY gb.post_date desc");
        sqlList = sqlList.concat(sql).concat(" LIMIT " + (pager.getPageNo() - 1) * pager.getPageSize() + "," + pager.getPageSize());

        List<Map<String, Object>> list = querySql(sqlList, params);
        if (list != null) {
            list.forEach(map -> {
                if (map.containsKey(status)) {
                    int statusId = (int) map.get(status);
                    GuestBookStatus guestBookStatus = GuestBookStatus.getStatus(statusId);
                    map.put("statusName", guestBookStatus == null ? "" : guestBookStatus.getName());
                }
            });
        }
        long total = getCountSql(sqlCount.concat(sql), params);
        pager.setTotalCount(total);
        pager.setData(list);
        return pager;
    }
}
