package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.common.util.StrUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

public class CmsGuestbook implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String ip;
    private java.util.Date createTime;
    private java.util.Date replayTime;
    private Short checked;
    private Boolean recommend;

    // one to one
    private CmsGuestbookExt ext;

    // many to one
    private CmsUser member;
    private CmsUser admin;
    private CmsSite site;
    private CmsGuestbookCtg ctg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
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


    public Short getChecked() {
        return checked;
    }


    public void setChecked(Short checked) {
        this.checked = checked;
    }

    public Boolean getRecommend() {
        return recommend;
    }


    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }


    public CmsGuestbookExt getExt() {
        return ext;
    }


    public void setExt(CmsGuestbookExt ext) {
        this.ext = ext;
    }

    public CmsUser getMember() {
        return member;
    }

    public void setMember(CmsUser member) {
        this.member = member;
    }

    public CmsUser getAdmin() {
        return admin;
    }


    public void setAdmin(CmsUser admin) {
        this.admin = admin;
    }


    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }

    public CmsGuestbookCtg getCtg() {
        return ctg;
    }


    public void setCtg(CmsGuestbookCtg ctg) {
        this.ctg = ctg;
    }


    public String getTitleHtml() {
        return StrUtils.txt2htm(getTitle());
    }

    public String getContentHtml() {
        return StrUtils.txt2htm(getContent());
    }

    public String getReplyHtml() {
        return StrUtils.txt2htm(getReply());
    }

    public String getTitle() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getTitle();
        } else {
            return null;
        }
    }

    public String getContent() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getContent();
        } else {
            return null;
        }
    }

    public String getReply() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getReply();
        } else {
            return null;
        }
    }

    public String getEmail() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getEmail();
        } else {
            return null;
        }
    }

    public String getPhone() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getPhone();
        } else {
            return null;
        }
    }

    public String getQq() {
        CmsGuestbookExt ext = getExt();
        if (ext != null) {
            return ext.getQq();
        } else {
            return null;
        }
    }

    public void init() {
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
        if (getMember() != null && StringUtils.isNotBlank(getMember().getUsername())) {
            json.put("memberUsername", getMember().getUsername());
        } else {
            json.put("memberUsername", "");
        }
        if (getMember() != null && getMember().getId() != null) {
            json.put("memberId", getMember().getId());
        } else {
            json.put("memberId", "");
        }
        if (getAdmin() != null && StringUtils.isNotBlank(getAdmin().getUsername())) {
            json.put("adminUsername", getAdmin().getUsername());
        } else {
            json.put("adminUsername", "");
        }
        if (getAdmin() != null && getAdmin().getId() != null) {
            json.put("adminId", getAdmin().getId());
        } else {
            json.put("adminId", "");
        }
        if (StringUtils.isNotBlank(getIp())) {
            json.put("ip", getIp());
        } else {
            json.put("ip", "");
        }
        if (getCtg() != null && StringUtils.isNotBlank(getCtg().getName())) {
            json.put("ctgName", getCtg().getName());
        } else {
            json.put("ctgName", "");
        }
        if (getCtg() != null && getCtg().getId() != null) {
            json.put("ctgId", getCtg().getId());
        } else {
            json.put("ctgId", "");
        }
        if (getContent() != null) {
            json.put("content", getContent());
        } else {
            json.put("content", "");
        }
        if (getReply() != null) {
            json.put("reply", getReply());
        } else {
            json.put("reply", "");
        }
        if (StringUtils.isNotBlank(getEmail())) {
            json.put("email", getEmail());
        } else {
            json.put("email", "");
        }
        if (StringUtils.isNotBlank(getPhone())) {
            json.put("phone", getPhone());
        } else {
            json.put("phone", "");
        }
        if (StringUtils.isNotBlank(getQq())) {
            json.put("qq", getQq());
        } else {
            json.put("qq", "");
        }
        return json;
    }

}