package com.bfly.admin.sms.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.sms.service.CmsSmsRecordMng;
import com.bfly.core.web.WebErrors;
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
 * 短信发送记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/25 16:34
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsSmsRecordApiAct {

    private static final Logger log = LoggerFactory.getLogger(CmsSmsRecordApiAct.class);

    /**
     * 短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:34
     */
    @RequestMapping("/smsRecord/list")
    public void list(Integer pageNo, Integer pageSize, Byte sms, String phone,
                     Integer validateType, String username, Date drawTimeBegin, Date drawTimeEnd,
                     HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(sms, pageNo, pageSize, phone, validateType, username, drawTimeBegin, drawTimeEnd);
        int totalCount = page.getTotalCount();
        List<CmsSmsRecord> list = (List<CmsSmsRecord>) page.getList();
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
     * 删除短信记录
     * @author andy_hulibo@163.com
     * @date 2018/11/25 16:34
     */
    @SignValidate
    @RequestMapping("/smsRecord/delete")
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
                    CmsSmsRecord[] beans = manager.deleteByIds(idArr);
                    for (CmsSmsRecord bean : beans) {
                        log.info("delete CmsSmsRecord id={}", bean.getId());
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
            for (Integer id:idArr) {
                vldExist(id, errors);
                if (errors.hasErrors()) {
                    return errors;
                }
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        String idStr="id";
        if (errors.ifNull(id, idStr, false)) {
            return true;
        }
        CmsSmsRecord entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsSmsRecord.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsSmsRecordMng manager;
}
