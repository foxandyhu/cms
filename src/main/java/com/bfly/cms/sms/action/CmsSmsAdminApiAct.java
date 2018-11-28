package com.bfly.cms.sms.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.AES128Util;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.sms.service.CmsSmsMng;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 短信服务Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 15:57
 */
@Controller("adminCmsSmsApiAct")
@RequestMapping(value = "/api/admin")
public class CmsSmsAdminApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsSmsAdminApiAct.class);

    /**
     * 短信服务列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:02
     */
    @RequestMapping("/sms/list")
    public void list(Byte source, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(source, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsSms> list = (List<CmsSms>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(true));
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 短信服务详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:02
     */
    @RequestMapping("/sms/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsSms bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsSms();
            } else {
                bean = manager.findById(id);
            }
            if (bean == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean.init();
                body = bean.convertToJson(false).toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 新增短信服务
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:03
     */
    @SignValidate
    @RequestMapping("/sms/save")
    public void save(CmsSms bean, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(),
                bean.getAccessKeyId(), bean.getAccessKeySecret(), bean.getTemplateCode());
        if (!errors.hasErrors()) {
            bean.init();
            ApiAccount account = apiManager.getApiAccount(request);
            //appid,appkey需要进行解密保存
            bean.setAccessKeyId(AES128Util.decrypt(bean.getAccessKeyId(), account.getAesKey(), account.getIvKey()));
            bean.setAccessKeySecret(AES128Util.decrypt(bean.getAccessKeySecret(), account.getAesKey(), account.getIvKey()));
            bean.setCreateTime(new Date());
            bean = manager.save(bean);
            log.info("save CmsSms id={}", bean.getId());
            body = "{\"id\":" + bean.getId() + "}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改短信服务
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:03
     */
    @SignValidate
    @RequestMapping("/sms/update")
    public void update(CmsSms bean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getTemplateCode());
        if (!errors.hasErrors()) {
            ApiAccount account = apiManager.getApiAccount(request);
            //appid,appkey需要进行解密保存
            if (StringUtils.isNotBlank(bean.getAccessKeyId())) {
                bean.setAccessKeyId(AES128Util.decrypt(bean.getAccessKeyId(), account.getAesKey(), account.getIvKey()));
            }
            if (StringUtils.isNotBlank(bean.getAccessKeySecret())) {
                bean.setAccessKeySecret(AES128Util.decrypt(bean.getAccessKeySecret(), account.getAesKey(), account.getIvKey()));
            }
            bean = manager.update(bean);
            log.info("update CmsSms id={}", bean.getId());
            body = "{\"id\":" + bean.getId() + "}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除短信服务
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:03
     */
    @SignValidate
    @RequestMapping("/sms/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            errors = validateDelete(errors, idArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                try {
                    CmsSms[] beans = manager.deleteByIds(idArr);
                    for (CmsSms cms:beans) {
                        log.info("delete CmsSms id={}", cms.getId());
                    }
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                } catch (Exception e) {
                    message = Constants.API_MESSAGE_DELETE_ERROR;
                    code = ResponseCode.API_CODE_DELETE_ERROR;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    private WebErrors validateDelete(WebErrors errors, Integer[] idArr) {
        if (idArr != null && idArr.length > 0) {
            for (Integer id:idArr) {
                errors = validateExist(errors, id);
                if (errors.hasErrors()) {
                    return errors;
                }
            }
        }
        return errors;
    }

    private WebErrors validateExist(WebErrors errors, Integer id) {
        if (id != null) {
            CmsSms bean = manager.findById(id);
            if (bean == null) {
                errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                return errors;
            }
        }
        return errors;
    }

    @Autowired
    private ApiAccountMng apiManager;
    @Autowired
    private CmsSmsMng manager;
}
