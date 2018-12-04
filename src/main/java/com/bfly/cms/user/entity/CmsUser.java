package com.bfly.cms.user.entity;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.content.entity.CmsModelItem;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentBuy;
import com.bfly.cms.content.entity.ContentRecord;
import com.bfly.cms.funds.entity.CmsUserAccount;
import com.bfly.cms.job.entity.CmsJobApply;
import com.bfly.cms.job.entity.CmsUserResume;
import com.bfly.cms.logs.entity.CmsLog;
import com.bfly.cms.message.entity.CmsMessage;
import com.bfly.cms.message.entity.CmsReceiverMessage;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.system.entity.CmsConfigItem;
import com.bfly.common.hibernate4.PriorityInterface;
import com.bfly.common.util.DateUtils;
import com.bfly.core.Constants;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * CMS用户类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 20:51
 */
@Entity
@Table(name = "jc_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsUser implements PriorityInterface, Serializable {

    private static final long serialVersionUID = 1L;
    public static final Integer USER_STATU_CHECKED = 0;
    public static final Integer USER_STATU_DISABLED = 1;
    public static final Integer USER_STATU_CHECKING = 2;

    @Id
    @Column(name = "user_id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 注册IP
     */
    @Column(name = "register_ip")
    private String registerIp;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private Integer loginCount;

    /**
     * 管理员级别
     */
    @Column(name = "rank")
    private Integer rank;

    /**
     * 上传总大小
     */
    @Column(name = "upload_total")
    private Long uploadTotal;

    /**
     * 上传大小
     */
    @Column(name = "upload_size")
    private Integer uploadSize;

    /**
     * 上传日期
     */
    @Column(name = "upload_date")
    private Date uploadDate;

    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private Boolean admin;

    /**
     * 是否只读管理员
     */
    @Column(name = "is_viewonly_admin")
    private Boolean viewonlyAdmin;

    /**
     * 是否只管理自己的数据
     */
    @Column(name = "is_self_admin")
    private Boolean selfAdmin;

    /**
     * 状态 0审核通过  1禁用  2待审核
     */
    @Column(name = "statu")
    private Integer statu;

    /**
     * session_id
     */
    @Column(name = "session_id")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private CmsGroup group;

    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "jc_user_attr", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private CmsUserExt userExt;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private CmsUserAccount userAccount;

    @ManyToMany(mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    private Set<Channel> channels;

    @ManyToMany(mappedBy = "collectUsers", cascade = CascadeType.REMOVE)
    private Set<Content> collectContents;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "buyUser")
    private Set<ContentBuy> buyContentSet;

    @OneToMany(mappedBy = "msgSendUser", cascade = CascadeType.REMOVE)
    private Set<CmsMessage> sendMessages;

    @OneToMany(mappedBy = "msgReceiverUser", cascade = CascadeType.REMOVE)
    private Set<CmsMessage> receivMessages;

    @OneToMany(mappedBy = "msgSendUser", cascade = CascadeType.REMOVE)
    private Set<CmsReceiverMessage> sendReceiverMessages;

    @OneToMany(mappedBy = "msgReceiverUser", cascade = CascadeType.REMOVE)
    private Set<CmsReceiverMessage> receivReceiverMessages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<CmsJobApply> jobApplys;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private CmsUserResume userResume;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<CmsLog> logs;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<CmsUserMenu> menus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ContentRecord> contentRecordSet;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    private Set<CmsThirdAccount> thirdAccounts;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }


    public String getRegisterIp() {
        return registerIp;
    }


    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }


    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }


    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


    public Long getUploadTotal() {
        return uploadTotal;
    }


    public void setUploadTotal(Long uploadTotal) {
        this.uploadTotal = uploadTotal;
    }


    public Integer getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(Integer uploadSize) {
        this.uploadSize = uploadSize;
    }


    public Date getUploadDate() {
        return uploadDate;
    }


    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }


    public Boolean getAdmin() {
        return admin;
    }


    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public Boolean getViewonlyAdmin() {
        return viewonlyAdmin;
    }

    public void setViewonlyAdmin(Boolean viewonlyAdmin) {
        this.viewonlyAdmin = viewonlyAdmin;
    }


    public Boolean getSelfAdmin() {
        return selfAdmin;
    }

    public void setSelfAdmin(Boolean selfAdmin) {
        this.selfAdmin = selfAdmin;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public CmsGroup getGroup() {
        return group;
    }

    public void setGroup(CmsGroup group) {
        this.group = group;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public CmsUserExt getUserExt() {
        return userExt;
    }

    public void setUserExt(CmsUserExt userExt) {
        this.userExt = userExt;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }


    public Set<Content> getCollectContents() {
        return collectContents;
    }

    public void setCollectContents(
            Set<Content> collectContents) {
        this.collectContents = collectContents;
    }

    public Set<ContentBuy> getBuyContentSet() {
        return buyContentSet;
    }

    public void setBuyContentSet(Set<ContentBuy> buyContentSet) {
        this.buyContentSet = buyContentSet;
    }

    public Set<CmsMessage> getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(
            Set<CmsMessage> sendMessages) {
        this.sendMessages = sendMessages;
    }

    public Set<CmsMessage> getReceivMessages() {
        return receivMessages;
    }

    public void setReceivMessages(
            Set<CmsMessage> receivMessages) {
        this.receivMessages = receivMessages;
    }

    public Set<CmsReceiverMessage> getSendReceiverMessages() {
        return sendReceiverMessages;
    }

    public void setSendReceiverMessages(
            Set<CmsReceiverMessage> sendReceiverMessages) {
        this.sendReceiverMessages = sendReceiverMessages;
    }

    public Set<CmsReceiverMessage> getReceivReceiverMessages() {
        return receivReceiverMessages;
    }

    public void setReceivReceiverMessages(
            Set<CmsReceiverMessage> receivReceiverMessages) {
        this.receivReceiverMessages = receivReceiverMessages;
    }

    public Set<CmsJobApply> getJobApplys() {
        return jobApplys;
    }

    public void setJobApplys(
            Set<CmsJobApply> jobApplys) {
        this.jobApplys = jobApplys;
    }


    public Set<CmsLog> getLogs() {
        return logs;
    }

    public void setLogs(Set<CmsLog> logs) {
        this.logs = logs;
    }

    public Set<CmsUserMenu> getMenus() {
        return menus;
    }

    public void setMenus(
            Set<CmsUserMenu> menus) {
        this.menus = menus;
    }


    public Set<ContentRecord> getContentRecordSet() {
        return contentRecordSet;
    }

    public void setContentRecordSet(Set<ContentRecord> contentRecordSet) {
        this.contentRecordSet = contentRecordSet;
    }

    public Set<CmsThirdAccount> getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(Set<CmsThirdAccount> thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }

    public String getRealname() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getRealname();
        } else {
            return null;
        }
    }

    public Boolean getGender() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getGender();
        } else {
            return null;
        }
    }

    public Date getBirthday() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getBirthday();
        } else {
            return null;
        }
    }

    public String getIntro() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getIntro();
        } else {
            return null;
        }
    }

    public String getComefrom() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getComefrom();
        } else {
            return null;
        }
    }

    public String getQq() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getQq();
        } else {
            return null;
        }
    }

    public String getMsn() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getMsn();
        } else {
            return null;
        }
    }

    public String getPhone() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getPhone();
        } else {
            return null;
        }
    }

    public String getMobile() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getMobile();
        } else {
            return null;
        }
    }

    public String getUserImg() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getUserImg();
        } else {
            return null;
        }
    }

    public String getUserSignature() {
        CmsUserExt ext = getUserExt();
        if (ext != null) {
            return ext.getUserSignature();
        } else {
            return null;
        }
    }

    public String getAccountWeixin() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getAccountWeixin();
        } else {
            return null;
        }
    }

    public String getAccountAlipy() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getAccountAlipy();
        } else {
            return null;
        }
    }

    public Short getDrawAccount() {
        CmsUserAccount ext = getUserAccount();
        if (ext != null) {
            return ext.getDrawAccount();
        } else {
            return 0;
        }
    }

    public CmsUserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(CmsUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public CmsUserResume getUserResume() {
        return userResume;
    }

    public void setUserResume(CmsUserResume userResume) {
        this.userResume = userResume;
    }

    public Set<Channel> getChannels(Integer siteId) {
        Set<Channel> set = getChannels();
        Set<Channel> results = new HashSet<Channel>();
        for (Channel c : set) {
            if (c.getSite().getId().equals(siteId)) {
                results.add(c);
            }
        }
        return results;
    }


    public Integer[] getChannelIds() {
        Set<Channel> channels = getChannels();
        return Channel.fetchIds(channels);
    }

    public Set<Integer> getChannelIds(Integer siteId) {
        Set<Channel> channels = getChannels();
        Set<Integer> ids = new HashSet<Integer>();
        for (Channel c : channels) {
            if (c.getSite().getId().equals(siteId)) {
                ids.add(c.getId());
            }
        }
        return ids;
    }

    public void addToChannels(Channel channel) {
        if (channel == null) {
            return;
        }
        Set<Channel> set = getChannels();
        if (set == null) {
            set = new HashSet<>();
            setChannels(set);
        }
        set.add(channel);
    }

    public void addToCollection(Content content) {
        if (content == null) {
            return;
        }
        Set<Content> set = getCollectContents();
        if (set == null) {
            set = new HashSet<>();
            setCollectContents(set);
        }
        set.add(content);
    }

    public void delFromCollection(Content content) {
        if (content == null) {
            return;
        }
        Set<Content> set = getCollectContents();
        if (set == null) {
            return;
        } else {
            set.remove(content);
        }
    }

    public void clearCollection() {
        getCollectContents().clear();
    }

    public Set<Content> getApplyContent() {
        Set<CmsJobApply> jobApplys = getJobApplys();
        Set<Content> contents = new HashSet<>(jobApplys.size());
        for (CmsJobApply apply : jobApplys) {
            contents.add(apply.getContent());
        }
        return contents;
    }

    public boolean hasApplyToday(Integer contentId) {
        Date now = Calendar.getInstance().getTime();
        Set<CmsJobApply> jobApplys = getJobApplys();
        for (CmsJobApply apply : jobApplys) {
            if (DateUtils.isInDate(now, apply.getApplyTime()) && apply.getContent().getId().equals(contentId)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否允许上传，根据每日限额
     *
     * @param size
     * @return
     */
    public boolean isAllowPerDay(int size) {
        int allowPerDay = getGroup().getAllowPerDay();
        if (allowPerDay == 0) {
            return true;
        }
        if (getUploadDate() != null) {
            if (isToday(getUploadDate())) {
                size += getUploadSize();
            }
        }
        return allowPerDay >= size;
    }

    /**
     * 是否允许上传，根据文件大小
     *
     * @param size 文件大小，单位KB
     * @return
     */
    public boolean isAllowMaxFile(int size) {
        int allowPerFile = getGroup().getAllowMaxFile();
        if (allowPerFile == 0) {
            return true;
        } else {
            return allowPerFile >= size;
        }
    }

    /**
     * 是否允许上传后缀
     *
     * @param ext
     * @return
     */
    public boolean isAllowSuffix(String ext) {
        return getGroup().isAllowSuffix(ext);
    }

    public void forMember(UnifiedUser u) {
        forUser(u);
        setAdmin(false);
        setRank(0);
        setViewonlyAdmin(false);
        setSelfAdmin(false);
    }

    public void forAdmin(UnifiedUser u, boolean viewonly, boolean selfAdmin,
                         int rank) {
        forUser(u);
        setAdmin(true);
        setRank(rank);
        setViewonlyAdmin(viewonly);
        setSelfAdmin(selfAdmin);
    }

    public void forUser(UnifiedUser u) {
        setStatu(USER_STATU_CHECKED);
        setId(u.getId());
        setUsername(u.getUsername());
        setEmail(u.getEmail());
        setRegisterIp(u.getRegisterIp());
        setRegisterTime(u.getRegisterTime());
        setLastLoginIp(u.getLastLoginIp());
        setLastLoginTime(u.getLastLoginTime());
        setLoginCount(0);
    }

    public boolean hasBuyContent(Content c) {
        boolean hasBuy = false;
        Set<ContentBuy> buys = getBuyContentSet();
        for (ContentBuy b : buys) {
            if (b.getContent().equals(c) && b.getUserHasPaid()) {
                hasBuy = true;
                break;
            }
        }
        return hasBuy;
    }

    public void init() {
        if (getUploadTotal() == null) {
            setUploadTotal(0L);
        }
        if (getUploadSize() == null) {
            setUploadSize(0);
        }
        if (getUploadDate() == null) {
            setUploadDate(new Date(System.currentTimeMillis()));
        }
        if (getAdmin() == null) {
            setAdmin(false);
        }
        if (getRank() == null) {
            setRank(0);
        }
        if (getViewonlyAdmin() == null) {
            setViewonlyAdmin(false);
        }
        if (getSelfAdmin() == null) {
            setSelfAdmin(false);
        }
        if (getStatu() == null) {
            setStatu(USER_STATU_CHECKED);
        }
        getDisabled();
    }

    public static Integer[] fetchIds(Collection<CmsUser> users) {
        if (users == null) {
            return null;
        }
        Integer[] ids = new Integer[users.size()];
        int i = 0;
        for (CmsUser u : users) {
            ids[i++] = u.getId();
        }
        return ids;
    }

    /**
     * 用于排列顺序。此处优先级为0，则按ID升序排。
     */
    @Override
    public Number getPriority() {
        return 0;
    }

    /**
     * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        long day = date.getTime() / 1000 / 60 / 60 / 24;
        long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
        return day == (currentDay - 1);
    }

    public JSONObject convertToJson(CmsSite site, Integer https, Boolean isLocal)
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getUsername())) {
            json.put("username", getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(getEmail())) {
            json.put("email", getEmail());
        } else {
            json.put("email", "");
        }
        json.put("grain", "");
        if (getRegisterTime() != null) {
            json.put("registerTime", DateUtils.parseDateToTimeStr(getRegisterTime()));
        } else {
            json.put("registerTime", "");
        }
        if (StringUtils.isNotBlank(getRegisterIp())) {
            json.put("registerIp", getRegisterIp());
        } else {
            json.put("registerIp", "");
        }
        if (getLastLoginTime() != null) {
            json.put("lastLoginTime", DateUtils.parseDateToTimeStr(getLastLoginTime()));
        } else {
            json.put("lastLoginTime", "");
        }
        if (StringUtils.isNotBlank(getLastLoginIp())) {
            json.put("lastLoginIp", getLastLoginIp());
        } else {
            json.put("lastLoginIp", "");
        }
        if (getLoginCount() != null) {
            json.put("loginCount", getLoginCount());
        } else {
            json.put("loginCount", "");
        }
        if (getGroup() != null && StringUtils.isNotBlank(getGroup().getName())) {
            json.put("groupName", getGroup().getName());
        } else {
            json.put("groupName", "");
        }
        if (getGroup() != null && getGroup().getId() != null) {
            json.put("groupId", getGroup().getId());
        } else {
            json.put("groupId", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getRealname())) {
            json.put("realname", getUserExt().getRealname());
        } else {
            json.put("realname", "");
        }

        if (getUserExt() != null && getUserExt().getGender() != null) {
            json.put("gender", getUserExt().getGender());
        } else {
            json.put("gender", "");
        }
        if (getUserExt() != null && getUserExt().getBirthday() != null) {
            json.put("birthday", DateUtils.parseDateToDateStr(getUserExt().getBirthday()));
        } else {
            json.put("birthday", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getIntro())) {
            json.put("intro", getUserExt().getIntro());
        } else {
            json.put("intro", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getComefrom())) {
            json.put("comefrom", getUserExt().getComefrom());
        } else {
            json.put("comefrom", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getQq())) {
            json.put("qq", getUserExt().getQq());
        } else {
            json.put("qq", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getMsn())) {
            json.put("msn", getUserExt().getMsn());
        } else {
            json.put("msn", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getPhone())) {
            json.put("phone", getUserExt().getPhone());
        } else {
            json.put("phone", "");
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getMobile())) {
            json.put("mobile", getUserExt().getMobile());
        } else {
            json.put("mobile", "");
        }
        //获取密码最小长度
        if (StringUtils.isNotBlank("" + site.getPasswordMinLen())) {
            json.put("passwordMinLen", site.getPasswordMinLen());
        } else {
            json.put("passwordMinLen", "3");
        }

        String urlPrefix = "";
        if (https == Constants.URL_HTTP) {
            urlPrefix = site.getUrlPrefixWithNoDefaultPort();
        } else {
            urlPrefix = site.getSafeUrlPrefix();
        }
        Ftp uploadFtp = site.getUploadFtp();
        boolean uploadToFtp = false;
        if (uploadFtp != null) {
            uploadToFtp = true;
        }
        if (!uploadToFtp) {
            if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getUserImg())) {
                json.put("userImg", urlPrefix + getUserExt().getUserImg());
            } else {
                json.put("userImg", "");
            }
        } else {
            if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getUserImg())) {
                json.put("userImg", getUserExt().getUserImg());
            } else {
                json.put("userImg", "");
            }
        }
        if (getUserExt() != null && StringUtils.isNotBlank(getUserExt().getUserSignature())) {
            json.put("userSignature", getUserExt().getUserSignature());
        } else {
            json.put("userSignature", "");
        }
        Set<CmsThirdAccount> accounts = getThirdAccounts();
        if (accounts != null && accounts.size() > 0) {
            json.put("thirdAccount", true);
        } else {
            json.put("thirdAccount", false);
        }
        Set<CmsConfigItem> configItems = site.getConfig().getRegisterItems();
        if (site.getConfig() != null && site.getConfig().getRegisterItems() != null) {
            Map<String, String> attr = getAttr();
            for (CmsConfigItem item : configItems) {
                //多选需要传递数组方便前端处理
                if (item.getDataType().equals(CmsModelItem.DATA_TYPE_CHECKBOX)) {
                    String[] attrValArray = null;
                    JSONArray jsonArray = new JSONArray();
                    if (attr != null && StringUtils.isNotBlank(attr.get(item.getField()))) {
                        attrValArray = attr.get(item.getField()).split(",");
                        if (attrValArray != null) {
                            for (int i = 0; i < attrValArray.length; i++) {
                                jsonArray.put(i, attrValArray[i]);
                            }
                        }
                    }
                    json.put("attr_" + item.getField(), jsonArray);
                } else {
                    if (attr != null && StringUtils.isNotBlank(attr.get(item.getField()))) {
                        json.put("attr_" + item.getField(), attr.get(item.getField()));
                    }
                }
            }
        }
        json.put("disabled", getDisabled());
        if (getRank() != null) {
            json.put("rank", getRank());
        } else {
            json.put("rank", "");
        }
        if (getSelfAdmin() != null) {
            json.put("selfAdmin", getSelfAdmin());
        } else {
            json.put("selfAdmin", "");
        }
        JSONArray allChannels = new JSONArray();
        JSONArray steps = new JSONArray();
        json.put("allChannels", allChannels);
        json.put("steps", steps);
        json.put("channelIds", getChannelIds());
        return json;
    }

    private JSONObject createEasyJson(CmsSite cmsSite) {
        JSONObject siteJson = new JSONObject();
        if (cmsSite.getId() != null) {
            siteJson.put("id", cmsSite.getId());
        } else {
            siteJson.put("id", "");
        }
        if (StringUtils.isNotBlank(cmsSite.getName())) {
            siteJson.put("name", cmsSite.getName());
        } else {
            siteJson.put("name", "");
        }
        return siteJson;
    }

    public boolean getDisabled() {
        Integer statu = getStatu();
        if (statu.equals(USER_STATU_DISABLED)) {
            return true;
        } else {
            return false;
        }
    }
}