package com.bfly.cms.siteconfig.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.common.util.AES128Util;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.service.CmsOssMng;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 云存储配置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:16
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsOssApiAct {

    private static final Logger log = LoggerFactory.getLogger(CmsOssApiAct.class);

    /**
     * OSS列表
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:17
     */
    @RequestMapping("/oss/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<CmsOss> list = manager.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * OSS详情
     *
     * @param id       OSS编号
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:17
     */
    @RequestMapping("/oss/get")
    public void get(Integer id, HttpServletResponse response, HttpServletRequest request) {
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        String body = "\"\"";
        CmsOss bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsOss();
            } else {
                bean = manager.findById(id);
            }
            if (bean != null) {
                JSONObject jsonObject = bean.convertToJson();
                body = jsonObject.toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } else {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * OSS新增
     *
     * @param bean     OSS对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:18
     */
    @SignValidate
    @RequestMapping("/oss/save")
    public void add(CmsOss bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors,
                bean.getSecretId(), bean.getAppKey(), bean.getName(), bean.getOssType(), bean.getBucketName());
        if (!errors.hasErrors()) {
            ApiAccount account = apiManager.getApiAccount(request);
            //appkey secretId解密保存
            bean.setAppKey(AES128Util.decrypt(bean.getAppKey(), account.getAesKey(), account.getIvKey()));
            bean.setSecretId(AES128Util.decrypt(bean.getSecretId(), account.getAesKey(), account.getIvKey()));
            bean = manager.save(bean);
            log.info("save OSS id={}", bean.getId());
            cmsLogMng.operating(request, "oss.log.save", "id=" + bean.getId()
                    + ";name=" + bean.getBucketName());
            body = "{\"id\":" + "\"" + bean.getId() + "\"}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * OSS修改
     *
     * @param bean     OSS对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception 异常
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:18
     */
    @SignValidate
    @RequestMapping("/oss/update")
    public void update(CmsOss bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors,
                bean.getId(), bean.getName(), bean.getOssType(), bean.getBucketName());
        if (!errors.hasErrors()) {
            ApiAccount account = apiManager.getApiAccount(request);
            if (StringUtils.isNotBlank(bean.getAppKey())) {
                //appkey解密
                bean.setAppKey(AES128Util.decrypt(bean.getAppKey(), account.getAesKey(), account.getIvKey()));

            }
            if (StringUtils.isNotBlank(bean.getSecretId())) {
                //secretId解密
                bean.setSecretId(AES128Util.decrypt(bean.getSecretId(), account.getAesKey(), account.getIvKey()));
            }
            bean = manager.update(bean);
            log.info("update oss id={}", bean.getId());
            cmsLogMng.operating(request, "oss.log.update", "id=" + bean.getId()
                    + ";name=" + bean.getBucketName());
            body = "{\"id\":" + "\"" + bean.getId() + "\"}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * OSS删除
     *
     * @param ids      OSS编号组
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:19
     */
    @SignValidate
    @RequestMapping("/oss/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            try {
                Integer[] idArray = StrUtils.getInts(ids);
                CmsOss[] ossEs = manager.deleteByIds(idArray);
                for (CmsOss oss : ossEs) {
                    cmsLogMng.operating(request, "oss.log.delete", "id=" + oss.getId() + ";name=" + oss.getBucketName());
                    log.info("delete oss id={}", oss.getId());
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            } catch (Exception e) {
                message = Constants.API_MESSAGE_DELETE_ERROR;
                code = ResponseCode.API_CODE_DELETE_ERROR;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsOssMng manager;
    @Autowired
    private ApiAccountMng apiManager;
}
