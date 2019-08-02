package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.dao.ICommentDao;
import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.comment.entity.CommentExt;
import com.bfly.cms.comment.service.ICommentService;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.IContentService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.user.service.IUserService;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.ContentStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private IContentService contentService;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        String sqlCount = "SELECT COUNT(1)";
        String sqlList = "SELECT cmt.id,cmt.content_id as contentId,cmt.member_user_name as memberUserName,cmt.user_name as userName,cmt.post_date as postDate,cmt.is_recommend as recommend,cmt.`status`,cmt_ext.ip,cmt_ext.area,cmt_ext.text,cnt_ext.title,cnt_type.`name` as typeName";
        String sql = " FROM `comment` AS cmt LEFT JOIN comment_ext AS cmt_ext ON cmt.id=cmt_ext.comment_id LEFT JOIN content AS cnt ON cmt.content_id=cnt.id LEFT JOIN content_ext AS cnt_ext ON cnt.id=cnt_ext.content_id LEFT JOIN content_type AS cnt_type ON cnt.type_id=cnt_type.id where 1=1";
        Object[] params = new Object[0];
        String status = "status", recommend = "recommend", contentId = "contentId";
        if (property != null) {
            if (property.containsKey(status)) {
                sql = sql.concat(" and cmt.status=?");
                params = ArrayUtils.add(params, property.get(status));
            }
            if (property.containsKey(recommend)) {
                sql = sql.concat(" and cmt.is_recommend=?");
                params = ArrayUtils.add(params, property.get(recommend));
            }
            if (property.containsKey(contentId)) {
                sql = sql.concat(" and cmt.content_id=?");
                params = ArrayUtils.add(params, property.get(contentId));
            }
        }
        sql = sql.concat(" ORDER BY cmt.post_date desc");
        sqlList = sqlList.concat(sql).concat(" LIMIT " + (pager.getPageNo() - 1) * pager.getPageSize() + "," + pager.getPageSize());

        List<Map<String, Object>> list = querySql(sqlList, params);
        if (list != null) {
            list.forEach(map -> {
                if (map.containsKey(status)) {
                    int statusId = (int) map.get(status);
                    CommentStatus commentStatus = CommentStatus.getStatus(statusId);
                    map.put("statusName", commentStatus == null ? "" : commentStatus.getName());
                }
            });
        }
        long total = getCountSql(sqlCount.concat(sql), params);
        pager.setTotalCount(total);
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
            Assert.isTrue(parent.getContentId() == comment.getContentId(), "评论所属内容ID错误!");
        }

        Content content = contentService.get(comment.getContentId());
        Assert.notNull(content, "评论文章不存在!");
        Assert.isTrue(content.getStatus() == ContentStatus.PASSED.getId(), content.getStatusName() + "状态的文章不允许评论!");

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
}
