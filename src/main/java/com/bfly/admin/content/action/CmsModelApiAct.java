package com.bfly.admin.content.action;

import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.logs.service.CmsLogMng;
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
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:40
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsModelApiAct extends BaseAdminController {
    private static final Logger log = LoggerFactory.getLogger(CmsModelApiAct.class);

    /**
     * 模型管理列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:43
     */
    @RequestMapping("/model/list")
    public void list(Boolean containDisabled, Boolean hasContent, HttpServletRequest request, HttpServletResponse response) {
        if (containDisabled == null) {
            containDisabled = true;
        }
        List<CmsModel> list = manager.getList(containDisabled, hasContent);
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson());
            }
        }
        String body = jsonArray.toString();
        ApiResponse apiResult = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResult);
    }

    /**
     * 模型管理详情
     *
     * @param id 模型编号
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:43
     */
    @RequestMapping("/model/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsModel bean;
        if (id.equals(0)) {
            bean = new CmsModel();
        } else {
            bean = manager.findById(id);
        }
        if (bean == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        JSONObject json = bean.convertToJson();
        String body = json.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型编号校验 可用编号返回true，已存在编号返回false
     *
     * @param id 模型编号
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:44
     */
    @RequestMapping("/model/check_id")
    public void chekcID(Integer id, HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        JSONObject json = new JSONObject();
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, id);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        if (id <= 0) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        CmsModel model = manager.findById(id);
        if (model == null) {
            result = true;
        }
        json.put("result", result);
        String body = json.toString();
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型添加
     *
     * @param bean 模型对象
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:44
     */
    @SignValidate
    @RequestMapping("/model/save")
    public void save(CmsModel bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getGlobal(), bean.getPath(), bean.getTplChannelPrefix(), bean.getPriority(), bean.getDisabled());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsModel model = manager.findById(bean.getId());
        if (model != null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean.init();
        bean = manager.save(bean);
        log.info("save CmsModel id={}", bean.getId());
        cmsLogMng.operating(request, "cmsModel.log.save", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型修改
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:45
     */
    @SignValidate
    @RequestMapping("/model/update")
    public void update(CmsModel bean, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getName(), bean.getGlobal(), bean.getPath(), bean.getTplChannelPrefix(), bean.getPriority(), bean.getDisabled());
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        CmsModel model = manager.findById(bean.getId());
        if (model == null) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        bean = manager.update(bean);
        log.info("update CmsModel id={}", bean.getId());
        cmsLogMng.operating(request, "cmsModel.log.update", "id=" + bean.getId() + ";name=" + bean.getName());
        String body = "{\"id\":" + "\"" + bean.getId() + "\"}";
        ApiResponse apiResponse = ApiResponse.getSuccess(body);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型删除
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:45
     */
    @SignValidate
    @RequestMapping("/model/delete")
    public void delete(String ids, HttpServletResponse response, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        try {
            Integer[] idArray = StrUtils.getInts(ids);
            CmsModel[] beans = manager.deleteByIds(idArray);
            for (CmsModel bean : beans) {
                log.info("delete CmsModel id={}", bean.getId());
                cmsLogMng.operating(request, "cmsModel.log.delete", "id=" + bean.getId() + ";name=" + bean.getName());
            }
        } catch (Exception e) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型批量保存
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 10:45
     */
    @SignValidate
    @RequestMapping("/model/priority")
    public void priority(String ids, String priorities, String disableds, Integer defId, HttpServletRequest request, HttpServletResponse response) {
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, priorities, disableds, defId);
        if (errors.hasErrors()) {
            throw new ApiException("缺少参数", ResponseCode.API_CODE_PARAM_REQUIRED);
        }
        Integer[] wid = StrUtils.getInts(ids);
        Integer[] priority = StrUtils.getInts(priorities);
        Boolean[] disabled = strToBoolean(disableds);
        errors = validatePriority(wid, priority, disabled, defId, request);
        if (errors.hasErrors()) {
            throw new ApiException("参数错误", ResponseCode.API_CODE_PARAM_ERROR);
        }
        manager.updatePriority(wid, priority, disabled, defId);
        ApiResponse apiResponse = ApiResponse.getSuccess();
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private Boolean[] strToBoolean(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] split = str.split(",");
        Boolean[] booleans = new Boolean[split.length];
        for (int i = 0; i < split.length; i++) {
            booleans[i] = Boolean.parseBoolean(split[i]);
        }
        return booleans;
    }

    private WebErrors validatePriority(Integer[] wid, Integer[] priority, Boolean[] disabled, Integer defId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (wid.length != priority.length || wid.length != disabled.length) {
            String s = Constants.API_MESSAGE_PARAM_ERROR;
            errors.addErrorString(s);
            return errors;
        }
        for (int i = 0, len = wid.length; i < len; i++) {
            if (vldExist(wid[i], errors)) {
                errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                return errors;
            }
            if (priority[i] == null) {
                priority[i] = 0;
            }
            if (disabled[i] == null) {
                disabled[i] = false;
            }
        }
        if (vldExist(defId, errors)) {
            errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        String idStr = "id";
        if (errors.ifNull(id, idStr, false)) {
            return true;
        }
        CmsModel entity = manager.findById(id);
        if (errors.ifNotExist(entity, CmsModel.class, id, false)) {
            return true;
        }
        return false;
    }

    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsModelMng manager;
}
