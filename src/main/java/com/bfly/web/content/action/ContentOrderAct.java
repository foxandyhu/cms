package com.bfly.web.content.action;


import com.alipay.api.response.AlipayTradeQueryResponse;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentBuy;
import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.cms.content.service.ContentBuyMng;
import com.bfly.cms.content.service.ContentChargeMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.funds.service.CmsUserAccountMng;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.*;
import com.bfly.common.web.*;
import com.bfly.core.config.SocialInfoConfig;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 付费内容订单
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 17:48
 */
@Controller
public class ContentOrderAct extends RenderController {

    /**
     * 支付购买（先选择支付方式，在进行支付）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 17:49
     */
    @RequestMapping(value = "/content/buy.html")
    public String contentBuy(Integer contentId, ModelMap model) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        if (content.getChargeAmount() <= 0) {
            errors.addErrorCode("error.contentChargeAmountError");
            return renderErrorPage(model, errors);
        }
        String ua = getRequest().getHeader("user-agent").toLowerCase();
        boolean webCatBrowser = false;
        String wxOpenId = null;
        if (ua.indexOf("micromessenger") > 0) {
            // 是微信浏览器
            webCatBrowser = true;
            wxOpenId = (String) getSession().getAttribute("wxopenid");
        }
        String orderNumber = System.currentTimeMillis() + RandomStringUtils.random(5, Num62.N10_CHARS);
        model.addAttribute("contentId", contentId);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("content", content);
        model.addAttribute("type", ContentCharge.MODEL_CHARGE);
        model.addAttribute("webCatBrowser", webCatBrowser);
        model.addAttribute("wxopenid", wxOpenId);
        return renderPage("special/content_reward.html", model);
    }

    /**
     * 打赏（先选择打赏金额，在选择支付方式）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/28 15:53
     */
    @RequestMapping(value = "/content/reward.html")
    public String contentReward(Integer contentId, ModelMap model) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        String ua = getRequest().getHeader("user-agent").toLowerCase();
        boolean webCatBrowser = false;
        String wxopenid = null;
        if (ua.indexOf("micromessenger") > 0) {
            // 是微信浏览器
            webCatBrowser = true;
            wxopenid = (String) getSession().getAttribute("wxopenid");
        }
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        Double max = config.getRewardMax();
        Double min = config.getRewardMin();
        List<Double> randomList = new ArrayList<>();
        Double s = 1d;
        for (int i = 0; i < 6; i++) {
            s = StrUtils.retainTwoDecimal(min + ((max - min) * new Random().nextDouble()));
            randomList.add(s);
        }
        String orderNumber = System.currentTimeMillis() + RandomStringUtils.random(5, Num62.N10_CHARS);
        model.addAttribute("contentId", contentId);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("content", content);
        model.addAttribute("type", ContentCharge.MODEL_REWARD);
        model.addAttribute("webCatBrowser", webCatBrowser);
        model.addAttribute("wxopenid", wxopenid);
        model.addAttribute("randomList", randomList);
        model.addAttribute("randomOne", s);
        return renderPage("special/content_reward.html", model);
    }

    @RequestMapping(value = "/content/fixSelect.html")
    public String contentFixSelect(Integer contentId, String orderNumber, Double rewardAmount, Short chargeReward, ModelMap model) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        String ua = getRequest().getHeader("user-agent").toLowerCase();
        boolean webCatBrowser = false;
        String wxopenid = null;
        if (ua.indexOf("micromessenger") > 0) {
            // 是微信浏览器
            webCatBrowser = true;
            wxopenid = (String) getSession().getAttribute("wxopenid");
        }
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        model.addAttribute("contentId", contentId);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("chargeReward", chargeReward);
        model.addAttribute("content", content);
        model.addAttribute("type", ContentCharge.MODEL_REWARD);
        model.addAttribute("webCatBrowser", webCatBrowser);
        model.addAttribute("wxopenid", wxopenid);
        model.addAttribute("rewardAmount", rewardAmount);
        return renderPage("special/content_reward.html", model);
    }

    /**
     * 内容购买或打赏记录
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 18:00
     */
    @RequestMapping(value = "/content/orders.html")
    public String contentOrders(Integer contentId, Short type, Integer pageNo, ModelMap model) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        if (type == null) {
            type = ContentCharge.MODEL_REWARD;
        }
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        Pagination pagination = contentBuyMng.getPageByContent(contentId, type, cpn(pageNo), CookieUtils.getPageSize(getRequest()));
        model.addAttribute("contentId", contentId);
        model.addAttribute("type", type);
        model.addAttribute("pagination", pagination);
        return renderPage("special/content_orders.html", model);
    }

    @RequestMapping(value = "/reward/random.html")
    public void randomReward(HttpServletResponse response) {
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        Double max = config.getRewardMax();
        Double min = config.getRewardMin();
        Double s = StrUtils.retainTwoDecimal(min + ((max - min) * new Random().nextDouble()));
        ResponseUtils.renderJson(response, s.toString());
    }

    /**
     * 选择支付方式
     *
     * @param contentId    内容ID
     * @param orderNumber  订单号
     * @param payMethod    支付方式 1微信扫码 2支付宝即时支付  3微信浏览器打开[微信移动端] 4支付宝扫码5支付宝手机网页
     * @param rewardAmount 金额
     * @author andy_hulibo@163.com
     * @date 2018/11/29 18:02
     */
    @RequestMapping(value = "/content/selectPay.html")
    public String selectPay(Integer contentId, String orderNumber, Integer payMethod, Double rewardAmount, Short chargeReward, ModelMap model, HttpServletResponse response) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        //收费模式金额必须大于0
        if (content.getChargeModel().equals(ContentCharge.MODEL_CHARGE) && content.getChargeAmount() <= 0) {
            errors.addErrorCode("error.contentChargeAmountError");
            return renderErrorPage(model, errors);
        }
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        //收取模式（收费 和打赏）
        if (chargeReward == null) {
            chargeReward = ContentCharge.MODEL_CHARGE;
        }
        CmsUser user = getUser();
        if (user != null) {
            cache.put(new Element(orderNumber, contentId + "," + user.getId() + "," + rewardAmount + "," + chargeReward));
        } else {
            cache.put(new Element(orderNumber, contentId + ",," + rewardAmount + "," + chargeReward));
        }
        Double totalAmount = content.getChargeAmount();
        if (rewardAmount != null) {
            totalAmount = rewardAmount;
        }
        switch (payMethod) {
            case 1:
                try {
                    WeixinPay.enterWeiXinPay(getSite(), getUser(), socialInfoConfig.getWeixin().getOrder().getPayUrl(), RequestUtils.getIpAddr(getRequest()), config, content, orderNumber, rewardAmount, model);
                    return renderPage("special/content_code_weixin.html", model);
                } catch (Exception e) {
                    return renderMessagePage(model, e.getMessage());
                }
            case 3:
                String openId = (String) getSession().getAttribute("wxopenid");
                try {
                    WeixinPay.weixinPayByMobile(getSite(), getUser(), socialInfoConfig.getWeixin().getOrder().getPayUrl(), RequestUtils.getIpAddr(getRequest()), config, openId, content, orderNumber, rewardAmount, model);
                    return renderPage("special/content_prepay.html", model);
                } catch (Exception e) {
                    return renderMessagePage(model, e.getMessage());
                }
            case 2:
                try {
                    String url = AliPay.enterAliPayImmediate(getSite(), config, orderNumber, content, rewardAmount, RequestUtils.getIpAddr(getRequest()));
                    return redirect(url);
                } catch (Exception e) {
                    return renderNotFoundPage(model);
                }
            case 4:
                try {
                    AliPay.enterAlipayScanCode(model, socialInfoConfig.getAlipay().getOpenapiUrl(), config, content, orderNumber, totalAmount);
                    return renderPage("special/content_code_alipy.html", model);
                } catch (Exception e) {
                    errors.addErrorCode(e.getMessage());
                    return renderErrorPage(model, errors);
                }
            case 5:
                model.addAttribute("orderNumber", orderNumber);
                model.addAttribute("content", content);
                model.addAttribute("type", chargeReward);
                model.addAttribute("rewardAmount", rewardAmount);
                return renderPage("special/content_alipy_mobile.html", model);
            default:
                return AliPay.enterAliPayImmediate(getSite(), config, orderNumber, content, rewardAmount, RequestUtils.getIpAddr(getRequest()));
        }
    }

    @RequestMapping(value = "/content/alipayInMobile.html")
    public String enterAlipayInMobile(Integer contentId, String orderNumber, Double rewardAmount, HttpServletResponse response, ModelMap model) throws JSONException {
        WebErrors errors = WebErrors.create(getRequest());
        if (contentId == null) {
            errors.addErrorCode("error.required", "contentId");
            return renderErrorPage(model, errors);
        }
        Content content = contentMng.findById(contentId);
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        if (content == null) {
            errors.addErrorCode("error.beanNotFound", "content");
            return renderErrorPage(model, errors);
        }
        Double totalAmount = content.getChargeAmount();
        if (rewardAmount != null) {
            totalAmount = rewardAmount;
        }
        AliPay.enterAlipayInMobile(response, socialInfoConfig.getAlipay().getOpenapiUrl(), config, content, orderNumber, totalAmount);
        return "";

    }


    /**
     * 微信回调
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 18:14
     */
    @RequestMapping(value = "/order/payCallByWeiXin.html")
    public void orderPayCallByWeiXin(String orderNumber, HttpServletResponse response) throws JDOMException, IOException, JSONException {
        JSONObject json = new JSONObject();
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        if (StringUtils.isNotBlank(orderNumber)) {
            ContentBuy order = contentBuyMng.findByOrderNumber(orderNumber);
            if (order != null && StringUtils.isNotBlank(order.getOrderNumWeiXin())) {
                //已成功支付过
                WeixinPay.noticeWeChatSuccess(socialInfoConfig.getWeixin().getOrder().getPayUrl());
                json.put("status", 4);
            } else {
                //订单未成功支付
                json.put("status", 6);
            }
        } else {
            // 回调结果
            String xmlReceiveResult = PayUtil.getWeiXinResponse(getRequest());
            if (StringUtils.isBlank(xmlReceiveResult)) {
                //检测到您可能没有进行扫码支付，请支付
                json.put("status", 5);
            } else {
                Map<String, String> resultMap = PayUtil.parseXMLToMap(xmlReceiveResult);
                String signReceive = resultMap.get("sign");
                resultMap.remove("sign");
                String key = config.getWeixinPassword();
                if (key == null) {
                    //微信扫码支付密钥错误，请通知商户
                    json.put("status", 1);
                }
                String checkSign = PayUtil.createSign(resultMap, key);
                if (checkSign != null && checkSign.equals(signReceive)) {
                    try {
                        if (resultMap != null) {
                            String return_code = resultMap.get("return_code");
                            if ("SUCCESS".equals(return_code)
                                    && "SUCCESS".equals(resultMap
                                    .get("result_code"))) {
                                // 微信返回的微信订单号（属于微信商户管理平台的订单号，跟自己的系统订单号不一样）
                                String transaction_id = resultMap
                                        .get("transaction_id");
                                // 商户系统的订单号，与请求一致。
                                String out_trade_no = resultMap.get("out_trade_no");
                                // 通知微信该订单已处理
                                WeixinPay.noticeWeChatSuccess(socialInfoConfig.getWeixin().getOrder().getPayUrl());
                                payAfter(out_trade_no, config.getChargeRatio(), transaction_id, null);
                                //支付成功
                                json.put("status", 0);
                            } else if ("SUCCESS".equals(return_code) && resultMap.get("err_code") != null) {
                                String message = resultMap.get("err_code_des");
                                json.put("status", 2);
                                json.put("error", message);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Map<String, String> parames = new HashMap<>(2);
                    parames.put("return_code", "FAIL");
                    parames.put("return_msg", "校验错误");
                    // 将参数转成xml格式
                    String xmlWeChat = PayUtil.assembParamToXml(parames);
                    try {
                        HttpClientUtil.post(socialInfoConfig.getWeixin().getOrder().getPayUrl(), xmlWeChat, Constants.UTF8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //支付参数错误，请重新支付!
                    json.put("status", 3);
                }
            }
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 支付宝即时支付回调地址
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 18:15
     */
    @RequestMapping(value = "/order/payCallByAliPay.html")
    public String payCallByAliPay(HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>(5);
        Map requestParams = getRequest().getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        //商户订单号
        String out_trade_no = getRequest().getParameter("out_trade_no");
        //支付宝交易号
        String trade_no = getRequest().getParameter("trade_no");
        //交易状态
        String trade_status = getRequest().getParameter("trade_status");
        //验证成功
        if (PayUtil.verifyAliPay(params, config.getAlipayPartnerId(), config.getAlipayKey())) {
            if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                Content content = payAfter(out_trade_no, config.getChargeRatio(), null, trade_no);
                try {
                    response.sendRedirect(content.getUrl());
                } catch (IOException e) {
                }
                return content.getUrl();
                //注意：TRADE_FINISHED
                //该种交易状态只在两种情况下出现
                //1、开通了普通即时到账，买家付款成功后。
                //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                //TRADE_SUCCESS
                //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
            }
        }
        //验证失败
        return renderMessagePage(model, "支付宝验证失败");
    }

    /**
     * 支付宝查询订单状态（扫码支付和手机网页支付均由此处理订单）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 18:17
     */
    @RequestMapping(value = "/content/orderQuery.html")
    public void aliPayOrderQuery(String orderNumber, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        CmsConfigContentCharge config = configContentChargeMng.getDefault();
        JSONObject json = new JSONObject();
        AlipayTradeQueryResponse res = AliPay.query(socialInfoConfig.getAlipay().getOpenapiUrl(), config, orderNumber, null);
        try {
            if (null != res && res.isSuccess()) {
                if ("10000".equals(res.getCode())) {
                    if ("TRADE_SUCCESS".equalsIgnoreCase(res.getTradeStatus())) {
                        json.put("status", 0);
                        payAfter(orderNumber, config.getChargeRatio(), null, res.getTradeNo());
                    } else if ("WAIT_BUYER_PAY".equalsIgnoreCase(res.getTradeStatus())) {
                        // 等待用户付款状态，需要轮询查询用户的付款结果
                        json.put("status", 1);
                    } else if ("TRADE_CLOSED".equalsIgnoreCase(res.getTradeStatus())) {
                        // 表示未付款关闭，或已付款的订单全额退款后关闭
                        json.put("status", 2);
                    } else if ("TRADE_FINISHED".equalsIgnoreCase(res.getTradeStatus())) {
                        // 此状态，订单不可退款或撤销
                        json.put("status", 0);
                    }
                } else {
                    // 如果请求未成功，请重试
                    json.put("status", 3);
                }
            } else {
                json.put("status", 4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    private Content payAfter(String orderNumber, Double ratio, String weixinOrderNum, String alipyOrderNum) {
        Element e = cache.get(orderNumber);
        Content content = null;
        if (e != null && StringUtils.isNotBlank(orderNumber)) {
            ContentBuy b = contentBuyMng.findByOrderNumber(orderNumber);
            //不能重复提交
            if (b == null) {
                Object obj = e.getObjectValue();
                String[] objArray = new String[4];
                if (obj != null) {
                    objArray = obj.toString().split(",");
                }
                Double rewardAmount = null;
                Integer contentId = null;
                Integer buyUserId = null;
                Short chargeReward = ContentCharge.MODEL_REWARD;
                if (objArray != null && objArray[0] != null) {
                    contentId = Integer.parseInt(objArray[0]);
                }
                if (objArray != null && objArray[1] != null && StringUtils.isNotBlank(objArray[1])) {
                    buyUserId = Integer.parseInt(objArray[1]);
                }
                if (objArray != null && objArray[2] != null && StringUtils.isNotBlank(objArray[2])
                        && !"null".equals(objArray[2].toLowerCase())) {
                    rewardAmount = Double.parseDouble(objArray[2]);
                }
                if (objArray != null && objArray[3] != null) {
                    chargeReward = Short.valueOf(objArray[3]);
                }
                ContentBuy contentBuy = new ContentBuy();
                if (contentId != null) {
                    content = contentMng.findById(contentId);
                    contentBuy.setAuthorUser(content.getUser());
                    //打赏可以匿名
                    if (buyUserId != null) {
                        contentBuy.setBuyUser(userMng.findById(buyUserId));
                    }
                    contentBuy.setContent(content);
                    contentBuy.setHasPaidAuthor(false);
                    contentBuy.setOrderNumber(orderNumber);
                    contentBuy.setBuyTime(Calendar.getInstance().getTime());
                    Double chargeAmount = content.getChargeAmount();
                    Double platAmount = content.getChargeAmount() * ratio;
                    Double authorAmount = content.getChargeAmount() * (1 - ratio);
                    if (rewardAmount != null) {
                        chargeAmount = rewardAmount;
                        platAmount = rewardAmount * ratio;
                        authorAmount = rewardAmount * (1 - ratio);
                    }
                    if (chargeReward.equals(ContentCharge.MODEL_REWARD)) {
                        contentBuy.setChargeReward(ContentCharge.MODEL_REWARD);
                    } else {
                        contentBuy.setChargeReward(ContentCharge.MODEL_CHARGE);
                    }
                    contentBuy.setChargeAmount(chargeAmount);
                    contentBuy.setPlatAmount(platAmount);
                    contentBuy.setAuthorAmount(authorAmount);
                    // 这里是把微信商户的订单号放入了交易号中
                    contentBuy.setOrderNumWeiXin(weixinOrderNum);
                    contentBuy.setOrderNumAliPay(alipyOrderNum);
                    contentBuy = contentBuyMng.save(contentBuy);
                    CmsUser authorUser = contentBuy.getAuthorUser();
                    //笔者所得统计
                    userAccountMng.userPay(contentBuy.getAuthorAmount(), authorUser);
                    //平台所得统计
                    configContentChargeMng.afterUserPay(contentBuy.getPlatAmount());
                    //内容所得统计
                    contentChargeMng.afterUserPay(contentBuy.getChargeAmount(), contentBuy.getContent());
                }
            }
        }
        return content;
    }


    @Autowired
    private ContentMng contentMng;
    @Autowired
    private ContentChargeMng contentChargeMng;
    @Autowired
    private ContentBuyMng contentBuyMng;
    @Autowired
    private CmsConfigContentChargeMng configContentChargeMng;
    @Autowired
    private CmsUserAccountMng userAccountMng;
    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private SocialInfoConfig socialInfoConfig;
    private Ehcache cache;

    @Autowired
    public void setCache(EhCacheCacheManager cacheManager) {
        EhCacheCache ehcache = (EhCacheCache) cacheManager.getCache("contentOrderTemp");
        cache = ehcache.getNativeCache();
    }
}

