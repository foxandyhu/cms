package com.bfly.admin.comment.action;

import com.bfly.cms.comment.entity.CmsGuestbookCtg;
import com.bfly.cms.comment.service.CmsGuestbookCtgMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
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
 * 留言类型管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 16:40
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsGuestbookCtgApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(CmsGuestbookCtgApiAct.class);

    /**
     * 留言类型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 16:41
     */
    @RequestMapping("/guestbook_ctg/list")
    public void list(HttpServletResponse response, HttpServletRequest request) {
        List<CmsGuestbookCtg> list = manager.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/guestbook_ctg/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsGuestbookCtg bean;
        if (id.equals(0)) {
            bean = new CmsGuestbookCtg();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        String body = bean.convertToJson().toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook_ctg/save")
    public void save(CmsGuestbookCtg bean, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        bean = manager.save(bean);
        log.info("save CmsGuestbookCtg id={}", bean.getId());
        cmsLogMng.operating(request, "cmsGuestbookCtg.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook_ctg/update")
    public void update(CmsGuestbookCtg bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean = manager.update(bean);
        log.info("update CmsGuestbookCtg id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsGuestbookCtg.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/guestbook_ctg/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelete(idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsGuestbookCtg[] beans = manager.deleteByIds(idArr);
        for (CmsGuestbookCtg bean : beans) {
            log.info("delete CmsGuestbookCtg id={}", bean.getId());
            cmsLogMng.operating(request, "cmsGuestbookCtg.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
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


    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsGuestbookCtg entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsGuestbookCtg.class, id, false);
    }

    @Autowired
    private CmsLogMng cmsLogMng;

    @Autowired
    private CmsGuestbookCtgMng manager;
}
