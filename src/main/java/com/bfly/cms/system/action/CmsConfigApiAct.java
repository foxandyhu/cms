package com.bfly.cms.system.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.content.service.ContentQueryFreshTimeCache;
import com.bfly.cms.system.entity.*;
import com.bfly.cms.system.entity.Config.ConfigEmailSender;
import com.bfly.cms.system.entity.Config.ConfigLogin;
import com.bfly.cms.system.entity.Config.ConfigMessageTemplate;
import com.bfly.cms.system.service.CmsConfigItemMng;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.cms.system.service.ConfigMng;
import com.bfly.cms.system.service.MessageTemplate;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.sms.service.CmsSmsMng;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统设置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/23 16:54
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsConfigApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsConfigApiAct.class);

    /**
     * 获得系统设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 16:54
     */
    @RequestMapping("/config/get")
    public void getConfig(HttpServletRequest request, HttpServletResponse response) {
        CmsConfig cmsConfig = manager.get();
        JSONObject json = new JSONObject();
        // 查询已经配置了的短信服务商
        List<CmsSms> list = smsMng.getList();
        JSONArray jsonArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, list.get(i).convertToJson(true));
            }
        }

        if (cmsConfig != null) {
            cmsConfig.init();
            json = cmsConfig.convertToJson(jsonArray);
        }
        String body = json.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改系统设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:11
     */
    @SignValidate
    @RequestMapping("/config/system_update")
    public void systemUpdate(CmsConfig bean, CmsConfigAttr attr,
                             HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(),
                bean.getDefImg(), bean.getDbFileUri());
        if (!errors.hasErrors()) {
            CmsConfig config = manager.get();
            if (!config.getId().equals(bean.getId())) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean = manager.update(bean);
                manager.updateConfigAttr(attr);
                log.info("update systemConfig of CmsConfig.");
                cmsLogMng.operating(request, "cmsConfig.log.systemUpdate", null);
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取其他设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:06
     */
    @RequestMapping("/config/attr_get")
    public void attrGet(HttpServletRequest request, HttpServletResponse response) {
        CmsConfigAttr attr = manager.get().getConfigAttr();
        String body = attr.attrToJson().toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新其他设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:08
     */
    @SignValidate
    @RequestMapping("/config/attr_update")
    public void attrUpdate(CmsConfigAttr bean, HttpServletRequest request,
                           HttpServletResponse response, String bdToken, String isBdSubmit) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getPictureNew(),
                bean.getPreview(), bean.getFlowSwitch(), bean.getCodeImgWidth(), bean.getCodeImgHeight(),
                bean.getContentFreshMinute());
        if (!errors.hasErrors()) {
            String bdTokenStr = "bdToken";
            String isBdSubmitStr = "isBdSubmit";
            if (bean.getAttr() != null) {
                if (bean.getAttr().containsKey(bdTokenStr)) {
                    if (StringUtils.isNotBlank(bdToken)) {
                        bean.getAttr().remove(bdTokenStr);
                        bean.getAttr().put(bdTokenStr, bdToken);
                    }
                } else {
                    if (StringUtils.isNotBlank(bdToken)) {
                        bean.getAttr().put(bdTokenStr, bdToken);
                    }
                }
                if (bean.getAttr().containsKey(isBdSubmitStr)) {
                    if (StringUtils.isNotBlank(isBdSubmit)) {
                        bean.getAttr().remove(isBdSubmitStr);
                        bean.getAttr().put(isBdSubmitStr, isBdSubmit);
                    }
                } else {
                    bean.getAttr().put(isBdSubmitStr, isBdSubmit);
                }
            } else {
                Map<String, String> attr = new HashMap<>(2);
                attr.put(bdTokenStr, bdToken);
                attr.put(isBdSubmitStr, isBdSubmit);
                bean.setAttr(attr);
            }

            manager.updateConfigAttr(bean);
            log.info("update attrs of CmsConfig.");
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 清除其他设置信息缓存内容
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:08
     */
    @RequestMapping("/config/attr_clear")
    public void clearContentQueryFreshTimeCache(HttpServletResponse response, HttpServletRequest request) {
        contentQueryFreshTimeCache.clearCache();
        String body = "\"\"";
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取水印设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:08
     */
    @RequestMapping("/config/mark_get")
    public void markGet(HttpServletRequest request, HttpServletResponse response) {
        MarkConfig config = manager.get().getMarkConfig();
        String body = config.convertToJson().toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改水印设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:09
     */
    @SignValidate
    @RequestMapping("/config/mark_update")
    public void markUpdate(MarkConfig bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getOn(),
                bean.getContent(), bean.getMinHeight(), bean.getMinWidth(), bean.getColor(),
                bean.getAlpha(), bean.getPos(), bean.getOffsetX(), bean.getOffsetY());
        if (!errors.hasErrors()) {
            manager.updateMarkConfig(bean);
            log.info("update markConfig of CmsConfig.");
            cmsLogMng.operating(request, "cmsConfig.log.markUpdate", null);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取会员设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:10
     */
    @RequestMapping("/config/member_get")
    public void memberGet(HttpServletRequest request, HttpServletResponse response) {
        MemberConfig config = manager.get().getMemberConfig();
        String body = config.convertToJson().toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新会员设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:10
     */
    @SignValidate
    @RequestMapping("/config/member_update")
    public void memeberUpdate(MemberConfig bean,
                              HttpServletResponse response, HttpServletRequest request) {
        String body = "\"\"";
        String message = "\"\"";
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        try {
            manager.updateMemberConfig(bean);
            log.info("update memberConfig of CmsConfig.");
            cmsLogMng.operating(request, "cmsConfig.log.memberUpdate", null);
            message = Constants.API_MESSAGE_SUCCESS;
        } catch (Exception e) {
            code = ResponseCode.API_CODE_CALL_FAIL;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取登录设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:10
     */
    @RequestMapping("/config/login_get")
    public void loginGet(HttpServletRequest request, HttpServletResponse response) {
        ConfigLogin login = configMng.getConfigLogin();
        EmailSender sender = configMng.getEmailSender();
        MessageTemplate passwordMT = configMng.getForgotPasswordMessageTemplate();
        MessageTemplate registerMT = configMng.getRegisterMessageTemplate();
        JSONObject json = createLoginJson(login, sender, passwordMT, registerMT);
        String body = json.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改登录设置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:11
     */
    @SignValidate
    @RequestMapping("/config/login_update")
    public void loginUpdate(ConfigLogin configLogin, ConfigEmailSender emailSender,
                            ConfigMessageTemplate msgTpl, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, configLogin.getErrorTimes(),
                configLogin.getErrorInterval(), emailSender.getHost(), emailSender.getUsername(),
                msgTpl.getForgotPasswordSubject(), msgTpl.getForgotPasswordText(), msgTpl.getRegisterSubject(),
                msgTpl.getRegisterText());
        if (!errors.hasErrors()) {
            //留空则默认原有密码
            if (StringUtils.isBlank(emailSender.getPassword())) {
                emailSender.setPassword(configMng.getEmailSender().getPassword());
            }
            configMng.updateOrSave(configLogin.getAttr());
            configMng.updateOrSave(emailSender.getAttr());
            configMng.updateOrSave(msgTpl.getAttr());
            log.info("update loginCoinfig of Config.");
            cmsLogMng.operating(request, "cmsConfig.log.loginUpdate", null);
            message = Constants.API_MESSAGE_SUCCESS;
            code = ResponseCode.API_CODE_CALL_SUCCESS;
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 获取会员注册模型列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:13
     */
    @RequestMapping("/config/register_item_list")
    public void getRegisterItemList(HttpServletResponse response, HttpServletRequest request) {
        Integer id = manager.get().getId();
        List<CmsConfigItem> list = cmsConfigItemMng.getList(id, CmsConfigItem.CATEGORY_REGISTER);
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
     * 获取会员注册模型详情
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:14
     */
    @RequestMapping("/config/register_item_get")
    public void getRegisterItemGet(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        CmsConfigItem bean;
        if (id != null) {
            if (id.equals(0)) {
                bean = new CmsConfigItem();
            } else {
                bean = cmsConfigItemMng.findById(id);
            }
            if (bean == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean.init();
                body = bean.convertToJsonGet().toString();
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 添加会员注册模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:14
     */
    @SignValidate
    @RequestMapping("/config/register_item_save")
    public void registerItemSave(CmsConfigItem bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getField(), bean.getLabel()
                , bean.getPriority(), bean.getRequired(), bean.getDataType());
        if (!errors.hasErrors()) {
            if (bean.getHelpPosition().length() > 1) {
                message = Constants.API_MESSAGE_PARAM_ERROR;
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                bean.setConfig(manager.get());
                bean.setCategory(CmsConfigItem.CATEGORY_REGISTER);
                bean.init();
                bean = cmsConfigItemMng.save(bean);
                log.info("save CmsConfigItem id={}", bean.getId());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 修改会员注册模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:14
     */
    @SignValidate
    @RequestMapping("/config/register_item_update")
    public void registerItemUpdate(CmsConfigItem bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getId(),
                bean.getField(), bean.getLabel()
                , bean.getPriority(), bean.getRequired(), bean.getDataType());
        if (!errors.hasErrors()) {
            CmsConfigItem item = cmsConfigItemMng.findById(bean.getId());
            if (item == null) {
                message = Constants.API_MESSAGE_OBJECT_NOT_FOUND;
                code = ResponseCode.API_CODE_NOT_FOUND;
            } else {
                bean = cmsConfigItemMng.update(bean);
                log.info("update CmsConfigItem id={}", bean.getId());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 删除会员注册模型
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:17
     */
    @SignValidate
    @RequestMapping("/config/register_item_delete")
    public void registerItemDelete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids);
        if (!errors.hasErrors()) {
            try {
                Integer[] idArr = StrUtils.getInts(ids);
                CmsConfigItem[] beans = cmsConfigItemMng.deleteByIds(idArr);
                for (CmsConfigItem bean : beans) {
                    log.info("delete CmsConfigItem id={}", bean.getId());
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
     * 批量保存会员注册模型信息
     *
     * @param ids        模型ID集合
     * @param labels     名称
     * @param priorities 优先级
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2018/11/23 17:25
     */
    @SignValidate
    @RequestMapping("/config/register_item_priority")
    public void registerItemPriority(String ids, String labels, String priorities,
                                     HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, ids, priorities, labels);
        if (!errors.hasErrors()) {
            Integer[] idArr = StrUtils.getInts(ids);
            String[] labelArr = null;
            if (StringUtils.isNotBlank(labels)) {
                labelArr = labels.split(",");
            }
            Integer[] priorityArr = StrUtils.getInts(priorities);
            errors = validatePriority(errors, idArr, labelArr, priorityArr);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                cmsConfigItemMng.updatePriority(idArr, priorityArr, labelArr);
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validatePriority(WebErrors errors, Integer[] idArr, String[] labels, Integer[] priorityArr) {
        if (idArr != null) {
            for (Integer id : idArr) {
                CmsConfigItem item = cmsConfigItemMng.findById(id);
                if (item == null) {
                    errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                    return errors;
                }
            }
        }
        if (idArr != null && labels != null && priorityArr != null) {
            if (idArr.length != labels.length || idArr.length != priorityArr.length) {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        }
        return errors;
    }

    private JSONObject createLoginJson(ConfigLogin login, EmailSender sender, MessageTemplate passwordMT,
                                       MessageTemplate registerMT) {
        JSONObject json = new JSONObject();
        if (login != null && login.getErrorTimes() != null) {
            json.put("errorTimes", login.getErrorTimes());
        } else {
            json.put("errorTimes", "");
        }
        if (login != null && login.getErrorInterval() != null) {
            json.put("errorInterval", login.getErrorInterval());
        } else {
            json.put("errorInterval", "");
        }
        if (sender != null && StringUtils.isNotBlank(sender.getHost())) {
            json.put("host", sender.getHost());
        } else {
            json.put("host", "");
        }
        if (sender != null && sender.getPort() != null) {
            json.put("port", sender.getPort());
        } else {
            json.put("port", "");
        }
        if (sender != null && StringUtils.isNotBlank(sender.getUsername())) {
            json.put("username", sender.getUsername());
        } else {
            json.put("username", "");
        }
        if (sender != null && StringUtils.isNotBlank(sender.getPassword())) {
            json.put("password", sender.getPassword());
        } else {
            json.put("password", "");
        }
        if (sender != null && StringUtils.isNotBlank(sender.getEncoding())) {
            json.put("encoding", sender.getEncoding());
        } else {
            json.put("encoding", "");
        }
        if (sender != null && StringUtils.isNotBlank(sender.getPersonal())) {
            json.put("personal", sender.getPersonal());
        } else {
            json.put("personal", "");
        }
        if (passwordMT != null && StringUtils.isNotBlank(passwordMT.getForgotPasswordSubject())) {
            json.put("forgotPasswordSubject", passwordMT.getForgotPasswordSubject());
        } else {
            json.put("forgotPasswordSubject", "");
        }
        if (passwordMT != null && StringUtils.isNotBlank(passwordMT.getForgotPasswordText())) {
            json.put("forgotPasswordText", passwordMT.getForgotPasswordText());
        } else {
            json.put("forgotPasswordText", "");
        }
        if (registerMT != null && StringUtils.isNotBlank(registerMT.getRegisterSubject())) {
            json.put("registerSubject", registerMT.getRegisterSubject());
        } else {
            json.put("registerSubject", "");
        }
        if (registerMT != null && StringUtils.isNotBlank(registerMT.getRegisterText())) {
            json.put("registerText", registerMT.getRegisterText());
        } else {
            json.put("registerText", "");
        }
        return json;
    }

    @Autowired
    private ContentQueryFreshTimeCache contentQueryFreshTimeCache;
    @Autowired
    private CmsLogMng cmsLogMng;
    @Autowired
    private CmsConfigItemMng cmsConfigItemMng;
    @Autowired
    private CmsConfigMng manager;
    @Autowired
    private ConfigMng configMng;
    @Autowired
    private CmsSmsMng smsMng;
}
