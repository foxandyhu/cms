package com.bfly.cms.sms.action;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.sms.entity.CmsSms;
import com.bfly.cms.sms.entity.CmsSmsRecord;
import com.bfly.cms.sms.service.CmsSmsMng;
import com.bfly.cms.sms.service.CmsSmsRecordMng;
import com.bfly.cms.system.entity.CmsConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.entity.UnifiedUser;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.cms.user.service.UnifiedUserMng;
import com.bfly.common.util.SmsSendUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.Constants;
import com.bfly.core.web.ApiResponse;
import com.bfly.core.web.ApiValidate;
import com.bfly.core.web.ResponseCode;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.CmsUtils;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

/**
 * 发送短信Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 15:40
 */
@Controller
@RequestMapping(value = "/api/front")
public class CmsSmsApiAct {

    @Autowired
    private CmsSmsMng manager;
    @Autowired
    private CmsSmsRecordMng smsRecordManager;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private CmsUserExtMng userExtManager;
    @Autowired
    private UnifiedUserMng unifiedUserMng;
    @Autowired
    private CmsUserMng cmsUserMng;

    /**
     * @param mobilePhone 手机号
     * @param smsSendType 发送短信类型用途  1：注册   2：找回密码
     * @param vCode       验证码
     * @param username    用户名      找回密码
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:39
     * 发送短信，若配置为手机注册-不需要验证图形验证码，若未配置-需要验证图形验证码
     */
    @RequestMapping("/sms/send_register_msg")
    public void send(Integer smsSendType, String mobilePhone, String vCode, String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String body = "\"\"";
        String message = Constants.API_MESSAGE_PARAM_REQUIRED;
        String code = ResponseCode.API_CODE_PARAM_REQUIRED;
        WebErrors errors = WebErrors.create(request);
        CmsSite site = CmsUtils.getSite(request);
        CmsConfig config = site.getConfig();
        errors = ApiValidate.validateRequiredParams(request, errors, mobilePhone);
        if (!errors.hasErrors()) {
            //查询是否开启短信验证
            Integer type = config.getValidateType();
            //验证类型：0：无验证 1：邮件验证  2：SMS验证
            if (type != 2) {
                errors.addErrorString(Constants.API_MESSAGE_SMS_IS_DISABLE);
                errors.addErrorString(ResponseCode.API_CODE_SMS_IS_DISABLE);
            }
            Long id = config.getSmsID();
            String smsId = "";
            if (id != null && id != 0) {
                smsId = id.toString();
            }

            //发送短信类型用途  1：注册   2：找回密码
            if (smsSendType == 1) {
                errors = validateRegister(config, smsId, mobilePhone, vCode, errors, request, response);
            } else if (smsSendType == 2) {
                errors = validateForgotPassword(config, smsId, mobilePhone, vCode, errors, request, response, username);
            } else {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                errors.addErrorString(ResponseCode.API_CODE_PARAM_ERROR);
            }

            if (errors.hasErrors()) {
                message = errors.getErrors().get(0);
                code = errors.getErrors().get(1);
            } else {
                //获取模板平台 1阿里 2腾讯 3百度
                CmsSms bean = null;
                if (StringUtils.isNotBlank(smsId)) {
                    bean = manager.findById(Integer.parseInt(smsId));
                    Byte source = bean.getSource();

                    //创建验证码
                    Random r = new Random();
                    StringBuffer str = new StringBuffer();
                    //验证码6位数
                    Integer num = 6;
                    if (bean.getRandomNum() != null && bean.getRandomNum() > 0) {
                        num = bean.getRandomNum();
                    }
                    int i = 0;
                    while (i < num) {
                        str.append(r.nextInt(10));
                        i++;
                    }
                    String values = str.toString();

                    if (source.equals((byte) 1)) {
                        errors = sendByALi(bean, mobilePhone, values, errors, site, username, smsSendType);
                    } else if (source.equals((byte) 2)) {
                        errors = sendByTX(bean, mobilePhone, values, errors, site, username, smsSendType);
                    } else if (source.equals((byte) 3)) {
                        errors = sendByBaiDu(bean, mobilePhone, values, errors, site, username, smsSendType);
                    }
                    if (errors.hasErrors()) {
                        message = Constants.API_MESSAGE_SMS_ERROR;
                        code = ResponseCode.API_CODE_SMS_ERROR;
                    } else {

                        //获取验证码有效时间 系统默认三分钟有效
                        Integer effectiveTime = 3 * 60 * 60 * 1000;
                        Byte effectiveUnit = 1;
                        if (bean.getEffectiveTime() != null && bean.getEffectiveTime() > 0) {
                            effectiveTime = bean.getEffectiveTime();
                            if (bean.getEffectiveUnit() != null) {
                                //获取有效时间单位  有效时间单位 0秒 1分 2时
                                effectiveUnit = bean.getEffectiveUnit();
                            }
                            switch (effectiveUnit) {
                                case 0:
                                    //秒-毫秒
                                    effectiveTime = effectiveTime * 1000;
                                    break;
                                case 1:
                                    //分-毫秒
                                    effectiveTime = effectiveTime * 60 * 1000;
                                    break;
                                case 2:
                                    //时-毫秒
                                    effectiveTime = effectiveTime * 60 * 60 * 1000;
                                    break;
                                default:
                                    //秒-毫秒
                                    effectiveTime = effectiveTime * 1000;
                                    break;
                            }
                        }
                        //发送短信类型用途  1：注册   2：找回密码
                        if (smsSendType == 1) {
                            //验证码值
                            session.setAttribute("AUTO_CODE", values);
                            //验证码有效时间
                            session.setAttribute("AUTO_CODE_CREAT_TIME", System.currentTimeMillis() + effectiveTime);
                        } else if (smsSendType == 2) {
                            //验证码值
                            session.setAttribute("FORGOTPWD_AUTO_CODE", values);
                            //验证码有效时间
                            session.setAttribute("FORGOTPWD_AUTO_CODE_CREAT_TIME", System.currentTimeMillis() + effectiveTime);
                        }
                        message = Constants.API_MESSAGE_SUCCESS;
                        code = ResponseCode.API_CODE_CALL_SUCCESS;
                    }
                } else {
                    message = Constants.API_MESSAGE_SMS_NOT_SET;
                    code = ResponseCode.API_CODE_SMS_NOT_SET;
                }
            }
        }
        ApiResponse apiResponse = new ApiResponse(request, body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateRegister(CmsConfig config, String smsId,
                                       String mobilePhone, String vCode, WebErrors errors, HttpServletRequest request, HttpServletResponse response) {

        //判断验证码是否正确
        errors = validateCode(vCode, errors, request, response);
        if (errors.hasErrors()) {
            return errors;
        } else {
            //判断手机号是否已注册
            int countByPhone = userExtManager.countByPhone(mobilePhone);
            if (countByPhone != 0) {
                errors.addErrorString(Constants.API_MESSAGE_MOBILE_PHONE_EXIST);
                errors.addErrorString(ResponseCode.API_CODE_MOBILE_PHONE_EXIST);
                return errors;
            }
            errors = validateSMS(config, mobilePhone, errors, smsId);
        }
        return errors;
    }

    /**
     * @Title: validateForgotPassword
     * @Description: 找回密码SMS验证
     * @param: attr
     * @param: isSMSRegister
     * @param: smsId
     * @param: mobilePhone
     * @param: vCode
     * @param: errors
     * @return: WebErrors
     */
    private WebErrors validateForgotPassword(CmsConfig config, String smsId,
                                             String mobilePhone, String vCode, WebErrors errors, HttpServletRequest request, HttpServletResponse response, String username) {
        if (StringUtils.isBlank(username)) {
            errors.addErrorString(Constants.API_MESSAGE_USER_NOT_FOUND);
            errors.addErrorString(ResponseCode.API_CODE_USER_NOT_FOUND);
        } else {
            //判断验证码是否正确
            errors = validateCode(vCode, errors, request, response);
        }
        if (errors.hasErrors()) {
            return errors;
        } else {
            //判断手机号与用户名是否匹配
            UnifiedUser user = unifiedUserMng.getByUsername(username);
            CmsUser user2 = cmsUserMng.findByUsername(username);
            CmsUserExt userExt = null;
            if (user2 != null) {
                userExt = userExtManager.findById(user2.getId());
            }
            if (user == null || user2 == null || userExt == null) {
                // 用户名不存在
                errors.addErrorString(Constants.API_MESSAGE_USER_NOT_FOUND);
                errors.addErrorString(ResponseCode.API_CODE_USER_NOT_FOUND);
            } else if (StringUtils.isBlank(userExt.getMobile())) {
                // 用户没有设置手机号码
                errors.addErrorString(Constants.API_MESSAGE_NOT_MOBILE);
                errors.addErrorString(ResponseCode.API_CODE_MOBILE_NOT_SET);
            } else {
                String mobile = userExt.getMobile();
                if (!mobile.equals(mobilePhone)) {
                    //输入的手机号码与绑定的手机号不匹配
                    errors.addErrorString(Constants.API_MESSAGE_MOBILE_MISMATCHING);
                    errors.addErrorString(ResponseCode.API_CODE_MOBILE_MISMATCHING);
                }
            }
            errors = validateSMS(config, mobilePhone, errors, smsId);
        }
        return errors;
    }

    private WebErrors validateSMS(CmsConfig config, String mobilePhone, WebErrors errors, String smsId) {
        //判断手机号每日限制是否已达标
        List<CmsSmsRecord> findByPhone = smsRecordManager.findByPhone(mobilePhone);
        Integer dayCount = 0;
        if (config.getDayCount() != null) {
            dayCount = config.getDayCount();
        }
        //每日限制若大于0则需要进行限制校验
        if (dayCount > 0) {
            //比较当天发送记录是否达到每日限制次数
            if (findByPhone.size() >= dayCount) {
                errors.addErrorString(Constants.API_MESSAGE_SMS_LIMIT);
                errors.addErrorString(ResponseCode.API_CODE_SMS_LIMIT);
                return errors;
            } else if (findByPhone.size() > 0) {
                if (StringUtils.isNotBlank(smsId)) {
                    CmsSms bean = manager.findById(Integer.parseInt(smsId));
                    if (bean == null) {
                        errors.addErrorString(Constants.API_MESSAGE_OBJECT_NOT_FOUND);
                        errors.addErrorString(ResponseCode.API_CODE_NOT_FOUND);
                        return errors;
                    } else {
                        CmsSmsRecord record = findByPhone.get(0);
                        //判断手机号每条短信相隔时间
                        //获取间隔时间
                        long intervalTime = 1 * 60 * 60 * 1000;//系统默认间隔1分钟
                        Byte intervalUnit = 1;
                        long sendTime = record.getSendTime().getTime();
                        long currentTime = System.currentTimeMillis();
                        if (bean.getIntervalTime() != null && bean.getIntervalTime() > 0) {
                            intervalTime = bean.getIntervalTime();
                        }
                        if (bean.getIntervalUnit() != null) {
                            intervalUnit = bean.getIntervalUnit();
                        }
                        switch (intervalUnit) {
                            case 0:
                                //秒-毫秒
                                intervalTime = intervalTime * 1000;
                                break;
                            case 1:
                                //分-毫秒
                                intervalTime = intervalTime * 60 * 1000;
                                break;
                            case 2:
                                //时-毫秒
                                intervalTime = intervalTime * 60 * 60 * 1000;
                                break;
                            default:
                                //秒-毫秒
                                intervalTime = intervalTime * 1000;
                                break;
                        }
                        if (currentTime - sendTime < intervalTime) {
                            //当前时间减去上一次的发送时间，若小于限制间隔时间，则终止发送
                            errors.addErrorString(Constants.API_MESSAGE_INTERVAL_NOT_ENOUGH);
                            errors.addErrorString(ResponseCode.API_CODE_INTERVAL_NOT_ENOUGH);
                            return errors;
                        }
                    }
                } else {
                    //未配置
                    errors.addErrorString(Constants.API_MESSAGE_SMS_NOT_SET);
                    errors.addErrorString(ResponseCode.API_CODE_SMS_NOT_SET);
                    return errors;
                }
            }
        }
        return errors;
    }

    private WebErrors validateCode(String vCode, WebErrors errors, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(vCode)) {
            errors.addErrorString(Constants.API_MESSAGE_CAPTCHA_CODE_ERROR);
            errors.addErrorString(ResponseCode.API_CODE_CAPTCHA_CODE_ERROR);
            return errors;
        } else {
            try {
                if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), vCode)) {
                    errors.addErrorString(Constants.API_MESSAGE_CAPTCHA_CODE_ERROR);
                    errors.addErrorString(ResponseCode.API_CODE_CAPTCHA_CODE_ERROR);
                    return errors;
                }
            } catch (Exception e) {
                errors.addErrorString(Constants.API_MESSAGE_CREATE_ERROR);
                errors.addErrorString(ResponseCode.API_CODE_CALL_FAIL);
            }
        }
        return errors;
    }

    private WebErrors sendByALi(CmsSms bean, String mobilePhone, String values, WebErrors errors, CmsSite site, String username, Integer smsSendType) {
        try {
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = SmsSendUtils.sendByALi(bean, mobilePhone, values);
            if (sendSmsResponse.getCode() != null && "OK".equals(sendSmsResponse.getCode())) {
                //请求成功
                CmsUser user = null;
                if (StringUtils.isNotBlank(username)) {
                    user = cmsUserMng.findByUsername(username);
                }
                smsRecordManager.save(bean, mobilePhone, smsSendType, site, user);
                return errors;
            } else {
                errors.addErrorString(sendSmsResponse.getCode());
                return errors;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            errors.addErrorString(Constants.API_MESSAGE_SMS_ERROR);
        }
        return errors;
    }

    /***
     *
     * @param username
     * @param site
     * @param smsSendType
     * @Title: sendByBaiDu
     * @Description: 百度短信服务
     * @param: @param bean
     * @param: @param mobilePhone
     * @param: @param values
     * @param: @param errors
     * @param: @return
     * @return: WebErrors
     */
    private WebErrors sendByBaiDu(CmsSms bean, String mobilePhone, String values, WebErrors errors, CmsSite site, String username, Integer smsSendType) {

        // 发送请求
        SendMessageV2Response response = SmsSendUtils.sendByBaiDu(bean, mobilePhone, values);
        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            //请求成功
            CmsUser user = null;
            if (StringUtils.isNotBlank(username)) {
                user = cmsUserMng.findByUsername(username);
            }
            smsRecordManager.save(bean, mobilePhone, smsSendType, site, user);
            return errors;
        } else {
            errors.addErrorString(response.getCode());
            return errors;
        }
    }

    /**
     * 腾讯短信服务
     *
     * @param bean
     * @param mobilePhone
     * @param values
     * @param errors
     * @param username
     * @param site
     * @param smsSendType
     * @return
     */
    private WebErrors sendByTX(CmsSms bean, String mobilePhone, String values, WebErrors errors, CmsSite site, String username, Integer smsSendType) {
        try {
            if (bean.getTemplateCode() != null) {
                SmsSingleSenderResult result = SmsSendUtils.sendByTX(bean, mobilePhone, values);
                if (result.result == 0) {
                    //请求成功
                    CmsUser user = null;
                    if (StringUtils.isNotBlank(username)) {
                        user = cmsUserMng.findByUsername(username);
                    }
                    smsRecordManager.save(bean, mobilePhone, smsSendType, site, user);
                    return errors;
                } else {
                    errors.addErrorString(Constants.API_MESSAGE_SMS_ERROR);
                    return errors;
                }
            } else {
                errors.addErrorString(Constants.API_MESSAGE_PARAM_ERROR);
                return errors;
            }
        } catch (Exception e) {
            errors.addErrorString(Constants.API_MESSAGE_SMS_ERROR);
        }
        return errors;
    }
}
