package com.bfly.cms.comment.entity;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.user.entity.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 文章内容评论
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:50
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1036844320957193420L;

    /**
     * 等待审核
     */
    public static final int WAIT_CHECK = 0;
    /**
     * 审核不通过
     */
    public static final int UNPASSED = 1;
    /**
     * 审核通过
     */
    public static final int PASSED = 2;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评论时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支持数
     */
    @Column(name = "ups")
    private int ups;

    /**
     * 反对数
     */
    @Column(name = "downs")
    private int downs;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 评分
     */
    @Column(name = "score")
    private int score;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private int replyCount;

    /**
     * 状态 1审核不通过 2审核通过
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:41
     */
    @Column(name = "status")
    private int status;

    /**
     * 主评论
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    /**
     * 引用的回复子评论
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private Set<Comment> child;

    /**
     * 评论扩展信息
     */
    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "comment")
    @NotNull(message = "回复信息不能为空!")
    private CommentExt commentExt;

    /**
     * 评论发布/回复者
     */
    @ManyToOne
    @JoinColumn(name = "post_user_id")
    private User postUser;

    /**
     * 评论发布/回复者
     */
    @ManyToOne
    @JoinColumn(name = "post_member_id")
    private Member postMember;

    /**
     * 所属文章
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public User getPostUser() {
        return postUser;
    }

    public void setPostUser(User postUser) {
        this.postUser = postUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Set<Comment> getChild() {
        return child;
    }

    public void setChild(Set<Comment> child) {
        this.child = child;
    }

    public CommentExt getCommentExt() {
        return commentExt;
    }

    public void setCommentExt(CommentExt commentExt) {
        this.commentExt = commentExt;
    }

    public Member getPostMember() {
        return postMember;
    }

    public void setPostMember(Member postMember) {
        this.postMember = postMember;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}