package com.bfly.admin.content.action;

import com.bfly.cms.content.entity.CmsScoreGroup;
import com.bfly.cms.content.entity.CmsScoreItem;
import com.bfly.cms.content.service.CmsScoreGroupMng;
import com.bfly.cms.content.service.CmsScoreItemMng;
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
 * 评分项Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:02
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsScoreItemApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsScoreItemApiAct.class);

    @RequestMapping("/scoreitem/list")
    public void list(Integer groupId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        WebErrors errors = WebErrors.create(request);
        errors = validateGroup(errors, groupId);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Pagination page = manager.getPage(groupId, pageNo, pageSize);
        int totalCount = page.getTotalCount();
        List<CmsScoreItem> list = (List<CmsScoreItem>) page.getList();
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

    @RequestMapping("/scoreitem/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsScoreItem bean;
        if (id.equals(0)) {
            bean = new CmsScoreItem();
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
    @RequestMapping("/scoreitem/save")
    public void save(CmsScoreItem bean, Integer groupId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getName(), bean.getScore());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        errors = validateGroup(errors, groupId);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsScoreGroup group = scoreGroupMng.findById(groupId);
        bean.init();
        bean.setGroup(group);
        bean = manager.save(bean);
        log.info("save CmsScoreItem id={}", bean.getId());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/scoreitem/update")
    public void update(CmsScoreItem bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getScore());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        errors = validateItem(errors, bean.getId());
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean = manager.update(bean);
        log.info("update CmsScoreItem id={}.", bean.getId());
        String body = "{\"id\":" + bean.getId() + "}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping("/scoreitem/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_ERROR);
        }
        Integer[] idArr = StrUtils.getInts(ids);
        errors = validateDelet(errors, idArr);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        try {
            CmsScoreItem[] beans = manager.deleteByIds(idArr);
            for (CmsScoreItem bean : beans) {
                log.info("delete CmsScoreItem id={}", bean.getId());
            }
        } catch (Exception e) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateDelet(WebErrors errors, Integer[] idArr) {
        if (idArr != null) {
            for (Integer id : idArr) {
                errors = validateItem(errors, id);
                if (errors.hasErrors()) {
                    return errors;
                }
            }
        }
        return errors;
    }

    private WebErrors validateItem(WebErrors errors, Integer id) {
        if (id != null) {
            CmsScoreItem item = manager.findById(id);
            if (item == null) {
                errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                errors.addErrorString(ResponseCode.API_CODE_NOT_FOUND);
                return errors;
            }
        }
        return errors;
    }

    private WebErrors validateGroup(WebErrors errors, Integer groupId) {
        if (groupId == null) {
            errors.addErrorString(Constants.API_MESSAGE_PARAM_REQUIRED);
            errors.addErrorString(ResponseCode.API_CODE_PARAM_REQUIRED);
            return errors;
        }
        CmsScoreGroup group = scoreGroupMng.findById(groupId);
        if (group == null) {
            errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
            errors.addErrorString(ResponseCode.API_CODE_NOT_FOUND);
            return errors;
        }
        return errors;
    }

    @Autowired
    private CmsScoreItemMng manager;
    @Autowired
    private CmsScoreGroupMng scoreGroupMng;
}
