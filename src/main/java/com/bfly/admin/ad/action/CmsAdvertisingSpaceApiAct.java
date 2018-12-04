package com.bfly.admin.ad.action;

import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
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
 * 广告位管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:59
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsAdvertisingSpaceApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(CmsAdvertisingSpaceApiAct.class);

    @RequestMapping("/advertising/space_list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<CmsAdvertisingSpace> list = manager.getList();
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

    @RequestMapping("/advertising/space_get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        CmsAdvertisingSpace bean;
        if (id == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (id.equals(0)) {
            bean = new CmsAdvertisingSpace();
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
    @RequestMapping("/advertising/space_save")
    public void save(CmsAdvertisingSpace bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        bean = manager.save(bean);
        log.info("save CmsAdvertisingSpace id={}", bean.getId());
        cmsLogMng.operating(request, "cmsAdvertisingSpace.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/advertising/space_update")
    public void update(CmsAdvertisingSpace bean, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getEnabled());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsAdvertisingSpace space = manager.findById(bean.getId());
        if (space == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean = manager.update(bean);
        log.info("update CmsAdvertisingSpace id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsAdvertisingSpace.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/advertising/space_delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelete(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            CmsAdvertisingSpace[] beans = manager.deleteByIds(idArr);
            for (CmsAdvertisingSpace bean : beans) {
                log.info("delete CmsAdvertisingSpace id={}", bean.getId());
                cmsLogMng.operating(request, "cmsAdvertisingSpace.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
            }
        } catch (Exception e) {
            throw new ApiException("删除出错", ResponseCode.API_CODE_DELETE_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateDelete(WebErrors errors, Integer[] idArr) {
        if (idArr != null) {
            for (Integer id : idArr) {
                vldExist(id, errors);
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsAdvertisingSpace entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsAdvertisingSpace.class, id, false);
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsAdvertisingSpaceMng manager;
}
