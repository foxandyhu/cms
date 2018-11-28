package com.bfly.cms.content.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.content.entity.CmsModel;
import com.bfly.cms.content.service.CmsModelMng;
import com.bfly.cms.content.entity.CmsModelItem;
import com.bfly.cms.content.service.CmsModelItemMng;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.common.web.springmvc.MessageResolver;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 模型字段Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 10:58
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsModelItemApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsModelItemApiAct.class);

    /**
     * 模型字段列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:01
     */
    @RequestMapping("/item/list")
    public void list(Integer modelId, Boolean isChannel, Boolean hasDisabled,
                     HttpServletRequest request, HttpServletResponse response) {
        //默认查询栏目模型
        if (isChannel == null) {
            isChannel = true;
        }
        List<CmsModelItem> list = manager.getList(modelId, isChannel, hasDisabled);
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJsonList());
            }
        }
        String body = jsonArray.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型字段详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:01
     */
    @RequestMapping("/item/get")
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsModelItem bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsModelItem();
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

    /**
     * 新增模型字段
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:01
     */
    @SignValidate
    @RequestMapping("/item/save")
    public void save(CmsModelItem bean, Integer modelId, HttpServletResponse response, HttpServletRequest request){
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        boolean fieldExit = false;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getChannel(), bean.getField(), modelId,
                bean.getLabel(), bean.getDataType(), bean.getPriority(), bean.getRequired());
        if (!errors.hasErrors()) {
            if (!StringUtils.isBlank(bean.getOptValue())) {
                bean.setOptValue(replaceLocaleSplit(bean.getOptValue(), request));
            }
            List<CmsModelItem> list = manager.getList(modelId, bean.getChannel(), null);
            if (list != null) {
                for (CmsModelItem item:list) {
                    if (item.getField().equals(bean.getField())) {
                        fieldExit = true;
                        break;
                    }
                }
            }
            //判断字段是否已存在
            if (fieldExit) {
                message = Constants.API_MESSAGE_FIELD_EXIST;
                code = ResponseCode.API_CODE_FIELD_EXIST;
            } else {
                bean.init();
                bean = manager.save(bean, modelId);
                log.info("update CmsModelItem id={}.", bean.getId());
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新模型字段
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:02
     */
    @SignValidate
    @RequestMapping("/item/update")
    public void update(CmsModelItem bean, Integer modelId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        boolean result = false;
        WebErrors errors = WebErrors.create(request);
        if (bean.getCustom() != null && !bean.getCustom()) {
            errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(), bean.getLabel(), bean.getRequired());
        } else {
            errors = ApiValidate.validateRequiredParams(request, errors, bean.getChannel(), bean.getId(), bean.getField(),
                    bean.getLabel(), bean.getDataType(), bean.getPriority(), bean.getRequired());
        }
        if (!errors.hasErrors()) {
            List<CmsModelItem> list = manager.getList(modelId, bean.getChannel(), null);
            if (list != null) {
                for (CmsModelItem item : list) {
                    if (item.getField().equals(bean.getField())
                            && !item.getId().equals(bean.getId())) {
                        result = true;
                        break;
                    }
                }
            }
            if (result) {
                message = Constants.API_MESSAGE_FIELD_EXIST;
                code = ResponseCode.API_CODE_FIELD_EXIST;
            } else {
                bean = manager.update(bean);
                body = "{\"id\":" + "\"" + bean.getId() + "\"}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除模型字段
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:02
     */
    @SignValidate
    @RequestMapping("/item/delete")
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            try {
                Integer[] idArray = StrUtils.getInts(ids);
                CmsModelItem[] beans = manager.deleteByIds(idArray);
                for (CmsModelItem bean : beans) {
                    log.info("delete CmsModelItem id={}", bean.getId());
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
     * 更新模型字段顺序
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:02
     */
    @SignValidate
    @RequestMapping("/item/priority")
    public void priority(String ids, String priorities, String labels, String singles,
                         String displays, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, priorities, labels, singles, displays);
        if (!errors.hasErrors()) {
            Integer[] idArray = StrUtils.getInts(ids);
            if (idArray.length > 0) {
                Integer[] priority = StrUtils.getInts(priorities);
                String[] label = null;
                if (StringUtils.isNotBlank(labels)) {
                    label = labels.split(",");
                }
                Boolean[] single = strToBoolean(singles);
                Boolean[] display = strToBoolean(displays);
                errors = validatePriority(errors, idArray, priority, label, single, display);
                if (errors.hasErrors()) {
                    message = errors.getErrors().get(0);
                    code = ResponseCode.API_CODE_PARAM_ERROR;
                } else {
                    manager.updatePriority(idArray, priority, label, single, display);
                    message = Constants.API_MESSAGE_SUCCESS;
                    code = ResponseCode.API_CODE_CALL_SUCCESS;
                }
            } else {
                message = Constants.API_MESSAGE_PARAM_ERROR;
                code = ResponseCode.API_CODE_PARAM_ERROR;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 批量保存模型字段
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:03
     */
    @SignValidate
    @RequestMapping("/item/list_save")
    public void saveList(Integer modelId, Boolean isChannel, String fields, String labels,
                         String dataTypes, String prioritys, String singles, String displays,
                         HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, modelId, fields);
        if (!errors.hasErrors()) {
            CmsModel model = cmsModelMng.findById(modelId);
            if (model != null) {
                String[] field = null;
                if (StringUtils.isNotBlank(fields)) {
                    field = fields.split(",");
                }
                String[] label = null;
                if (StringUtils.isNotBlank(labels)) {
                    label = labels.split(",");
                }
                Integer[] dataType = StrUtils.getInts(dataTypes);
                Integer[] priority = StrUtils.getInts(prioritys);
                Boolean[] signles = strToBoolean(singles);
                Boolean[] display = strToBoolean(displays);
                if (field != null && label != null && dataType != null && priority != null && signles != null && display != null) {
                    if (field.length != label.length || field.length != dataType.length || field.length != priority.length ||
                            field.length != signles.length || field.length != display.length) {
                        message = Constants.API_MESSAGE_PARAM_ERROR;
                        code = ResponseCode.API_CODE_PARAM_ERROR;
                    } else {
                        List<CmsModelItem> list = getItems(model, isChannel, field, label, dataType, priority, signles, display);
                        manager.saveList(list);
                        log.info("save CmsModelItem count={}", list.size());
                        message = Constants.API_MESSAGE_SUCCESS;
                        code = ResponseCode.API_CODE_CALL_SUCCESS;
                    }
                } else {
                    message = Constants.API_MESSAGE_PARAM_ERROR;
                    code = ResponseCode.API_CODE_PARAM_ERROR;
                }
            } else {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 模型字段校验检查
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/26 11:04
     */
    @RequestMapping("/item/check_field")
    public void checkField(Integer modelId, Boolean channel, String field, HttpServletRequest request, HttpServletResponse response) {
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, modelId, field);
        JSONObject json = new JSONObject();
        boolean result = false;
        if (!errors.hasErrors()) {
            List<CmsModelItem> list = manager.getList(modelId, channel, null);
            if (list != null) {
                for (CmsModelItem item : list) {
                    if (item.getField().equals(field)) {
                        result = true;
                        break;
                    }
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

    private WebErrors validatePriority(WebErrors errors, Integer[] idArray, Integer[] priorities, String[] labels,
                                       Boolean[] singles, Boolean[] displays) {
        if (idArray != null && priorities != null && labels != null && singles != null && displays != null) {
            if (idArray.length != priorities.length || idArray.length != labels.length ||
                    idArray.length != singles.length || idArray.length != displays.length) {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        }
        return errors;
    }

    private String replaceLocaleSplit(String s, HttpServletRequest request) {
        String split = MessageResolver.getMessage(request,
                "cmsModelItem.optValue.split");
        return StringUtils.replace(s, split, ",");
    }

    private List<CmsModelItem> getItems(CmsModel model, boolean isChannel,
                                        String[] fields, String[] labels, Integer[] dataTypes,
                                        Integer[] prioritys, Boolean[] singles, Boolean[] displays) {
        List<CmsModelItem> list = new ArrayList<>();
        CmsModelItem item;
        for (int i = 0, len = fields.length; i < len; i++) {
            if (!StringUtils.isBlank(fields[i])) {
                item = new CmsModelItem();
                item.setCustom(false);
                item.setModel(model);
                item.setChannel(isChannel);
                item.setField(fields[i]);
                item.setLabel(labels[i]);
                item.setPriority(prioritys[i]);
                item.setDataType(dataTypes[i]);
                item.setSingle(singles[i]);
                item.setDisplay(displays[i]);
                if ("name".equals(fields[i]) || "path".equals(fields[i])
                        || "channelId".equals(fields[i]) || "title".equals(fields[i])) {
                    item.setRequired(true);
                } else {
                    item.setRequired(false);
                }
                list.add(item);
            }
        }
        return list;
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

    @Autowired
    private CmsModelMng cmsModelMng;
    @Autowired
    private CmsModelItemMng manager;
}
