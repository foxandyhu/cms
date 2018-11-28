package com.bfly.cms.member.action;

import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员管理 Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 17:23
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsMemberApiAct {
    private static final Logger log = LoggerFactory
            .getLogger(CmsMemberApiAct.class);

    /**
     * 会员分页列表信息
     *
     * @param queryUsername
     * @param queryEmail
     * @param queryGroupId
     * @param queryStatus
     * @param pageNo
     * @param pageSize
     * @param https
     * @param request
     * @param response
     */
    @RequestMapping("/member/list")
    public void list(String queryUsername, String queryEmail, Integer queryGroupId,
                     Integer queryStatus, Integer pageNo, Integer pageSize, Integer https,
                     HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        CmsSite site = CmsUtils.getSite(request);
        Pagination page = manager.getPage(queryUsername, queryEmail, null, queryGroupId, queryStatus,
                false, null, null, null, null, null, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        JSONArray jsonArray = new JSONArray();
        List<CmsUser> list = (List<CmsUser>) page.getList();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(site, https, null));
            }
        }
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 会员详情
     *
     * @param id
     * @param https
     * @param request
     * @param response
     */
    @RequestMapping("/member/get")
    public void get(Integer id, Integer https, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsSite site = CmsUtils.getSite(request);
        if (https == null) {
            https = Constants.URL_HTTP;
        }
        CmsUser bean = null;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsUser();
            } else {
                bean = manager.findById(id);
            }
            if (bean != null) {
                bean.init();
                body = bean.convertToJson(site, https, null).toString();
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
     * 会员添加
     *
     * @param bean
     * @param ext
     * @param username
     * @param email
     * @param password
     * @param groupId
     * @param grain
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/member/save")
    public void save(CmsUser bean, CmsUserExt ext, String username,
                     String email, String password, Integer groupId, Integer grain,
                     HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, username, password);
        if (!errors.hasErrors()) {
            String ip = RequestUtils.getIpAddr(request);
            Map<String, String> attrs = RequestUtils.getRequestMap(request, "attr_");
            CmsUser user = manager.findByUsername(username);
            if (user != null) {
                message = Constants.API_MESSAGE_USERNAME_EXIST;
                code = ResponseCode.API_CODE_USERNAME_EXIST;
            } else {
                bean = manager.registerMember(username, email, password, ip, groupId, grain, false, ext, attrs);
                log.info("save CmsMember id={}", bean.getId());
                cmsLogMng.operating(request, "cmsMember.log.save", "id=" + bean.getId()
                        + ";username=" + bean.getUsername());
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 会员修改
     *
     * @param id
     * @param email
     * @param password
     * @param disabled
     * @param ext
     * @param groupId
     * @param grain
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/member/update")
    public void update(Integer id, String email, String password,
                       Boolean disabled, CmsUserExt ext, Integer groupId, Integer grain,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id, groupId, disabled);
        if (!errors.hasErrors()) {
            CmsUser user = manager.findById(id);
            if (user == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                Map<String, String> attrs = RequestUtils.getRequestMap(request, "attr_");
                CmsUser bean = manager.updateMember(id, email, password, disabled, ext, groupId, grain, attrs);
                log.info("update CmsMember id={}.", bean.getId());
                cmsLogMng.operating(request, "cmsMember.log.update", "id="
                        + bean.getId() + ";username=" + bean.getUsername());
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 会员删除
     *
     * @param ids
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/member/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            errors = validateExit(errors, idArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                try {
                    CmsUser[] beans = manager.deleteByIds(idArr);
                    for (CmsUser bean : beans) {
                        Map<String, String> paramsValues = new HashMap<>(5);
                        paramsValues.put("username", bean.getUsername());
                        paramsValues.put("admin", "false");
                        log.info("delete CmsMember id={}", bean.getId());
                        cmsLogMng.operating(request, "cmsMember.log.delete", "id="
                                + bean.getId() + ";username=" + bean.getUsername());
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

    /**
     * 会员审核
     *
     * @param ids
     * @param request
     * @param response
     */
    @SignValidate
    @RequestMapping("/member/check")
    public void check(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            errors = validateExit(errors, idArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                for (Integer id : idArr) {
                    CmsUser user = manager.findById(id);
                    user.setStatu(CmsUser.USER_STATU_CHECKED);
                    manager.updateUser(user);
                    log.info("check CmsMember id={}", user.getId());
                    cmsLogMng.operating(request, "cmsMember.log.delete", "id="
                            + user.getId() + ";username=" + user.getUsername());
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateExit(WebErrors errors, Integer[] idArr) {
        if (idArr != null) {
            for (int i = 0; i < idArr.length; i++) {
                CmsUser user = manager.findById(idArr[i]);
                if (user == null) {
                    errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                    return errors;
                }
            }
        }
        return errors;
    }

    @RequestMapping("/member/check_name")
    public void checkByUserName(String username, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        boolean result = false;
        errors = ApiValidate.validateRequiredParams(request, errors, username);
        if (!errors.hasErrors()) {
            CmsUser user = manager.findByUsername(username);
            if (user == null) {
                result = true;
            }
            body = "{\"result\":" + result + "}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsUserMng manager;
}
