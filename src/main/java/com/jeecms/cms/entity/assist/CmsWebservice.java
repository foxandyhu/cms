package com.jeecms.cms.entity.assist;

import org.hibernate.annotations.Cache;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口表
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:47
 */
@Entity
@Table(name = "jc_webservice")
public class CmsWebservice implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SERVICE_TYPE_ADD_USER = "addUser";
    public static final String SERVICE_TYPE_UPDATE_USER = "updateUser";
    public static final String SERVICE_TYPE_DELETE_USER = "deleteUser";

    public void addToParams(String name, String defaultValue) {
        List<CmsWebserviceParam> list = getParams();
        if (list == null) {
            list = new ArrayList<>();
            setParams(list);
        }
        CmsWebserviceParam param = new CmsWebserviceParam();
        param.setParamName(name);
        param.setDefaultValue(defaultValue);
        list.add(param);
    }

    public JSONObject convertToJson(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getAddress())) {
            json.put("address", getAddress());
        } else {
            json.put("address", "");
        }
        if (StringUtils.isNotBlank(getTargetNamespace())) {
            json.put("targetNamespace", getTargetNamespace());
        } else {
            json.put("targetNamespace", "");
        }
        if (StringUtils.isNotBlank(getSuccessResult())) {
            json.put("successResult", getSuccessResult());
        } else {
            json.put("successResult", "");
        }
        if (StringUtils.isNotBlank(getType())) {
            json.put("type", getType());
        } else {
            json.put("type", "");
        }
        if (StringUtils.isNotBlank(getOperate())) {
            json.put("operate", getOperate());
        } else {
            json.put("operate", "");
        }
        JSONArray jsonArray = new JSONArray();
        if (getParams() != null && getParams().size() > 0) {
            List<CmsWebserviceParam> list = getParams();
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        json.put("params", jsonArray);
        return json;
    }

    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接口地址
     */
    @Column(name = "service_address")
    private String address;

    @Column(name = "target_namespace")
    private String targetNamespace;

    /**
     * 正确返回值
     */
    @Column(name = "success_result")
    private String successResult;

    /**
     * 接口类型
     */
    @Column(name = "service_type")
    private String type;

    /**
     * 接口操作
     */
    @Column(name = "service_operate")
    private String operate;

    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
    @OrderColumn(name = "priority")
    @CollectionTable(name = "jc_webservice_param",joinColumns = @JoinColumn(name = "service_id"))
    private List<CmsWebserviceParam> params;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }


    public String getSuccessResult() {
        return successResult;
    }


    public void setSuccessResult(String successResult) {
        this.successResult = successResult;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getOperate() {
        return operate;
    }


    public void setOperate(String operate) {
        this.operate = operate;
    }


    public List<CmsWebserviceParam> getParams() {
        return params;
    }

    public void setParams(List<CmsWebserviceParam> params) {
        this.params = params;
    }


}