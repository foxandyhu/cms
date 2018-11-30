package com.bfly.common.util;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.web.ClientCustomSSL;
import com.bfly.common.web.Constants;
import com.bfly.common.web.HttpClientUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.ui.ModelMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 14:19
 */
public class WeixinPay {

    /**
     * 微信统一下单
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:34
     */
    public static Map<String, String> weixinUniformOrder(CmsSite site, CmsUser user, String trade_type, String openId, String ip, String serverUrl, CmsConfigContentCharge config, Content content, String orderNum, Double rewardAmount) {
        Map<String, String> paramMap = new HashMap<>(15);
        // 微信分配的公众账号ID（企业号corpid即为此appId）[必填]
        paramMap.put("appid", config.getWeixinAppId());
        // 微信支付分配的商户号 [必填]
        paramMap.put("mch_id", config.getWeixinAccount());
        // 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" [非必填]
        paramMap.put("device_info", "WEB");
        // 随机字符串，不长于32位。 [必填]
        paramMap.put("nonce_str", RandomStringUtils.random(10, Num62.N62_CHARS));
        // 商品或支付单简要描述 [必填]
        paramMap.put("body", content.getTitle());
        // 商户系统内部的订单号,32个字符内、可包含字母, [必填]
        paramMap.put("out_trade_no", orderNum);
        // 符合ISO 4217标准的三位字母代码，默认人民币：CNY. [非必填]
        paramMap.put("fee_type", "CNY");
        Double amount = content.getChargeAmount();
        if (rewardAmount != null) {
            amount = rewardAmount;
        }
        // 金额必须为整数 单位为分 [必填]
        paramMap.put("total_fee", PayUtil.changeY2F(amount));
        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP [必填]
        paramMap.put("spbill_create_ip", ip);
        // 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 [必填]
        paramMap.put("notify_url", "http://" + site.getDomain() + "/order/payCallByWeiXin.html");
        // 交易类型{取值如下：JSAPI，NATIVE，APP，(JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付)}
        // [必填]
        paramMap.put("trade_type", trade_type);
        //openid trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
        if ("JSAPI".equals(trade_type)) {
            if (StringUtils.isNotBlank(openId)) {
                paramMap.put("openid", openId);
            } else if (user != null && user.getUserAccount() != null) {
                paramMap.put("openid", user.getUserAccount().getAccountWeixinOpenId());
            }
        }
        // 商品ID{trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。}
        paramMap.put("product_id", content.getId().toString());
        if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
            // 根据微信签名规则，生成签名
            paramMap.put("sign", PayUtil.createSign(paramMap, config.getTransferApiPassword()));
        }
        // 把参数转换成XML数据格式
        String xmlWeChat = PayUtil.assembParamToXml(paramMap);
        String resXml = HttpClientUtil.post(serverUrl, xmlWeChat);
        Map<String, String> map = new HashMap<>(15);
        try {
            if (StringUtils.isNotBlank(resXml)) {
                map = PayUtil.parseXMLToMap(resXml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 微信订单查询
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:35
     */
    public static Map<String, String> weixinOrderQuery(String transactionId, String orderNum, String serverUrl, CmsConfigContentCharge config) {
        Map<String, String> paramMap = new HashMap<>(5);
        // 微信分配的公众账号ID（企业号corpid即为此appId）[必填]
        paramMap.put("appid", config.getWeixinAppId());
        // 微信支付分配的商户号 [必填]
        paramMap.put("mch_id", config.getWeixinAccount());
        // 微信订单号
        paramMap.put("transaction_id", transactionId);
        //商户订单号
        paramMap.put("out_trade_no", orderNum);
        // 随机字符串，不长于32位。 [必填]
        paramMap.put("nonce_str", RandomStringUtils.random(10, Num62.N62_CHARS));
        // 签名类型
        paramMap.put("sign_type", "MD5");
        if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
            // 根据微信签名规则，生成签名
            paramMap.put("sign", PayUtil.createSign(paramMap, config.getTransferApiPassword()));
        }
        // 把参数转换成XML数据格式
        String xmlWeChat = PayUtil.assembParamToXml(paramMap);
        String resXml = HttpClientUtil.post(serverUrl, xmlWeChat);
        Map<String, String> map = new HashMap<>(5);
        try {
            if (StringUtils.isNotBlank(resXml)) {
                map = PayUtil.parseXMLToMap(resXml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 微信扫码支付
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:35
     */
    public static void enterWeiXinPay(CmsSite site, CmsUser user, String serverUrl, String ip, CmsConfigContentCharge config, Content content, String orderNumber, Double rewardAmount, ModelMap model) throws JSONException {
        if (StringUtils.isBlank(config.getWeixinAppId()) || StringUtils.isBlank(config.getWeixinAccount())) {
            throw new RuntimeException("error.contentCharge.need.appid");
        }
        if (StringUtils.isBlank(config.getTransferApiPassword())) {
            throw new RuntimeException("error.contentCharge.need.key");
        }
        Map<String, String> map = weixinUniformOrder(site, user, "NATIVE", null, ip, serverUrl, config, content, orderNumber, rewardAmount);
        String returnCode = map.get("return_code");
        if (StringUtils.isNotBlank(returnCode)) {
            if ("FAIL".equalsIgnoreCase(returnCode)) {
                throw new RuntimeException(map.get("return_msg"));
            }
            if (map.get("err_code") != null) {
                throw new RuntimeException(map.get("err_code_des"));
            }
            if ("SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
                String codeUrl = map.get("code_url");
                model.addAttribute("code_url", codeUrl);
                model.addAttribute("orderNumber", orderNumber);
                if (rewardAmount != null) {
                    model.addAttribute("payAmount", rewardAmount);
                } else {
                    model.addAttribute("payAmount", content.getChargeAmount());
                }
                model.addAttribute("content", content);
            }
        }
        throw new RuntimeException("error.connect.timeout");
    }

    /**
     * 微信公众号支付(手机端)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:37
     */
    public static void weixinPayByMobile(CmsSite site, CmsUser user, String serverUrl, String ip, CmsConfigContentCharge config, String openId, Content content, String orderNum, Double rewardAmount, ModelMap model) {
        if (StringUtils.isBlank(config.getWeixinAppId()) || StringUtils.isBlank(config.getWeixinAccount())) {
            throw new RuntimeException("error.contentCharge.need.appid");
        }
        if (StringUtils.isBlank(config.getTransferApiPassword())) {
            throw new RuntimeException("error.contentCharge.need.key");
        }
        Map<String, String> map = weixinUniformOrder(site, user, "JSAPI", openId, ip, serverUrl, config, content, orderNum, rewardAmount);
        String returnCode = map.get("return_code");
        if ("FAIL".equalsIgnoreCase(returnCode)) {
            throw new RuntimeException(map.get("return_msg"));
        }
        if (map.get("err_code") != null) {
            throw new RuntimeException(map.get("err_code_des"));
        }
        if ("SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
            String prepayId = map.get("prepay_id");
            Long time = System.currentTimeMillis() / 1000;
            String nonceStr = RandomStringUtils.random(16, Num62.N10_CHARS);
            //公众号appid
            model.addAttribute("appId", config.getWeixinAppId());
            //时间戳 当前的时间 需要转换成秒
            model.addAttribute("timeStamp", time);
            //随机字符串  不长于32位
            model.addAttribute("nonceStr", nonceStr);
            //订单详情扩展字符串 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
            model.addAttribute("package", "prepay_id=" + prepayId);
            //签名方式 签名算法，暂支持MD5
            model.addAttribute("signType", "MD5");
            Map<String, String> paramMap = new HashMap<>(5);
            paramMap.put("appId", config.getWeixinAppId());
            paramMap.put("timeStamp", time.toString());
            paramMap.put("nonceStr", nonceStr);
            paramMap.put("package", "prepay_id=" + prepayId);
            paramMap.put("signType", "MD5");
            //签名
            model.addAttribute("paySign", PayUtil.createSign(paramMap, config.getTransferApiPassword()));
            model.addAttribute("orderNumber", orderNum);
            if (rewardAmount != null) {
                model.addAttribute("payAmount", rewardAmount);
            } else {
                model.addAttribute("payAmount", content.getChargeAmount());
            }
            model.addAttribute("content", content);
        }
        throw new RuntimeException("error.connect.timeout");
    }

    /**
     * 企业付款接口
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:38
     */
    public static Object[] payToUser(CmsConfigContentCharge config, File pkcFile, String serverUrl, String orderNum, String weixinOpenId, String realname, Double payAmount, String desc, String ip) {
        Map<String, String> paramMap = new HashMap<>(15);
        // 公众账号appid[必填]
        paramMap.put("mch_appid", config.getWeixinAppId());
        // 微信支付分配的商户号 [必填]
        paramMap.put("mchid", config.getWeixinAccount());
        // 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB" [非必填]
        paramMap.put("device_info", "WEB");
        // 随机字符串，不长于32位。 [必填]
        paramMap.put("nonce_str", RandomStringUtils.random(16, Num62.N62_CHARS));

        // 商户订单号,需保持唯一性[必填]
        paramMap.put("partner_trade_no", orderNum);
        // 商户appid下，某用户的openid[必填]
        paramMap.put("openid", weixinOpenId);
        //校验用户姓名选项
        paramMap.put("check_name", "OPTION_CHECK");
        //收款用户姓名,如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
        paramMap.put("re_user_name", realname);
        // 企业付款金额，金额必须为整数 单位为分 [必填]
        paramMap.put("amount", PayUtil.changeY2F(payAmount));
        // 企业付款描述信息 [必填]
        paramMap.put("desc", desc);
        // 调用接口的机器Ip地址[必填]
        paramMap.put("spbill_create_ip", ip);
        if (StringUtils.isNotBlank(config.getTransferApiPassword())) {
            // 根据微信签名规则，生成签名
            paramMap.put("sign", PayUtil.createSign(paramMap, config.getTransferApiPassword()));
        }
        // 把参数转换成XML数据格式
        String xmlWeChat = PayUtil.assembParamToXml(paramMap);
        String resXml = "";
        boolean postError = false;
        try {
            resXml = ClientCustomSSL.getInSsl(serverUrl, pkcFile, config.getWeixinAccount(), xmlWeChat, "application/xml");
        } catch (Exception e1) {
            postError = true;
            e1.printStackTrace();
        }
        Object[] result = new Object[2];
        result[0] = postError;
        result[1] = resXml;
        return result;
    }

    /**
     * 通知微信正确接收
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 13:39
     */
    public static void noticeWeChatSuccess(String weiXinPayUrl) {
        Map<String, String> parames = new HashMap<>(2);
        parames.put("return_code", "SUCCESS");
        parames.put("return_msg", "OK");
        //将参数转成xml格式
        String xmlWeChat = PayUtil.assembParamToXml(parames);
        try {
            if (StringUtils.isNotBlank(weiXinPayUrl)) {
                HttpClientUtil.post(weiXinPayUrl, xmlWeChat, Constants.UTF8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
