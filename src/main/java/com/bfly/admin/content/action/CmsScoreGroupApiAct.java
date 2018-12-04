package com.bfly.admin.content.action;

import com.bfly.cms.content.entity.CmsScoreGroup;
import com.bfly.cms.content.service.CmsScoreGroupMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
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
 * 评分组管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:00
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsScoreGroupApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsScoreGroupApiAct.class);

    @RequestMapping("/scoregroup/list")
    public void list(Integer pageNo, Integer pageSize, HttpServletResponse response, HttpServletRequest request) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pagination page = manager.getPage(pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsScoreGroup> list = (List<CmsScoreGroup>) page.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }

        String body = jsonArray.toString() + ",\"totalCount\":" + totalCount;
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping("/scoregroup/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsScoreGroup bean;
        if (id.equals(0)) {
            bean = new CmsScoreGroup();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        String body = bean.convertToJson().toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/scoregroup/save")
    public void save(CmsScoreGroup bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        bean.init();
        bean = manager.save(bean);
        log.info("save CmsScoreGroup id={}", bean.getId());
        String body = "{\"id\":\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/scoregroup/update")
    public void update(CmsScoreGroup bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        errors = validateExist(errors, bean.getId());
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        bean = manager.update(bean);
        log.info("update CmsScoreGroup id={}.", bean.getId());
        String body = "{\"id\":\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/scoregroup/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelete(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        try {
            CmsScoreGroup[] beans = manager.deleteByIds(idArr);
            for (CmsScoreGroup bean : beans) {
                log.info("delete CmsScoreGroup id={}", bean.getId());
            }
        } catch (Exception e) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_NOT_FOUND);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateDelete(WebErrors errors, Integer[] idArr) {
        if (errors.ifEmpty(idArr, "ids", false)) {
            return errors;
        }
        for (Integer id : idArr) {
            vldExist(id, errors);
        }
        return errors;
    }

    private WebErrors validateExist(WebErrors errors, Integer id) {
        CmsScoreGroup group = manager.findById(id);
        if (group == null) {
            errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id", false)) {
            return true;
        }
        CmsScoreGroup entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsScoreGroup.class, id, false);
    }

    @Autowired
    private CmsScoreGroupMng manager;
}
