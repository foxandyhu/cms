package com.bfly.cms.message.entity;

import com.bfly.core.enums.CommentStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评论时间
     */
    @Column(name = "post_date")
    private Date postDate;

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
     * 状态
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 11:41
     * @see com.bfly.core.enums.CommentStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 主评论
     */
    @Column(name = "parent_id")
    private int parentId;

    /**
     * 引用的回复子评论
     */
    @JoinColumn(name = "parent_id")
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> child;

    /**
     * 评论扩展信息
     */
    @OneToOne(cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    @NotNull(message = "回复信息不能为空!")
    @NotFound(action = NotFoundAction.IGNORE)
    private CommentExt commentExt;

    /**
     * 评论发布/回复者
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 评论发布/回复者
     */
    @Column(name = "member_user_name")
    private String memberUserName;

    /**
     * 所属文章
     */
    @Column(name = "content_id")
    private int contentId;

    public String getStatusName() {
        CommentStatus status = CommentStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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

    public List<Comment> getChild() {
        return child;
    }

    public void setChild(List<Comment> child) {
        this.child = child;
    }

    public CommentExt getCommentExt() {
        return commentExt;
    }

    public void setCommentExt(CommentExt commentExt) {
        this.commentExt = commentExt;
    }
}