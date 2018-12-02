package com.bfly.admin.comment.action;

import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
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
 * 留言类型管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:40
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsGuestbookCtgApiAct {

    private static final Logger log = LoggerFactory.getLogger(CmsGuestbookCtgApiAct.class);

    /**
     * 留言类型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:41
     */
    @RequestMapping("/guestbook_ctg/list")
    public void list(HttpServletResponse response, HttpServletRequest request) {
        List<CmsGuestbookCtg> list = manager.getList(CmsUtils.getSiteId(request));
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
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

    @RequestMapping("/guestbook_ctg/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsGuestbookCtg bean = null;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsGuestbookCtg();
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
    @RequestMapping("/guestbook_ctg/save")
    public void save(CmsGuestbookCtg bean, HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName());
        if (!errors.hasErrors()) {
            bean.init();
            bean.setSite(CmsUtils.getSite(request));
            bean = manager.save(bean);
            log.info("save CmsGuestbookCtg id={}", bean.getId());
            cmsLogMng.operating(request, "cmsGuestbookCtg.log.save", "id="
                    + bean.getId() + ";name=" + bean.getName());
            body = "{\"id\":" + bean.getId() + "}";
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook_ctg/update")
    public void update(CmsGuestbookCtg bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName());
        if (!errors.hasErrors()) {
            errors = validateUpdate(bean.getId(), request);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                bean = manager.update(bean);
                log.info("update CmsGuestbookCtg id={}.", bean.getId());
                cmsLogMng.operating(request, "cmsGuestbookCtg.log.update", "id="
                        + bean.getId() + ";name=" + bean.getName());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook_ctg/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
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
                CmsGuestbookCtg[] beans = manager.deleteByIds(idArr);
                for (CmsGuestbookCtg bean : beans) {
                    log.info("delete CmsGuestbookCtg id={}", bean.getId());
                    cmsLogMng.operating(request, "cmsGuestbookCtg.log.delete", "id="
                            + bean.getId() + ";name=" + bean.getName());
                }
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = CmsUtils.getSite(request);
        if (errors.ifEmpty(ids, "ids", false)) {
            return errors;
        }
        for (Integer id : ids) {
            vldExist(id, site.getId(), errors);
        }
        return errors;
    }


    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        CmsSite site = CmsUtils.getSite(request);
        if (vldExist(id, site.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsGuestbookCtg entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsGuestbookCtg.class, id, false)) {
            return true;
        }
        if (!entity.getSite().getId().equals(siteId)) {
            errors.addErrorString("error.notInSite");
            return true;
        }
        return false;
    }

    @Autowired
    private CmsLogMng cmsLogMng;

    @Autowired
    private CmsGuestbookCtgMng manager;
}
