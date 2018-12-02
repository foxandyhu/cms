package com.bfly.admin.system.action;

import com.bfly.core.annotation.SignValidate;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.Constants;
import com.bfly.core.web.ResponseCode;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.service.CmsConfigMng;
import com.bfly.common.util.JsonUtil;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.web.WebErrors;
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
import java.util.Map;

/**
 * 内容佣金配置Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:02
 */
@Controller
@RequestMapping(value = "/api/admin")
public class CmsConfigContentChargeApiAct {
    private static final Logger log = LoggerFactory.getLogger(CmsConfigContentChargeApiAct.class);

    /**
     * 获得内容佣金设置信息
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:03
     */
    @RequestMapping("/config/content_charge_get")
    public void get(HttpServletRequest request, HttpServletResponse response) {
        CmsConfigContentCharge bean = manager.getDefault();
        CmsConfig config = cmsConfigMng.get();
        Map<String, String> map = config.getRewardFixAttr();
        JSONArray jsonArray = JsonUtil.mapToJsonArray(map);
        JSONObject json = bean.convertToJson();
        json.put("fixMap", jsonArray);
        String body = json.toString();
        String message = Constants.API_MESSAGE_SUCCESS;
        String code = ResponseCode.API_CODE_CALL_SUCCESS;
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    /**
     * 更新内容佣金设置信息
     *
     * @param request             HttpServletRequest
     * @param response            HttpServletResponse
     * @param bean                配置信息
     * @param alipayKey           支付宝公钥
     * @param alipayPrivateKey    支付宝RSA私钥
     * @param alipayPublicKey     支付宝RSA公钥
     * @param payTransferPassword 转账登陆密码
     * @param transferApiPassword 企业转账接口API密钥
     * @param weixinPassword      微信支付商户密钥
     * @param weixinSecret        微信公众号secret
     * @author andy_hulibo@163.com
     * @date 2018/11/24 18:03
     */
    @SignValidate
    @RequestMapping("/config/content_charge_update")
    public void update(CmsConfigContentCharge bean, String weixinPassword,
                       String weixinSecret, String alipayKey, String alipayPublicKey,
                       String alipayPrivateKey, String transferApiPassword, String payTransferPassword,
                       HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        errors = ApiValidate.validateRequiredParams(request, errors, bean.getWeixinAppId(),
                bean.getWeixinAccount(), bean.getRewardPattern(), bean.getRewardMin(), bean.getRewardMax(),
                bean.getAlipayAccount(), bean.getChargeRatio(), bean.getMinDrawAmount());
        if (!errors.hasErrors()) {
            errors = validateUpdate(bean.getId(), request);
            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = ResponseCode.API_CODE_PARAM_ERROR;
            } else {
                Map<String, String> attrs = new HashMap<>(15);
                attrs.put("weixinPassword", weixinPassword);
                attrs.put("weixinSecret", weixinSecret);
                attrs.put("alipayKey", alipayKey);
                attrs.put("alipayPublicKey", alipayPublicKey);
                attrs.put("alipayPrivateKey", alipayPrivateKey);
                attrs.put("transferApiPassword", transferApiPassword);
                Map<String, String> fixMap = RequestUtils.getRequestMap(request, "attr_");
                bean = manager.update(bean, payTransferPassword, attrs, fixMap);
                log.info("update CmsConfigContentCharge id={}.", bean.getId());
                body = "{\"id\":" + bean.getId() + "}";
                message = Constants.API_MESSAGE_SUCCESS;
                code = ResponseCode.API_CODE_CALL_SUCCESS;
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        String idStr="id";
        if (errors.ifNull(id, idStr, false)) {
            return true;
        }
        CmsConfigContentCharge entity = manager.findById(id);
        return errors.ifNotExist(entity, CmsConfigContentCharge.class, id, false);
    }

    @Autowired
    private CmsConfigMng cmsConfigMng;
    @Autowired
    private CmsConfigContentChargeMng manager;
}
