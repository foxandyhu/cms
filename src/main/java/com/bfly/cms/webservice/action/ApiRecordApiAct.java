package com.bfly.cms.webservice.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.webservice.service.ApiRecordMng;
import com.bfly.cms.webservice.entity.ApiRecord;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
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
 * API调用记录Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 22:13
 */
@Controller
@RequestMapping(value = "/api/admin")
public class ApiRecordApiAct {
    private static final Logger log = LoggerFactory.getLogger(ApiRecordApiAct.class);

    /**
     * API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:13
     */
    @RequestMapping("/apiRecord/list")
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
        List<ApiRecord> list = (List<ApiRecord>) page.getList();
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
     * 删除API接口记录
     * @author andy_hulibo@163.com
     * @date 2018/11/24 22:13
     */
    @SignValidate
    @RequestMapping("/apiRecord/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Long[] idArr = StrUtils.getLongs(ids);
            errors = validateDelete(errors, idArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                try {
                    ApiRecord[] beans = manager.deleteByIds(idArr);
                    for (ApiRecord bean : beans) {
                        log.info("delete ApiRecord id={}", bean.getId());
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

    private WebErrors validateDelete(WebErrors errors, Long[] idArr) {
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

    private boolean vldExist(Long id, WebErrors errors) {
        String idStr="id";
        if (errors.ifNull(id, idStr, false)) {
            return true;
        }
        ApiRecord entity = manager.findById(id);
        return errors.ifNotExist(entity, ApiRecord.class, id, false);
    }

    @Autowired
    private ApiRecordMng manager;
}
