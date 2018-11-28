package com.bfly.cms.comment.entity;

import com.bfly.common.util.DateUtils;
import com.bfly.common.util.StrUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * CMS留言
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:18
 */
@Entity
@Table(name = "jc_guestbook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsGuestbook implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "guestbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 留言IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 留言时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 回复时间
     */
    @Column(name = "replay_time")
    private Date replayTime;

    /**
     * 是否审核
     */
    @Column(name = "is_checked")
    private Short checked;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private Boolean recommend;

    @OneToOne(mappedBy = "guestbook", cascade = CascadeType.REMOVE)
    private CmsGuestbookExt ext;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private CmsUser member;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private CmsUser admin;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "guestbookctg_id")
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Date replayTime) {
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