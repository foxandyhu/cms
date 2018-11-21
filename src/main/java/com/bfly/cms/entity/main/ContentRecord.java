package com.bfly.cms.entity.main;

import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章操作记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:31
 */
@Entity
@Table(name = "jc_content_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ContentOperateType {
        /**
         * 新增
         */
        add,
        /**
         * 修改
         */
        edit,
        /**
         * 审核
         */
        check,
        /**
         * 退回
         */
        rejected,
        /**
         * 移动
         */
        move,
        /**
         * 生成静态页
         */
        createPage,
        /**
         * 回收
         */
        cycle,
        /**
         * 归档
         */
        pigeonhole,
        /**
         * 出档
         */
        reuse,
        /**
         * 共享
         */
        shared
    }

    public static final byte ADD = 0;
    public static final byte EDIT = 1;
    public static final byte CHECK = 2;
    public static final byte REJECTED = 3;
    public static final byte MOVE = 4;
    public static final byte CREATEPAGE = 5;
    public static final byte CYCLE = 6;
    public static final byte PIGEONHOLE = 7;
    public static final byte REUSE = 8;
    public static final byte SHARED = 9;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getOperateTime() != null) {
            json.put("operateTime", DateUtils.parseDateToDateStr(getOperateTime()));
        } else {
            json.put("operateTime", "");
        }
        if (getOperateType() != null) {
            json.put("operateType", getOperateType());
        } else {
            json.put("operateType", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("username", getUser().getUsername());
        } else {
            json.put("username", "");
        }
        return json;
    }

    @Id
    @Column(name = "content_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private Date operateTime;

    /**
     * 0 新增 1修改 2审核 3退回 4移动 5生成静态页 6删除到回收站 7归档 8出档 9推送共享
     */
    @Column(name = "operate_type")
    private Byte operateType;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Date getOperateTime() {
        return operateTime;
    }


    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }


    public Byte getOperateType() {
        return operateType;
    }


    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }


    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }


}