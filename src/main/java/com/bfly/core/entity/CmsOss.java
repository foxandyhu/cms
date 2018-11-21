package com.bfly.core.entity;

import com.bfly.common.upload.AliOssCloudClient;
import com.bfly.common.upload.QCoscloudClient;
import com.bfly.common.upload.QiniuOssCloudClient;
import com.bfly.common.upload.UploadUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * oss云存储配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:51
 */
@Entity
@Table(name = "jc_oss")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsOss implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final byte OSS_TYPE_Q_CLOUD = 1;

    public static final byte OSS_TYPE_QINIU_CLOUD = 3;

    public static final byte OSS_TYPE_ALI_CLOUD = 2;

    public enum OSSTYPE {
        /**
         * 腾讯云cos
         */
        QCLOUD,
        /**
         * 七牛云oss
         */
        QINIUCLOUD,
        /**
         * 阿里云oss
         */
        ALICLOUD,
        /**
         * 百度云
         */
        BAIDUCLOUD
    }

    public CmsOss () { }

    public CmsOss(String ossAppId, String secretId,
                  String appKey, String bucketName, String bucketArea,
                  String endPoint, String accessDomain, Byte ossType) {
        this.ossAppId=ossAppId;
        this.secretId=secretId;
        this.appKey=appKey;
        this.bucketName=bucketName;
        this.bucketArea=bucketArea;
        this.endPoint=endPoint;
        this.accessDomain=accessDomain;
        this.ossType=ossType;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "app_id")
    private String ossAppId;

    @Column(name = "secret_id")
    private String secretId;

    /**
     * secret key
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * bucket名
     */
    @Column(name = "bucket_name")
    private String bucketName;

    /**
     * 地区码
     */
    @Column(name = "bucket_area")
    private String bucketArea;

    @Column(name = "end_point")
    private String endPoint;

    /**
     * 访问域名
     */
    @Column(name = "access_domain")
    private String accessDomain;

    /**
     * 名称
     */
    @Column(name = "oss_name")
    private String name;

    /**
     * 存储类型(1腾讯云cos  2阿里云oss  3七牛云)
     */
    @Column(name = "oss_type")
    private Byte ossType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOssAppId() {
        return ossAppId;
    }

    public void setOssAppId(String ossAppId) {
        this.ossAppId = ossAppId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }


    public String getAppKey() {
        return appKey;
    }


    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketArea() {
        return bucketArea;
    }

    public void setBucketArea(String bucketArea) {
        this.bucketArea = bucketArea;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }


    public String getAccessDomain() {
        return accessDomain;
    }

    public void setAccessDomain(String accessDomain) {
        this.accessDomain = accessDomain;
    }

    public Byte getOssType() {
        return ossType;
    }

    public void setOssType(Byte ossType) {
        this.ossType = ossType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String storeByExt(String path, String ext, InputStream in)
            throws IOException {
        String remoteFileName = UploadUtils.generateFilename(path, ext);
        String fileOssUrl = store(remoteFileName, in);
        return fileOssUrl;
    }

    public String storeByFilename(String filename, InputStream in)
            throws IOException {
        String fileOssUrl = store(filename, in);
        return fileOssUrl;
    }

    private String store(String remote, InputStream in) {
        String result = "";
        String fileUrl = "";
        if (getOssType().equals(OSS_TYPE_Q_CLOUD)) {
            result = QCoscloudClient.uploadFileByInputStream(this, remote, in);
            if (StringUtils.isNotBlank(result) && result.startsWith("{")) {
                JSONObject json = new JSONObject(result);
                Object code = json.get("code");
                if (code != null && code.equals(0)) {
                    fileUrl = json.getJSONObject("data").getString("source_url");
                }
            }
        } else if (getOssType().equals(OSS_TYPE_ALI_CLOUD)) {
            boolean succ = AliOssCloudClient.uploadFileByInputStream(this, remote, in);
            if (succ) {
                //阿里云存储accessDomain设置不带http://
                fileUrl = "http://" + getBucketName() + "." + this.getAccessDomain() + remote;
            }
        } else if (getOssType().equals(OSS_TYPE_QINIU_CLOUD)) {
            Map<String, String> map = QiniuOssCloudClient.uploadFileByInputStream(this, remote, in);
            if (StringUtils.isNotBlank(map.get(QiniuOssCloudClient.RESULT_KEY))) {
                //七牛云存存储accessDomain得设置带http://
                fileUrl = this.getAccessDomain() + remote;
            }
        }
        return fileUrl;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getOssAppId())) {
            json.put("ossAppId", getOssAppId());
        } else {
            json.put("ossAppId", "");
        }
        if (StringUtils.isNotBlank(getSecretId())) {
            json.put("secretId", getSecretId());
        } else {
            json.put("secretId", "");
        }
        if (StringUtils.isNotBlank(getAppKey())) {
            json.put("appKey", getAppKey());
        } else {
            json.put("appKey", "");
        }
        if (StringUtils.isNotBlank(getBucketName())) {
            json.put("bucketName", getBucketName());
        } else {
            json.put("bucketName", "");
        }
        if (StringUtils.isNotBlank(getBucketArea())) {
            json.put("bucketArea", getBucketArea());
        } else {
            json.put("bucketArea", "");
        }
        if (StringUtils.isNotBlank(getEndPoint())) {
            json.put("endPoint", getEndPoint());
        } else {
            json.put("endPoint", "");
        }
        if (StringUtils.isNotBlank(getAccessDomain())) {
            json.put("accessDomain", getAccessDomain());
        } else {
            json.put("accessDomain", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (getOssType() != null) {
            json.put("ossType", getOssType());
        } else {
            json.put("ossType", "");
        }
        return json;
    }
}