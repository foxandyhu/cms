package com.jeecms.core.entity;

import com.jeecms.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * SMS短信服务配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:56
 */
@Entity
@Table(name = "jc_sms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSms implements Serializable {
    private static final long serialVersionUID = 1L;

    //阿里固定参数
    //短信API产品名称
    public static final String product = "Dysmsapi";
    //短信API产品域名
    public static final String domain = "dysmsapi.aliyuncs.com";
    //初始化ascClient,暂时不支持多region
    public static final String regionId = "cn-hangzhou";
    public static final String endpointName = "cn-hangzhou";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 消息服务名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 发送账号安全认证的Access Key ID/appId 注：不同接口参数类型可能不同
     */
    @Column(name = "access_key_id")
    private String accessKeyId;

    /**
     * 发送账号安全认证的Secret Access Key/appKey
     */
    @Column(name = "access_key_secret")
    private String accessKeySecret;

    /**
     * 本次发送使用的模板Code 注：不同接口参数类型可能不同
     */
    @Column(name = "template_code")
    private String templateCode;

    /**
     * 模板参数 注：不同接口参数类型可能不同
     */
    @Column(name = "template_param")
    private String templateParam;

    /**
     * 每次发送时间间隔
     */
    @Column(name = "interval_time")
    private Integer intervalTime;

    /**
     * 间隔时间单位 0秒 1分 2时
     */
    @Column(name = "interval_unit")
    private Byte intervalUnit;

    /**
     * 二维码有效时间
     */
    @Column(name = "effective_time")
    private Integer effectiveTime;

    /**
     * 有效时间单位 0秒 1分 2时
     */
    @Column(name = "effective_unit")
    private Byte effectiveUnit;

    /**
     * 短信签名(阿里)
     */
    @Column(name = "sign_name")
    private String signName;

    /**
     * 上行短信扩展码(阿里)
     */
    @Column(name = "sms_up_extend_code")
    private String smsUpExtendCode;

    /**
     * 提供给业务方扩展字段(阿里)
     */
    @Column(name = "out_id")
    private String outId;

    /**
     * 区域码(腾讯)
     */
    @Column(name = "nation_code")
    private String nationCode;

    /**
     * SMS服务域名，可根据环境选择具体域名(百度)
     */
    @Column(name = "end_point")
    private String endPoint;

    /**
     * 发送使用签名的调用ID(百度)
     */
    @Column(name = "invoke_id")
    private String invokeId;

    /**
     * SMS服务平台1阿里 2腾讯 3百度
     */
    @Column(name = "sms_source")
    private Byte source;

    /**
     * 是否为验证码模板
     */
    @Column(name = "is_code")
    private Boolean isCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 验证码位数
     */
    @Column(name = "random_num")
    private Integer randomNum;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sms")
    private Set<CmsSmsRecord> smsRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Boolean getIsCode() {
        return isCode;
    }

    public void setIsCode(Boolean isCode) {
        this.isCode = isCode;
    }

    public Byte getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(Byte intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public Byte getEffectiveUnit() {
        return effectiveUnit;
    }

    public void setEffectiveUnit(Byte effectiveUnit) {
        this.effectiveUnit = effectiveUnit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(Integer randomNum) {
        this.randomNum = randomNum;
    }

    public Set<CmsSmsRecord> getSmsRecords() {
        return smsRecords;
    }

    public void setSmsRecords(Set<CmsSmsRecord> smsRecords) {
        this.smsRecords = smsRecords;
    }


    public JSONObject convertToJson(boolean isList) {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getAccessKeyId())) {
            json.put("accessKeyId", "");
        } else {
            json.put("accessKeyId", "");
        }
        if (StringUtils.isNotBlank(getAccessKeySecret())) {
            json.put("accessKeySecret", "");
        } else {
            json.put("accessKeySecret", "");
        }
        if (getSource() != null) {
            json.put("source", getSource());
        } else {
            json.put("source", "");
        }
        if (getSource() != null) {
            Byte sourceType = getSource();
            String sourceName = "未知";
            if (sourceType == 1) {
                sourceName = "阿里云";
            } else if (sourceType == 3) {
                sourceName = "百度云";
            } else if (sourceType == 2) {
                sourceName = "腾讯云";
            }

            json.put("sourceName", sourceName);
        } else {
            json.put("sourceName", "");
        }
        if (getIsCode() != null) {
            json.put("isCode", getIsCode());
        } else {
            json.put("isCode", "");
        }
        if (getCreateTime() != null) {
            json.put("createTime", DateUtils.parseDateToTimeStr(getCreateTime()));
        } else {
            json.put("createTime", "");
        }
        if (!isList) {
            if (StringUtils.isNotBlank(getTemplateCode())) {
                json.put("templateCode", getTemplateCode());
            } else {
                json.put("templateCode", "");
            }
            if (getIntervalTime() != null) {
                json.put("intervalTime", getIntervalTime());
            } else {
                json.put("intervalTime", "");
            }
            if (getIntervalUnit() != null) {
                json.put("intervalUnit", getIntervalUnit());
            } else {
                json.put("intervalUnit", "");
            }
            if (getEffectiveTime() != null) {
                json.put("effectiveTime", getEffectiveTime());
            } else {
                json.put("effectiveTime", "");
            }
            if (getEffectiveUnit() != null) {
                json.put("effectiveUnit", getEffectiveUnit());
            } else {
                json.put("effectiveUnit", "");
            }
            if (StringUtils.isNotBlank(getSignName())) {
                json.put("signName", getSignName());
            } else {
                json.put("signName", "");
            }
            if (StringUtils.isNotBlank(getSmsUpExtendCode())) {
                json.put("smsUpExtendCode", getSmsUpExtendCode());
            } else {
                json.put("smsUpExtendCode", "");
            }
            if (StringUtils.isNotBlank(getOutId())) {
                json.put("outId", getOutId());
            } else {
                json.put("outId", "");
            }
            if (StringUtils.isNotBlank(getNationCode())) {
                json.put("nationCode", getNationCode());
            } else {
                json.put("nationCode", "");
            }
            if (StringUtils.isNotBlank(getEndPoint())) {
                json.put("endPoint", getEndPoint());
            } else {
                json.put("endPoint", "");
            }
            if (StringUtils.isNotBlank(getInvokeId())) {
                json.put("invokeId", getInvokeId());
            } else {
                json.put("invokeId", "");
            }
            if (getRandomNum() != null) {
                json.put("randomNum", getRandomNum());
            } else {
                json.put("randomNum", "");
            }
            JSONArray paramArr = new JSONArray();
            if (StringUtils.isNotBlank(getTemplateParam())) {
                String param = getTemplateParam();
                String[] params = param.split(",");
                for (int i = 0; i < params.length; i++) {
                    paramArr.put(i, params[i]);
                }
            }
            json.put("templateParam", paramArr);
        }
        return json;
    }

    public void init() {
        if (getIntervalTime() == null) {
            setIntervalTime(0);
        }
        if (getIntervalUnit() == null) {
            setIntervalUnit((byte) 0);
        }
        if (getEffectiveTime() == null) {
            setEffectiveTime(0);
        }
        if (getEffectiveUnit() == null) {
            setEffectiveUnit((byte) 0);
        }
        if (getIsCode() == null) {
            setIsCode(false);
        }
    }
}
