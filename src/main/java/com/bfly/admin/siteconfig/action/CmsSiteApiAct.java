package com.bfly.admin.siteconfig.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.siteconfig.entity.CmsOss;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.service.CmsOssMng;
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
import java.io.IOException;
import java.util.List;

/**
 * 站点管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 11:56
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsSiteApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsSiteApiAct.class);

    /**
     * 获得所有站点列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:56
     */
    @RequestMapping("/site/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<CmsSite> list;
        JSONArray jsonArray = new JSONArray();
        list = manager.getList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 查看站点基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:57
     */
    @RequestMapping("/site/get")
    public void get(Integer id, Integer root, HttpServletRequest request, HttpServletResponse response) {
        CmsSite site;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        if (!errors.hasErrors()) {
            if (id.equals(0)) {
                site = new CmsSite();
                site.init();
            } else {
                site = manager.findById(id);
                ;
            }
            if (site != null) {
                JSONObject json = site.convertToJson();
                json.put("root", root);
                body = json.toString();
                code = ResponseCode.API_CODE_CALL_SUCCESS;
                message = Constants.API_MESSAGE_SUCCESS;
            } else {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 新增新站点
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:58
     */
    @SignValidate
    @RequestMapping("/site/save")
    public void save(CmsSite bean, Integer ossId, Integer syncPageFtpId, Integer uploadFtpId, Integer root,
                     HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(),
                bean.getDomain(), bean.getPath());
        if (!errors.hasErrors()) {
            CmsSite site = CmsUtils.getSite(request);
            CmsUser user = CmsUtils.getUser(request);
            if (ossId != null) {
                CmsOss oss = ossMng.findById(ossId);
                if (oss == null) {
                    errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                    code = ResponseCode.API_CODE_NOT_FOUND;
                } else {
                    bean.setUploadOss(oss);
                }
            }
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
            } else {
                // 加上config信息
                bean.setConfig(configMng.get());
                bean.init();
                CmsSite checkDomain = manager.findByDomain(bean.getDomain());
                if (checkDomain != null) {
                    errors.addErrorString(Constants.API_MESSAGE_DOMAIN_EXIST);
                }
                if (errors.hasErrors()) {
                    message = errors.getErrors().get(0);
                    if (message.equals(Constants.API_MESSAGE_DOMAIN_EXIST)) {
                        code = ResponseCode.API_CODE_DOMAIN_EXIST;
                    } else if (message.equals(Constants.API_MESSAGE_ACCESSPATH_EXIST)) {
                        code = ResponseCode.API_CODE_ACCESSPATH_EXIST;
                    }
                } else {
                    bean = manager.save(site, user, bean, uploadFtpId, syncPageFtpId);
                    log.info("save CmsSite id={}", bean.getId());
                    cmsLogMng.operating(request, "cmsSite.log.save", "id=" + bean.getId()
                            + ";name=" + bean.getName());
                    body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改更新站点
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:58
     */
    @SignValidate
    @RequestMapping("/site/update")
    public void update(CmsSite bean, Integer ossId, Integer uploadFtpId, Integer syncPageFtpId
            , HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(),
                bean.getDomain(), bean.getPath());
        if (!errors.hasErrors()) {
            CmsSite checkDomain = manager.findByDomain(bean.getDomain());
            if (checkDomain != null) {
                //若已存在，修改操作需要判断id是否相同
                if (!checkDomain.getId().equals(bean.getId())) {
                    errors.addErrorString(Constants.API_MESSAGE_DOMAIN_EXIST);
                }
            }
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                if (message.equals(Constants.API_MESSAGE_DOMAIN_EXIST)) {
                    code = ResponseCode.API_CODE_DOMAIN_EXIST;
                } else if (message.equals(Constants.API_MESSAGE_ACCESSPATH_EXIST)) {
                    code = ResponseCode.API_CODE_ACCESSPATH_EXIST;
                }
            } else {
                if (ossId != null) {
                    CmsOss oss = ossMng.findById(ossId);
                    if (oss == null) {
                        errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                        code = ResponseCode.API_CODE_NOT_FOUND;
                    }
                }
                if (errors.hasErrors()) {
                    message = errors.getErrors().get(0);
                } else {
                    bean = manager.update(bean, uploadFtpId, syncPageFtpId, ossId);
                    log.info("update CmsSite id={}", bean.getId());
                    cmsLogMng.operating(request, "cmsSite.log.update", "id=" + bean.getId()
                            + ";name=" + bean.getName());
                    body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除站点
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 11:59
     */
    @SignValidate
    @RequestMapping("/site/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            try {
                Integer[] idArray = StrUtils.getInts(ids);
                CmsSite[] deleteByIds = manager.deleteByIds(idArray);
                for (int i = 0; i < deleteByIds.length; i++) {
                    log.info("delete CmsSite id={}", deleteByIds[i].getId());
                    cmsLogMng.operating(request, "cmsSite.log.delete", "id="
                            + deleteByIds[i].getId() + ";name=" + deleteByIds[i].getName());
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

    /**
     * 树形站点
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 12:07
     */
    @RequestMapping("/site/tree")
    public void getTree(HttpServletRequest request, HttpServletResponse response) {
        List<CmsSite> siteList;
        JSONArray jsonArray = new JSONArray();
        siteList = manager.getList();
        if (siteList != null) {
            for (int i = 0; i < siteList.size(); i++) {
                JSONObject json = getJson(siteList.get(i));
                jsonArray.put(i, json);
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 检查是否是主站
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 13:32
     */
    @RequestMapping("/site/check_master")
    public void checkMaster(HttpServletRequest request, HttpServletResponse response) {
        boolean result = true;
        JSONObject json = new JSONObject();
        json.put("result", result);
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        String body = json.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }


    /**
     * 检查域名
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 13:35
     */
    @RequestMapping("/site/check_domain")
    public void checkDomain(Integer siteId, String domain,
                            HttpServletResponse response, HttpServletRequest request) {
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        boolean result = false;
        JSONObject json = new JSONObject();
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, domain);
        if (!errors.hasErrors()) {
            CmsSite site = manager.findByDomain(domain);
            if (site == null) {
                result = true;
            } else {
                if (site.getId().equals(siteId)) {
                    result = true;
                }
            }
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        json.put("result", result);
        String body = json.toString();
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private JSONObject getJson(CmsSite site) {
        JSONObject json = new JSONObject();
        if (site.getId() != null) {
            json.put("id", site.getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(site.getName())) {
            json.put("name", site.getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(site.getShortName())) {
            json.put("shortName", site.getShortName());
        } else {
            json.put("shortName", "");
        }
        json.put("hasChild", false);
        json.put("parentId", "");
        return json;
    }

    @Autowired
    private CmsOssMng ossMng;
    @Autowired
    private CmsConfigMng configMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsSiteMng manager;
}
