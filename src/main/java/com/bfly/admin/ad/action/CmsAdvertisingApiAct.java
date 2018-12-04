package com.bfly.admin.ad.action;

import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.cms.ad.service.CmsAdvertisingMng;
import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.SignValidate;
import com.bfly.core.base.action.BaseAdminController;
import com.bfly.core.exception.ApiException;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 广告管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:54
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsAdvertisingApiAct extends BaseAdminController {

    private static final Logger log = LoggerFactory.getLogger(CmsAdvertisingApiAct.class);

    @RequestMapping("/advertising/list")
    public void list(Integer queryAdspaceId, Boolean queryEnabled, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(queryAdspaceId, queryEnabled, pageNo, pageSize);
        List<CmsAdvertising> list = (List<CmsAdvertising>) page.getList();
        int totalCount = page.getTotalCount();
        JSONArray jsonArray = new JSONArray();
        if (list != null) {
            for (CmsAdvertising ad : list) {
                jsonArray.put(ad.convertToJson());
            }
        }
        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/advertising/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        CmsAdvertising bean;
        if (id == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        if (id.equals(0)) {
            bean = new CmsAdvertising();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        bean.init();
        String body = bean.convertToJson().toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/advertising/save")
    public void save(CmsAdvertising bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(), bean.getCategory(), adspaceId);
        Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        validAd(bean, attr);
        if (cmsAdvertisingSpaceMng.findById(adspaceId) == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        // 去除为空串的属性
        Set<String> toRemove = new HashSet<>();
        for (Entry<String, String> entry : attr.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                toRemove.add(entry.getKey());
            }
        }
        for (String key : toRemove) {
            attr.remove(key);
        }
        bean = manager.save(bean, adspaceId, attr);
        log.info("save CmsAdvertising id={}", bean.getId());
        cmsLogMng.operating(request, "cmsAdvertising.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/advertising/update")
    public void update(CmsAdvertising bean, Integer adspaceId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getCategory());
        Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        validAd(bean, attr);
        CmsAdvertising advertising = manager.findById(bean.getId());
        if (advertising == null && cmsAdvertisingSpaceMng.findById(adspaceId) == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        // 去除为空串的属性
        Set<String> toRemove = new HashSet<>();
        for (Entry<String, String> entry : attr.entrySet()) {
            if (StringUtils.isBlank(entry.getValue())) {
                toRemove.add(entry.getKey());
            }
        }
        for (String key : toRemove) {
            attr.remove(key);
        }
        bean = manager.update(bean, adspaceId, attr);
        log.info("update CmsAdvertising id={}.", bean.getId());
        cmsLogMng.operating(request, "cmsAdvertising.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private void validAd(CmsAdvertising ad, Map<String, String> attr) {
        WebErrors errors = WebErrors.create(getRequest());
        switch (ad.getCategory()) {
            case "image":
                errors = ApiValidate.validateRequiredParams(getRequest(), errors, attr.get("image_link"), attr.get("image_url"));
                break;
            case "flash":
                errors = ApiValidate.validateRequiredParams(getRequest(), errors, attr.get("flash_url"));
                break;
            case "text":
                errors = ApiValidate.validateRequiredParams(getRequest(), errors, attr.get("text_link"), attr.get("text_title"));
                break;
            case "code":
                errors = ApiValidate.validateRequiredParams(getRequest(), errors, ad.getCode());
                break;
            default:
                throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
    }

    @SignValidate
    @RequestMapping("/advertising/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelete(errors, idArr, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsAdvertising[] beans = manager.deleteByIds(idArr);
        for (CmsAdvertising bean : beans) {
            log.info("delete CmsAdvertising id={}", bean.getId());
            cmsLogMng.operating(request, "cmsAdvertising.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateDelete(WebErrors errors, Integer[] idArr, HttpServletRequest request) {
        if (idArr != null) {
            for (int i = 0; i < idArr.length; i++) {
                vldExist(idArr[i], errors);
            }
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsAdvertising entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsAdvertising.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsAdvertisingMng manager;
}
