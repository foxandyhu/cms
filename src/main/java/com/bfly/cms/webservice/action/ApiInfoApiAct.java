package com.bfly.cms.webservice.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.webservice.entity.ApiInfo;
import com.bfly.cms.webservice.service.ApiInfoMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.WebErrors;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * API接口信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:38
 */
@Controller
@RequestMapping(value = "/api/admin")
public class ApiInfoApiAct {
    private static final Logger log = LoggerFactory.getLogger(ApiInfoApiAct.class);

    @RequestMapping("/info/list")
    public void list(Integer pageNo, Integer pageSize,
                     HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<ApiInfo> list = (List<ApiInfo>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/info/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        ApiInfo bean = null;
        if (id != null) {
            if (id.equals(0)) {
                bean = new ApiInfo();
            } else {
                bean = manager.findById(id);
            }
            if (bean == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean.init();
                body = bean.convertToJson().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/info/save")
    public void save(ApiInfo bean, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(),
                bean.getUrl(), bean.getCode());
        if (!errors.hasErrors()) {
            bean.init();
            bean = manager.save(bean);
            log.info("save ApiInfo id={}", bean.getId());
            body = "{\"id\":\"" + bean.getId() + "\"}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/info/update")
    public void update(ApiInfo bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(),
                bean.getUrl(), bean.getCode());
        if (!errors.hasErrors()) {
            ApiInfo info = manager.findById(bean.getId());
            if (info == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean = manager.update(bean);
                body = "{\"id\":\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/info/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
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
                    ApiInfo[] beans = manager.deleteByIds(idArr);
                    for (ApiInfo bean : beans) {
                        log.info("delete ApiInfo id={}", bean.getId());
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
        if (idArr != null) {
            for (int i = 0; i < idArr.length; i++) {
                vldExist(idArr[i], errors);
                if (errors.hasErrors()) {
                    return errors;
                }
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        ApiInfo entity = manager.findById(id);
        if (errors.ifNotExist(entity, ApiInfo.class, id, false)) {
            return true;
        }
        return false;
    }


    @Autowired
    private ApiInfoMng manager;
}
