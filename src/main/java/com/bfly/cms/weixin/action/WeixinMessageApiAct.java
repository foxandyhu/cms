package com.bfly.cms.weixin.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.weixin.entity.WeixinMenu;
import com.bfly.cms.weixin.service.WeixinMessage;
import com.bfly.cms.weixin.service.WeixinMessageMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
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
 * 微信公众号自定义回复
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 17:05
 */
@Controller
@RequestMapping(value = "/api/admin")
public class WeixinMessageApiAct {
    private static final Logger log = LoggerFactory.getLogger(WeixinMessageApiAct.class);

    @RequestMapping("/weixinMessage/list")
    public void list(Integer pageNo, Integer pageSize, HttpServletRequest request,
                     HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(CmsUtils.getSiteId(request), pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<WeixinMessage> list = (List<WeixinMessage>) page.getList();
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

    /**
     * 微信公众号默认回复
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 17:08
     */
    @RequestMapping("/weixinMessage/default_get")
    public void defGet(HttpServletRequest request, HttpServletResponse response) {
        WeixinMessage defaultMsg = manager.getWelcome(CmsUtils.getSiteId(request));
        String body = defaultMsg.convertToJson().toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/weixinMessage/get")
    public void get(Integer id, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WeixinMessage bean = null;
        if (id != null) {
            if (id.equals(0)) {
                bean = new WeixinMessage();
            } else {
                bean = manager.findById(id);
            }
            if (bean == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_FILE_NOT_FOUNT;
            } else {
                body = bean.convertToJson().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/weixinMessage/save")
    public void save(WeixinMessage bean, Boolean welcome, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getTitle(), welcome);
        if (!errors.hasErrors()) {
            bean.setSite(CmsUtils.getSite(request));
            bean.setWelcome(welcome);
            if (!welcome) {
                bean.setType(0);
            }
            bean = manager.save(bean);
            body = "{\"id\":" + "\"" + bean.getId() + "\"}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/weixinMessage/update")
    public void update(WeixinMessage bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getWelcome());
        if (!errors.hasErrors()) {
            errors = validateUpdate(errors, bean, request);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                bean = manager.update(bean);
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/weixinMessage/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            errors = validateDelete(idArr, request);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                try {
                    WeixinMessage[] beans = manager.deleteByIds(idArr);
                    for (WeixinMessage bean : beans) {
                        log.info("delete WeixinMessage id={}", bean.getId());
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

    private WebErrors validateUpdate(WebErrors errors, WeixinMessage bean, HttpServletRequest request) {
        if (bean.getId() != null) {
            vldExist(bean.getId(), errors);
            if (errors.hasErrors()) {
                return errors;
            }
        }
        if (bean.getWelcome()) {//判断是否为默认回复
            if (bean.getType().equals(0)) {//默认回复需要根据消息类型，进行相应的判断
                errors = ApiValidate.validateRequiredParams(request, errors, bean.getTitle());
                return errors;
            } else if (bean.getType().equals(1)) {
                errors = ApiValidate.validateRequiredParams(request, errors, bean.getContent());
                return errors;
            } else if (bean.getType().equals(2)) {
                errors = ApiValidate.validateRequiredParams(request, errors, bean.getContent());
                return errors;
            } else {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        } else {
            errors = ApiValidate.validateRequiredParams(request, errors, bean.getTitle());
            return errors;
        }
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifEmpty(ids, "ids", false)) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        WeixinMessage entity = manager.findById(id);
        if (errors.ifNotExist(entity, WeixinMenu.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private WeixinMessageMng manager;
}
