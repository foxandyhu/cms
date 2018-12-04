package com.bfly.core.web;

import com.bfly.cms.webservice.entity.ApiAccount;
import com.bfly.common.util.PayUtil;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.Constants;
import com.bfly.core.exception.ApiException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ApiValidate {
    private static final Logger log = LoggerFactory.getLogger(ApiValidate.class);

    public static WebErrors validateRequiredParams(HttpServletRequest request, WebErrors errors, Object... params) {
        for (Object p : params) {
            //空字符串或者其他类型对象为空
            if ((p instanceof String && StringUtils.isBlank((String) p) || p == null)) {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_REQUIRED);
            }
        }
        return errors;
    }

    public static WebErrors validateApiAccount(HttpServletRequest request,
                                               WebErrors errors, ApiAccount apiAccount) {
        if (apiAccount == null || apiAccount.getDisabled()) {
            errors.addErrorString(Constants.API_MESSAGE_APP_PARAM_ERROR);
            log.error(RequestUtils.getIpAddr(request) + Constants.API_MESSAGE_APP_PARAM_ERROR);
        }
        return errors;
    }

    public static boolean validateSign(HttpServletRequest request, ApiAccount apiAccount, String sign) throws ApiException {
        // 获取非签名,空字符,文件的参数名和参数值
        Map<String, String> param = RequestUtils.getSignMap(request);
        // 密匙
        String appKey = apiAccount.getAppKey();
        String validateSign = PayUtil.createSign(param, appKey);
        if (StringUtils.isBlank(sign) || !sign.equals(validateSign)) {
            log.error(RequestUtils.getIpAddr(request) + " sign validate error");
            throw new ApiException("sign校验错误", ResponseCode.API_CODE_SIGN_ERROR);
        }
        return true;
    }

}
