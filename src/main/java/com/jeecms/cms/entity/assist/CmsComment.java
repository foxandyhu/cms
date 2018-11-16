package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.main.Content;
import com.jeecms.common.util.DateUtils;
import com.jeecms.common.util.StrUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

public class CmsComment implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private java.util.Date createTime;
    private java.util.Date replayTime;
    private Short ups;
    private Short downs;
    private Boolean recommend;
    private Short checked;
    private Integer score;

    private Integer replyCount;
    private CmsComment parent;

    // one to one
    private CmsCommentExt commentExt;

    // many to one
    private CmsUser replayUser;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;
    private CmsUser commentUser;
    private CmsSite site;

    private Set<CmsComment> child;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(java.util.Date replayTime) {
        this.replayTime = replayTime;
    }

    public Short getUps() {
        return ups;
    }

    public void setUps(Short ups) {
        this.ups = ups;
    }

    public Short getDowns() {
        return downs;
    }

    public void setDowns(Short downs) {
        this.downs = downs;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Short getChecked() {
        return checked;
    }

    public void setChecked(Short checked) {
        this.checked = checked;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public CmsComment getParent() {
        return parent;
    }

    public void setParent(CmsComment parent) {
        this.parent = parent;
    }

    public CmsCommentExt getCommentExt() {
        return commentExt;
    }

    public void setCommentExt(CmsCommentExt commentExt) {
        this.commentExt = commentExt;
    }

    public CmsUser getReplayUser() {
        return replayUser;
    }


    public void setReplayUser(CmsUser replayUser) {
        this.replayUser = replayUser;
    }


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


    public CmsUser getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CmsUser commentUser) {
        this.commentUser = commentUser;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public Set<CmsComment> getChild() {
        return child;
    }

    public void setChild(Set<CmsComment> child) {
        this.child = child;
    }


    public String getText() {
        return getCommentExt().getText();
    }

    public String getTextHtml() {
        return StrUtils.txt2htm(getText());
    }

    public String getReply() {
        return getCommentExt().getReply();
    }

    public String getReplayHtml() {
        return StrUtils.txt2htm(getReply());
    }

    public String getIp() {
        return getCommentExt().getIp();
    }

    public void init() {
        short zero = 0;
        if (getDowns() == null) {
            setDowns(zero);
        }
        if (getUps() == null) {
            setUps(zero);
        }
        if (getReplyCount() == null) {
            setReplyCount(0);
        }
        if (getChecked() == null) {
            setChecked((short) 0);
        }
        if (getRecommend() == null) {
            setRecommend(false);
        }
        if (getCreateTime() == null) {
            setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getCreateTime() != null) {
            json.put("createTime", DateUtils.parseDateToTimeStr(getCreateTime()));
        } else {
            json.put("createTime", "");
        }
        if (getReplayTime() != null) {
            json.put("replayTime", DateUtils.parseDateToTimeStr(getReplayTime()));
        } else {
            json.put("replayTime", "");
        }
        if (getUps() != null) {
            json.put("ups", getUps());
        } else {
            json.put("ups", "");
        }
        if (getDowns() != null) {
            json.put("downs", getDowns());
        } else {
            json.put("downs", "");
        }
        if (getRecommend() != null) {
            json.put("recommend", getRecommend());
        } else {
            json.put("recommend", "");
        }
        if (getChecked() != null) {
            json.put("checked", getChecked());
        } else {
            json.put("checked", "");
        }
        if (getReplyCount() != null) {
            json.put("replyCount", getReplyCount());
        } else {
            json.put("replyCount", "");
        }
        if (getReplayUser() != null && StringUtils.isNotBlank(getReplayUser().getUsername())) {
            json.put("replayerUsername", getReplayUser().getUsername());
        } else {
            json.put("replayerUsername", "");
        }
        if (getReplayUser() != null && getReplayUser().getId() != null) {
            json.put("replayerId", getReplayUser().getId());
        } else {
            json.put("replayerId", "");
        }
        if (getCommentUser() != null && getCommentUser().getId() != null) {
            json.put("commenterId", getCommentUser().getId());
        } else {
            json.put("commenterId", "");
        }
        if (getCommentUser() != null && StringUtils.isNotBlank(getCommentUser().getUsername())) {
            json.put("commenterUsername", getCommentUser().getUsername());
        } else {
            json.put("commenterUsername", "");
        }
        if (getCommentExt() != null && StringUtils.isNotBlank(getCommentExt().getIp())) {
            json.put("ip", getIp());
        } else {
            json.put("ip", "");
        }
        if (getCommentExt() != null && StringUtils.isNotBlank(getCommentExt().getText())) {
            json.put("text", getText());
        } else {
            json.put("text", "");
        }
        if (getCommentExt() != null && StringUtils.isNotBlank(getCommentExt().getReply())) {
            json.put("reply", getReply());
        } else {
            json.put("reply", "");
        }
        if (getScore() != null) {
            json.put("score", getScore());
        } else {
            json.put("score", "");
        }
        if (getParent() != null && getParent().getId() != null) {
            json.put("parentId", getParent().getId());
        } else {
            json.put("parentId", "");
        }
        if (getContent() != null && getContent().getId() != null) {
            json.put("contentId", getContent().getId());
        } else {
            json.put("contentId", "");
        }
        if (getContent() != null && StringUtils.isNotBlank(getContent().getTitle())) {
            json.put("contentTitle", getContent().getTitle());
        } else {
            json.put("contentTitle", "");
        }
        if (getContent() != null && getContent().getChannel() != null && getContent().getChannel().getId() != null) {
            json.put("channelId", getContent().getChannel().getId());
        } else {
            json.put("channelId", "");
        }
        if (getContent() != null && getContent().getChannel() != null && StringUtils.isNotBlank(getContent().getChannel().getName())) {
            json.put("channelName", getContent().getChannel().getName());
        } else {
            json.put("channelName", "");
        }
        if (getContent() != null && StringUtils.isNotBlank(getContent().getUrl())) {
            json.put("contentURL", getContent().getUrl());
        } else {
            json.put("contentURL", "");
        }
        return json;
    }
}