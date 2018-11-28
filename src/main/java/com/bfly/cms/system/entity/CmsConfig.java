package com.bfly.cms.system.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * CMS系统配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 15:30
 */
@Entity
@Table(name = "jc_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String VERSION = "version";
    public static final String REWARD_FIX_PREFIX = "reward_fix_";

    @Id
    @Column(name = "config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部署路劲
     */
    @Column(name = "context_path")
    private String contextPath;

    /**
     * Servlet挂载点
     */
    @Column(name = "servlet_point")
    private String servletPoint;

    /**
     * 端口
     */
    @Column(name = "port")
    private Integer port;

    /**
     * 数据库附件访问路径
     */
    @Column(name = "db_file_uri")
    private String dbFileUri;

    /**
     * 上传附件至数据库
     */
    @Column(name = "is_upload_to_db")
    private Boolean uploadToDb;

    /**
     * 图片不存在时默认图片
     */
    @Column(name = "def_img")
    private String defImg;

    /**
     * 登录地址
     */
    @Column(name = "login_url")
    private String loginUrl;

    /**
     * 登录后处理地址
     */
    @Column(name = "process_url")
    private String processUrl;

    /**
     * 计数器清除时间
     */
    @Column(name = "count_clear_time")
    private Date countClearTime;

    /**
     * 计数器拷贝时间
     */
    @Column(name = "count_copy_time")
    private Date countCopyTime;

    /**
     * 下载防盗链md5混淆码
     */
    @Column(name = "download_code")
    private String downloadCode;

    /**
     * 下载有效时间（小时）
     */
    @Column(name = "download_time")
    private Integer downloadTime;

    /**
     * 验证类型：0:无  1：邮件验证  2：SMS验证
     */
    @Column(name = "validate_type")
    private Integer validateType;

    /**
     * 只有终审才能浏览内容页
     */
    @Column(name = "view_only_checked")
    private Boolean viewOnlyChecked;

    /**
     * 流量统计清除时间
     */
    @Column(name = "flow_clear_time")
    private Date flowClearTime;

    /**
     * 栏目发布内容计数器清除时间
     */
    @Column(name = "channel_count_clear_time")
    private Date channelCountClearTime;

    /**
     * 短信验证 每日验证次数限制
     */
    @Column(name = "day_count")
    private Integer dayCount;

    /**
     * 配置了的短信运营商
     */
    @Column(name = "smsid")
    private Long smsID;

    /**
     * 水印配置
     */
    @Embedded
    MarkConfig m_markConfig;

    /**
     * 邮件配置
     */
    @Embedded
    EmailConfig m_emailConfig;

    @ElementCollection(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "jc_config_attr", joinColumns = @JoinColumn(name = "config_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    @OneToMany(mappedBy = "config")
    private Set<CmsConfigItem> registerItems;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }


    public String getServletPoint() {
        return servletPoint;
    }


    public void setServletPoint(String servletPoint) {
        this.servletPoint = servletPoint;
    }


    public Integer getPort() {
        return port;
    }


    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbFileUri() {
        return dbFileUri;
    }

    public void setDbFileUri(String dbFileUri) {
        this.dbFileUri = dbFileUri;
    }

    public Boolean getUploadToDb() {
        return uploadToDb;
    }


    public void setUploadToDb(Boolean uploadToDb) {
        this.uploadToDb = uploadToDb;
    }

    public String getDefImg() {
        return defImg;
    }

    public void setDefImg(String defImg) {
        this.defImg = defImg;
    }


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getProcessUrl() {
        return processUrl;
    }


    public void setProcessUrl(String processUrl) {
        this.processUrl = processUrl;
    }


    public Date getCountClearTime() {
        return countClearTime;
    }

    public void setCountClearTime(Date countClearTime) {
        this.countClearTime = countClearTime;
    }


    public Date getCountCopyTime() {
        return countCopyTime;
    }

    public void setCountCopyTime(Date countCopyTime) {
        this.countCopyTime = countCopyTime;
    }


    public String getDownloadCode() {
        return downloadCode;
    }

    public void setDownloadCode(String downloadCode) {
        this.downloadCode = downloadCode;
    }


    public Integer getDownloadTime() {
        return downloadTime;
    }


    public void setDownloadTime(Integer downloadTime) {
        this.downloadTime = downloadTime;
    }


    public Integer getValidateType() {
        return validateType;
    }

    public void setValidateType(Integer validateType) {
        this.validateType = validateType;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Long getSmsID() {
        return smsID;
    }

    public void setSmsID(Long smsID) {
        this.smsID = smsID;
    }

    public Boolean getViewOnlyChecked() {
        return viewOnlyChecked;
    }

    public void setViewOnlyChecked(Boolean viewOnlyChecked) {
        this.viewOnlyChecked = viewOnlyChecked;
    }

    public Date getFlowClearTime() {
        return flowClearTime;
    }

    public void setFlowClearTime(Date flowClearTime) {
        this.flowClearTime = flowClearTime;
    }

    public Date getChannelCountClearTime() {
        return channelCountClearTime;
    }

    public void setChannelCountClearTime(Date channelCountClearTime) {
        this.channelCountClearTime = channelCountClearTime;
    }

    public MarkConfig getMarkConfig() {
        return m_markConfig;
    }


    public void setMarkConfig(MarkConfig m_markConfig) {
        this.m_markConfig = m_markConfig;
    }


    public EmailConfig getEmailConfig() {
        return m_emailConfig;
    }


    public void setEmailConfig(EmailConfig m_emailConfig) {
        this.m_emailConfig = m_emailConfig;
    }


    public Map<String, String> getAttr() {
        return attr;
    }


    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public Set<CmsConfigItem> getRegisterItems() {
        return registerItems;
    }

    public void setRegisterItems(Set<CmsConfigItem> registerItems) {
        this.registerItems = registerItems;
    }


    public JSONObject convertToJson(JSONArray jsonArray) {
        JSONObject json = new JSONObject();
        json.put("jsonArray", jsonArray);
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getContextPath())) {
            json.put("contextPath", getContextPath());
        } else {
            json.put("contextPath", "");
        }
        if (StringUtils.isNotBlank(getServletPoint())) {
            json.put("servletPoint", getServletPoint());
        } else {
            json.put("servletPoint", "");
        }
        if (StringUtils.isNotBlank(getDbFileUri())) {
            json.put("dbFileUri", getDbFileUri());
        } else {
            json.put("dbFileUri", "");
        }
        if (getPort() != null) {
            json.put("port", getPort());
        } else {
            json.put("port", "");
        }
        if (getUploadToDb() != null) {
            json.put("uploadToDb", getUploadToDb());
        } else {
            json.put("uploadToDb", "");
        }
        if (StringUtils.isNotBlank(getDefImg())) {
            json.put("defImg", getDefImg());
        } else {
            json.put("defImg", "");
        }
        if (StringUtils.isNotBlank(getLoginUrl())) {
            json.put("loginUrl", getLoginUrl());
        } else {
            json.put("loginUrl", "");
        }
        if (StringUtils.isNotBlank(getProcessUrl())) {
            json.put("processUrl", getProcessUrl());
        } else {
            json.put("processUrl", "");
        }
        if (getCountClearTime() != null) {
            json.put("countClearTime", getCountClearTime());
        } else {
            json.put("countClearTime", "");
        }
        if (getCountCopyTime() != null) {
            json.put("countCopyTime", getCountCopyTime());
        } else {
            json.put("countCopyTime", "");
        }
        if (StringUtils.isNotBlank(getDownloadCode())) {
            json.put("downloadCode", getDownloadCode());
        } else {
            json.put("downloadCode", "");
        }
        if (getDownloadTime() != null) {
            json.put("downloadTime", getDownloadTime());
        } else {
            json.put("downloadTime", "");
        }
        if (getViewOnlyChecked() != null) {
            json.put("viewOnlyChecked", getViewOnlyChecked());
        } else {
            json.put("viewOnlyChecked", "");
        }
        if (getFlowClearTime() != null) {
            json.put("flowClearTime", getFlowClearTime());
        } else {
            json.put("flowClearTime", "");
        }
        if (getChannelCountClearTime() != null) {
            json.put("channelCountClearTime", getChannelCountClearTime());
        } else {
            json.put("channelCountClearTime", "");
        }
        if (getCommentOpen() != null) {
            json.put("commentOpen", getCommentOpen());
        } else {
            json.put("commentOpen", "");
        }
        if (getCommentDayLimit() != null) {
            json.put("commentDayLimit", getCommentDayLimit());
        } else {
            json.put("commentDayLimit", "");
        }
        if (getGuestbookOpen() != null) {
            json.put("guestbookOpen", getGuestbookOpen());
        } else {
            json.put("guestbookOpen", "");
        }
        if (getGuestbookNeedLogin() != null) {
            json.put("guestbookNeedLogin", getGuestbookNeedLogin());
        } else {
            json.put("guestbookNeedLogin", "");
        }
        if (getGuestbookDayLimit() != null) {
            json.put("guestbookDayLimit", getGuestbookDayLimit());
        } else {
            json.put("guestbookDayLimit", "");
        }
        if (getDayCount() != null) {
            json.put("dayCount", getDayCount());
        } else {
            json.put("dayCount", 0);
        }
        if (getSmsID() != null) {
            json.put("smsID", getSmsID());
        } else {
            json.put("smsID", getSmsID());
        }
        if (getValidateType() != null) {
            json.put("validateType", getValidateType());
        } else {
            json.put("validateType", 0);
        }
        return json;
    }

    public String getVersion() {
        return getAttr().get(VERSION);
    }

    public Boolean getSsoEnable() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getSsoEnable();
    }

    public Boolean getFlowSwitch() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getFlowSwitch();
    }

    public Boolean getCommentOpen() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getCommentOpen();
    }

    public Boolean getGuestbookOpen() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getGuestbookOpen();
    }

    public Boolean getGuestbookNeedLogin() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getGuestbookNeedLogin();
    }

    public Map<String, String> getSsoAttr() {
        Map<String, String> ssoMap = new HashMap<>();
        Map<String, String> attr = getAttr();
        for (String ssoKey : attr.keySet()) {
            if (ssoKey.startsWith("sso_")) {
                ssoMap.put(ssoKey, attr.get(ssoKey));
            }
        }
        return ssoMap;
    }

    public List<String> getSsoAuthenticateUrls() {
        Map<String, String> ssoMap = getSsoAttr();
        List<String> values = new ArrayList<>();
        for (String key : ssoMap.keySet()) {
            values.add(ssoMap.get(key));
        }
        return values;
    }

    public Map<String, String> getRewardFixAttr() {
        Map<String, String> attrMap = new HashMap<>();
        Map<String, String> attr = getAttr();
        for (String fixKey : attr.keySet()) {
            if (fixKey.startsWith(REWARD_FIX_PREFIX)) {
                attrMap.put(fixKey, attr.get(fixKey));
            }
        }
        return attrMap;
    }

    public Object[] getRewardFixValues() {
        Map<String, String> attrMap = getRewardFixAttr();
        Collection<String> fixStrings = attrMap.values();
        return fixStrings.toArray();
    }


    public MemberConfig getMemberConfig() {
        MemberConfig memberConfig = new MemberConfig(getAttr());
        return memberConfig;
    }

    public void setMemberConfig(MemberConfig memberConfig) {
        getAttr().putAll(memberConfig.getAttr());
    }

    public CmsConfigAttr getConfigAttr() {
        CmsConfigAttr configAttr = new CmsConfigAttr(getAttr());
        return configAttr;
    }

    public void setConfigAttr(CmsConfigAttr configAttr) {
        getAttr().putAll(configAttr.getAttr());
    }

    public Boolean getQqEnable() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqEnable();
    }

    public Integer getCommentDayLimit() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getCommentDayLimit();
    }

    public Integer getGuestbookDayLimit() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getGuestbookDayLimit();
    }

    public Boolean getSinaEnable() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getSinaEnable();
    }

    public Boolean getQqWeboEnable() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqWeboEnable();
    }

    public String getQqID() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqID();
    }

    public String getSinaID() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getSinaID();
    }

    public String getQqWeboID() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getQqWeboID();
    }

    public Boolean getWeixinEnable() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinEnable();
    }

    public String getWeixinID() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinID();
    }

    public String getWeixinKey() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinKey();
    }

    public String getWeixinAppId() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinAppId();
    }

    public String getWeixinAppSecret() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinAppSecret();
    }

    public String getWeixinLoginId() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinLoginId();
    }

    public String getWeixinLoginSecret() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getWeixinLoginSecret();
    }

    public Integer getContentFreshMinute() {
        CmsConfigAttr configAttr = getConfigAttr();
        return configAttr.getContentFreshMinute();
    }

    public void init() {
        if (getUploadToDb() == null) {
            setUploadToDb(false);
        }
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getProcessUrl())) {
            setProcessUrl(null);
        }
        if (StringUtils.isBlank(getContextPath())) {
            setContextPath(null);
        }
        if (StringUtils.isBlank(getServletPoint())) {
            setServletPoint(null);
        }
    }
}