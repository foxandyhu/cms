package com.bfly.cms.job.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.job.entity.CmsJobApply;
import com.bfly.cms.job.service.CmsJobApplyMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.DateUtils;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.job.entity.CmsUserResume;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
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
 * 职位申请管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:30
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsJobApplyApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsJobApplyApiAct.class);

    /**
     * 职位申请列表
     *
     * @param title
     * @param pageNo
     * @param pageSize
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/jobapply/list")
    public void list(String title, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(null, null, CmsUtils.getSiteId(request), true, title, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsJobApply> list = (List<CmsJobApply>) page.getList();
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
     * 简历查看
     *
     * @param userId
     * @param contentId
     * @param request
     * @param response
     */
    @RequestMapping("/jobapply/view")
    public void view(Integer userId, Integer contentId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, userId, contentId);
        if (!errors.hasErrors()) {
            errors = validateViewResume(errors, userId, contentId, request);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                CmsUser user = userMng.findById(userId);
                CmsUserResume resume = user.getUserResume();
                JSONObject json = new JSONObject();
                if (resume != null) {
                    json = resume.convertToJson();
                    json = createUserJson(json, user);
                    body = json.toString();
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 职位申请删除
     *
     * @param ids
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/jobapply/delete")
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
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                try {
                    CmsJobApply[] beans = manager.deleteByIds(idArr);
                    for (CmsJobApply bean : beans) {
                        log.info("delete CmsJobApply id={}", bean.getId());
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

    private JSONObject createUserJson(JSONObject json, CmsUser user) {
        if (StringUtils.isNotBlank(user.getRealname())) {
            json.put("realname", user.getRealname());
        } else {
            json.put("realname", "");
        }
        if (user.getGender() != null) {
            json.put("gender", user.getGender());
        } else {
            json.put("gender", "");
        }
        if (user.getBirthday() != null) {
            json.put("birthday", DateUtils.parseDateToDateStr(user.getBirthday()));
        } else {
            json.put("birthday", "");
        }
        if (StringUtils.isNotBlank(user.getIntro())) {
            json.put("intro", user.getIntro());
        } else {
            json.put("intro", "");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            json.put("phone", user.getPhone());
        } else {
            json.put("phone", "");
        }
        if (StringUtils.isNotBlank(user.getMobile())) {
            json.put("mobile", user.getMobile());
        } else {
            json.put("mobile", "");
        }
        return json;
    }

    private WebErrors validateDelete(WebErrors errors, Integer[] ids) {
        if (errors.ifEmpty(ids, "ids", true)) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, errors);
        }
        return errors;
    }

    private WebErrors validateViewResume(WebErrors errors, Integer userId, Integer contentId, HttpServletRequest request) {
        CmsSite site = CmsUtils.getSite(request);
        CmsUser u = userMng.findById(userId);
        if (u == null) {
            errors.addErrorString("error.notExist");
            return errors;
        }
        Content c = contentMng.findById(contentId);
        if (c == null) {
            errors.addErrorString("error.notExist");
            return errors;
        }
        if (!c.getSite().equals(site)) {
            errors.addErrorString("error.notInSite");
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsJobApply entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsJobApply.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsJobApplyMng manager;
    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private ContentMng contentMng;
}
