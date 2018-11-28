package com.bfly.cms.dictionary.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.dictionary.entity.CmsDictionary;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.dictionary.service.CmsDictionaryMng;
import com.bfly.cms.logs.service.CmsLogMng;
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
import java.util.List;

/**
 * 数据字典Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:30
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsDictionaryApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsDictionaryApiAct.class);

    /**
     * 数据字典列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:31
     */
    @RequestMapping("/dictionary/list")
    public void list(String queryType, Integer pageNo, Integer pageSize,
                     HttpServletResponse response, HttpServletRequest request) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(queryType, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsDictionary> list = (List<CmsDictionary>) page.getList();
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
     * 获得数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:31
     */
    @RequestMapping("/dictionary/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsDictionary bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsDictionary();
            } else {
                bean = manager.findById(id);
            }
            if (bean != null) {
                body = bean.convertToJson().toString();
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
     * 新增数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:32
     */
    @SignValidate
    @RequestMapping("/dictionary/save")
    public void save(CmsDictionary bean, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(), bean.getValue());
        if (!errors.hasErrors()) {
            errors = validateValue(bean, errors);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                bean = manager.save(bean);
                log.info("save CmsDictionary id={}", bean.getId());
                cmsLogMng.operating(request, "CmsDictionary.log.save", "id="
                        + bean.getId() + ";name=" + bean.getName());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:32
     */
    @SignValidate
    @RequestMapping("/dictionary/update")
    public void update(CmsDictionary bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(),
                bean.getName(), bean.getValue());
        if (!errors.hasErrors()) {
            CmsDictionary dictionary = manager.findById(bean.getId());
            if (dictionary == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                errors = validateValue(bean, errors);
                if (errors.hasErrors()) {
                    message = errors.getErrors().get(0);
                    code = ResponseCode.API_CODE_PARAM_ERROR;
                } else {
                    bean = manager.update(bean);
                    log.info("update CmsDictionary id={}.", bean.getId());
                    cmsLogMng.operating(request, "CmsDictionary.log.update", "id="
                            + bean.getId() + ";name=" + bean.getName());
                    body = "{\"id\":" + bean.getId() + "}";
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:32
     */
    @SignValidate
    @RequestMapping("/dictionary/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            errors = validateExist(errors, idArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                CmsDictionary[] beans = manager.deleteByIds(idArr);
                for (CmsDictionary bean : beans) {
                    log.info("delete CmsDictionary id={}", bean.getId());
                    cmsLogMng.operating(request, "CmsDictionary.log.delete", "id="
                            + bean.getId() + ";name=" + bean.getName());
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 检查是否有重复的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:33
     */
    @RequestMapping("/dictionary/check_value")
    public void checkValue(String value, String type, HttpServletRequest request, HttpServletResponse response) {
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, value, type);
        boolean result = false;
        if (!errors.hasErrors()) {
            result = manager.dicDeplicateValue(value, type);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        String body = "{\"result\":" + result + "}";
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获得同类型的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:34
     */
    @RequestMapping("/dictionary/type")
    public void listByType(String type, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        if (StringUtils.isNotBlank(type)) {
            List<CmsDictionary> list = manager.getList(type);
            JSONArray jsonArray = new JSONArray();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, list.get(i).convertToJson());
                }
            }
            body = jsonArray.toString();
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateExist(WebErrors errors, Integer[] idArr) {
        if (idArr != null) {
            for (Integer id:idArr) {
                vldExist(id, errors);
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        CmsDictionary entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsDictionary.class, id, false);
    }


    private WebErrors validateValue(CmsDictionary bean, WebErrors errors) {
        if (manager.dicDeplicateValue(bean.getValue(), bean.getType())) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
            return errors;
        }
        return errors;
    }

    @Autowired
    private CmsDictionaryMng manager;
    @Autowired
    private CmsLogMng cmsLogMng;
}
