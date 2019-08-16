package com.bfly.cms.message.service.impl;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.message.dao.ICommentDao;
import com.bfly.cms.message.entity.Comment;
import com.bfly.cms.message.entity.CommentExt;
import com.bfly.cms.message.service.ICommentService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.ContentType;
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
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer> implements ICommentService {

    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService contentService;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Pageable pageable = getPageRequest(pager);
        Page<Map<String, Object>> page = commentDao.getCommentPage((Integer) property.get("status"), (Boolean) property.get("recommend"), (Integer) property.get("articleId"), pageable);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.getContent() != null) {
            String status = "status";
            String typeId = "typeId";
            page.getContent().forEach(map -> {
                Map<String, Object> dataMap = new HashMap<>(map.size());
                dataMap.putAll(map);
                if (map.containsKey(status)) {
                    int statusId = (int) map.get(status);
                    CommentStatus commentStatus = CommentStatus.getStatus(statusId);
                    dataMap.put("statusName", commentStatus == null ? "" : commentStatus.getName());
                }
                if (map.containsKey(typeId)) {
                    int type = (int) map.get(typeId);
                    ContentType contentType = ContentType.getType(type);
                    dataMap.put("typeName", contentType == null ? "" : contentType.getName());
                }
                list.add(dataMap);
            });
        }
        pager.setTotalCount(page.getTotalElements());
        pager.setData(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyComment(CommentStatus status, Integer... commentIds) {
        if (commentIds != null) {
            for (int id : commentIds) {
                Comment comment = get(id);
                Assert.notNull(comment, "评论信息不存在!");
                commentDao.editCommentStatus(id, status.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendComment(int commentId, boolean recommend) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");
        Assert.isTrue(comment.getStatus() == CommentStatus.PASSED.getId(), comment.getStatusName() + "状态的评论不能推荐!");
        commentDao.editCommentRecommend(commentId, recommend);
    }

    /**
     * 评论或回复 只需要包装内容,所属父类ID,所属文章ID,用户名即可
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 13:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Comment comment) {
        //管理员回复或评论
        if (StringUtils.hasLength(comment.getUserName())) {
            User user = userService.getUser(comment.getUserName());
            Assert.notNull(user, "用户信息不存在!");
            comment.setStatus(CommentStatus.PASSED.getId());
        } else if (StringUtils.hasLength(comment.getMemberUserName())) {
            Member member = memberService.getMember(comment.getMemberUserName());
            Assert.notNull(member, "用户信息不存在!");
            comment.setStatus(CommentStatus.WAIT_CHECK.getId());
        }
        //评论回复
        if (comment.getParentId() > 0) {
            Comment parent = get(comment.getParentId());
            Assert.notNull(parent, "不存在的引用评论!");
            Assert.isTrue(parent.getStatus() != CommentStatus.WAIT_CHECK.getId(), "未审核的评论不允许回复!");
            Assert.isTrue(parent.getStatus() != CommentStatus.UNPASSED.getId(), "审核不通过的评论不允许回复!");
            Assert.isTrue(parent.getArticleId() == comment.getArticleId(), "评论所属内容ID错误!");
        }

        Article article = contentService.get(comment.getArticleId());
        Assert.notNull(article, "评论文章不存在!");
        Assert.isTrue(article.getStatus() == ArticleStatus.PASSED.getId(), article.getStatusName() + "状态的文章不允许评论!");

        comment.setPostDate(new Date());
        comment.setRecommend(false);
        comment.setDowns(0);
        comment.setUps(0);

        CommentExt ext = comment.getCommentExt();
        Assert.notNull(ext, "评论内容不能为空!");
        Assert.notNull(ext.getText(), "评论内容不能为空!");
        ext.setIp(IpThreadLocal.get());
        IPLocation location = IpSeekerUtil.getIPLocation(ext.getIp());
        if (location != null) {
            ext.setArea(location.getCountry() + " " + location.getArea());
        }
        ext.setComment(comment);

        commentDao.save(comment);
        return true;
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalComment() {
        return commentDao.getTodayAndTotalComment();
    }

    @Override
    public List<Map<String, Object>> getLatestComment(int limit) {
        List<Map<String, Object>> list = commentDao.getLatestRegistComment(limit);
        if (list != null) {
            String status = "status",face="face";
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Map<String, Object> m = new HashMap<>(map.size());
                m.putAll(map);
                if (m.containsKey(status)) {
                    CommentStatus commentStatus=CommentStatus.getStatus((Integer) m.get(status));
                    m.put("statusName",commentStatus==null?"":commentStatus.getName() );
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
