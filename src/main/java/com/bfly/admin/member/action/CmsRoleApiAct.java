package com.bfly.admin.member.action;

import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsAdmin;
import com.bfly.cms.user.entity.CmsRole;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsRoleMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.cms.webservice.service.ApiAccountMng;
import com.bfly.cms.webservice.service.ApiUserLoginMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.security.CmsAuthorizingRealm;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色管理 Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 17:30
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsRoleApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsRoleApiAct.class);

    @RequestMapping(value = "/user/getPerms")
    public void getUserPerms(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        CmsAdmin admin = getAdmin();
        if (admin != null) {
            JSONObject json = new JSONObject();
            json.put("perms", admin.getPermStr());
            json.put("isMasterSite", true);
            body = json.toString();
            message = Constants.API_MESSAGE_SUCCESS;
        } else {
            //用户不存在
            message = Constants.API_MESSAGE_USER_NOT_LOGIN;
            code = ResponseCode.API_CODE_USER_NOT_LOGIN;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 角色列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 17:30
     */
    @RequestMapping("/role/list")
    public void list(Integer level, HttpServletRequest request, HttpServletResponse response) {
        List<CmsRole> list = manager.getList(level);
        //获取当前登录的用户
        CmsAdmin admin = getAdmin();
        //判断是否为超级管理员↓
        boolean isSuper = false;
        for (CmsRole cmsRole : admin.getRoles()) {
            if (cmsRole.getAll()) {
                isSuper = true;
            }
        }
        JSONArray jsonArray = new JSONArray();
        if (isSuper) {
            //↓直接获取所有角色
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, list.get(i).convertToJson());
                }
            }
        } else {
            //→获取自己对应的角色列表
            if (list != null && list.size() > 0) {
                for (int i = 0, j = 0; i < list.size(); i++) {
                    if (admin.getRoles().contains(list.get(i))) {
                        jsonArray.put(j, list.get(i).convertToJson());
                        j++;
                    }
                }
            }
        }

        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/role/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsRole bean = null;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsRole();
            } else {
                bean = manager.findById(id);
            }
            if (bean != null) {
                bean.init();
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

    @SignValidate
    @RequestMapping("/role/save")
    public void save(CmsRole bean, String perms,
                     HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        CmsAdmin admin =getAdmin();
        errors = ApiValidate.validateRequiredParams(request, errors,
                bean.getName(), bean.getPriority(), bean.getLevel(),
                bean.getAll());
        CmsRole currUserTopRole = admin.getTopRole();
        Integer currUserTopRoleLevel = 10;
        if (currUserTopRole != null) {
            currUserTopRoleLevel = currUserTopRole.getLevel();
        }
        if (bean.getLevel() > currUserTopRoleLevel) {
            message = Constants.API_MESSAGE_ROLE_LEVEL_ERROR;
            code = ResponseCode.API_CODE_ROLE_LEVEL_ERROR;
        } else {
            if (!errors.hasErrors()) {
                bean.init();
                bean = manager.save(bean, splitPerms(perms));
                log.info("save CmsRole id={}", bean.getId());
                cmsLogMng.operating(request, "cmsRole.log.save", "id=" + bean.getId()
                        + ";name=" + bean.getName());
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/role/update")
    public void update(CmsRole bean, String perms, boolean all,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(),
                bean.getName(), bean.getPriority(), bean.getLevel(),
                bean.getAll());
        CmsAdmin admin = getAdmin();
        CmsRole currUserTopRole = admin.getTopRole();
        Integer currUserTopRoleLevel = 10;
        if (currUserTopRole != null) {
            currUserTopRoleLevel = currUserTopRole.getLevel();
        }
        if (bean.getLevel() > currUserTopRoleLevel) {
            message = Constants.API_MESSAGE_ROLE_LEVEL_ERROR;
            code = ResponseCode.API_CODE_ROLE_LEVEL_ERROR;
        } else {
            if (!errors.hasErrors()) {
                bean = manager.update(bean, splitPerms(perms));
                String[] split = null;
                if (perms != null) {
                    split = perms.split(",");
                }
                if (hasChangePermission(all, split, bean)) {
                    Set<CmsAdmin> admins = bean.getAdmins();
                    for (CmsAdmin cmsAdmin : admins) {
                        authorizingRealm.removeUserAuthorizationInfoCache(cmsAdmin.getUsername());
                    }
                }
                log.info("update CmsRole id={}.", bean.getId());
                cmsLogMng.operating(request, "cmsRole.log.update", "id=" + bean.getId()
                        + ";name=" + bean.getName());
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }

        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/role/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            try {
                Integer[] idArray = StrUtils.getInts(ids);
                CmsRole[] beans = manager.deleteByIds(idArray);
                for (CmsRole bean : beans) {
                    log.info("delete CmsRole id={}", bean.getId());
                    cmsLogMng.operating(request, "cmsRole.log.delete", "id="
                            + bean.getId() + ";name=" + bean.getName());
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

    @SuppressWarnings("unchecked")
    @RequestMapping("/role/member_list")
    public void memberList(Integer https, Integer roleId, Integer pageNo, Integer pageSize,
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
        Pagination pagination = userMng.getAdminsByRoleId(roleId, pageNo, pageSize);
        int totalCount = pagination.getTotalCount();
        List<CmsUser> list = (List<CmsUser>) pagination.getList();
        JSONArray jsonArray = new JSONArray();
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

    @SignValidate
    @RequestMapping("/role/member_delete")
    public void memberDelete(Integer roleId, String userIds, HttpServletRequest request,
                             HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, roleId, userIds);
        if (!errors.hasErrors()) {
            CmsRole role = manager.findById(roleId);
            if (role != null) {
                Integer[] idArray = StrUtils.getInts(userIds);
                manager.deleteMembers(role, idArray);
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

    private boolean hasChangePermission(boolean all, String[] perms, CmsRole bean) {
        CmsRole role = manager.findById(bean.getId());
        if (!role.getAll().equals(all)) {
            return true;
        }
        if (!bean.getPerms().toArray().equals(perms)) {
            return true;
        }
        return false;
    }

    private Set<String> splitPerms(String perms) {
        Set<String> set = new HashSet<>();
        if (perms != null) {
            for (String p : StringUtils.split(perms, ',')) {
                if (!StringUtils.isBlank(p)) {
//					if (p.startsWith("/api/admin")) {
//						p=p.substring(10);
//					}
                    set.add(p);
                }
            }
        }
        return set;
    }

    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private CmsAuthorizingRealm authorizingRealm;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsRoleMng manager;
}
