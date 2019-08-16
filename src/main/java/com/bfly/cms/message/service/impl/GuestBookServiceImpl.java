package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.dao.IGuestBookDao;
import com.bfly.cms.message.entity.GuestBook;
import com.bfly.cms.message.entity.GuestBookExt;
import com.bfly.cms.message.service.IGuestBookService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.GuestBookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;

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

        Pageable pageable = getPageRequest(pager);
        Page<Map<String, Object>> page = guestBookDao.getGuestBookPage((Integer) property.get("status"), (Boolean) property.get("recommend"), (Integer) property.get("type"), pageable);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.getContent() != null) {
            String status = "status";
            page.getContent().forEach(map -> {
                Map<String, Object> dataMap = new HashMap<>(map.size());
                dataMap.putAll(map);
                if (map.containsKey(status)) {
                    int statusId = (int) map.get(status);
                    GuestBookStatus guestBookStatus = GuestBookStatus.getStatus(statusId);
                    dataMap.put("statusName", guestBookStatus == null ? "" : guestBookStatus.getName());
                }
                list.add(dataMap);
            });
        }
        pager.setTotalCount(page.getTotalElements());
        pager.setData(list);
        return pager;
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalGuestBook() {
        return guestBookDao.getTodayAndTotalGuestBook();
    }

    @Override
    public List<Map<String, Object>> getLatestGuestBook(int limit) {
        List<Map<String, Object>> list = guestBookDao.getLatestGuestBook(limit);
        if (list != null) {
            String status = "status",face="face";
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Map<String, Object> m = new HashMap<>(map.size());
                m.putAll(map);
                if (m.containsKey(status)) {
                    GuestBookStatus guestBookStatus=GuestBookStatus.getStatus((Integer) m.get(status));
                    m.put("statusName", guestBookStatus==null?"":guestBookStatus.getName());
                }
                if (m.containsKey(face)) {
                    if (StringUtils.hasLength((String) m.get(face))) {
                        m.put("face", ResourceConfig.getServer() + m.get(face));
                    }
                }
                list.set(i, m);
            }
        }
        return list;
    }
}
