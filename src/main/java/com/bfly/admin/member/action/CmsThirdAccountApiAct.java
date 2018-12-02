package com.bfly.admin.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfly.core.web.WebErrors;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.user.entity.CmsThirdAccount;
import com.bfly.cms.user.service.CmsThirdAccountMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;

/**
 * 第三方平台账户绑定Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 17:36
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsThirdAccountApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsThirdAccountApiAct.class);

    /**
     * 账户绑定列表接口
     *
     * @param username
     * @param source
     * @param pageNo
     * @param pageSize
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/account/list")
    public void list(String username, String source, Integer pageNo, Integer pageSize,
                     HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(username, source, pageNo, pageSize);
        List<CmsThirdAccount> list = (List<CmsThirdAccount>) page.getList();
        int totalCount = page.getTotalCount();
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
     * 删除第三方账户
     *
     * @param ids
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/account/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
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
                    CmsThirdAccount[] beans = manager.deleteByIds(idArr);
                    for (CmsThirdAccount bean : beans) {
                        log.info("delete CmsThirdAccount id={}", bean.getId());
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
            }
        }
        return errors;
    }

    private boolean vldExist(Long id, WebErrors errors) {
        CmsThirdAccount entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsThirdAccount.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsThirdAccountMng manager;
}
