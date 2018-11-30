package com.bfly.common.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付宝支付
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 14:41
 */
public class AliPay {

    private static final Logger log = LoggerFactory.getLogger(AliPay.class);

    /**
     * 支付宝当面付(扫码付款)
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:41
     */
    public static void enterAlipayScanCode(ModelMap model, String serverUrl, CmsConfigContentCharge config, Content content, String outTradeNo, Double totalAmount) {
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(serverUrl, config.getAlipayAppId(), config.getAlipayPrivateKey(), config.getAlipayPublicKey(), "utf-8");
        AlipayTradePrecreateRequest aliRequest = new AlipayTradePrecreateRequest();
        aliRequest.setBizContent("{" +
                //商户订单号
                "    \"out_trade_no\":\"" + outTradeNo + "\"," +
                //卖家支付宝用户 ID
                "    \"seller_id\":\"" + config.getAlipayPartnerId() + "\"," +
                //订单标题
                "    \"subject\":\"" + content.getTitle() + "\"," +
                //订单总金额
                "    \"total_amount\":" + totalAmount + "," +
                //支付超时时间
                "    \"timeout_express\":\"90m\"" +
                "  }");
        //设置回转地址
        aliRequest.setReturnUrl(content.getUrlWhole());
        AlipayTradePrecreateResponse aliResponse = null;
        try {
            aliResponse = alipayClient.execute(aliRequest);
        } catch (AlipayApiException e) {
            log.error(e.getErrMsg());
            e.printStackTrace();
        }
        if (aliResponse != null && aliResponse.isSuccess()) {
            if ("10000".equals(aliResponse.getCode())) {
                //"支付宝预下单成功
                model.addAttribute("code_url", aliResponse.getQrCode());
                model.addAttribute("orderNumber", outTradeNo);
                model.addAttribute("payAmount", totalAmount);
                model.addAttribute("content", content);
            }
            throw new RuntimeException(aliResponse.getMsg());
        }
        throw new RuntimeException("error.alipay.params.fail");
    }

    /**
     * 手机网站快速支付
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:40
     */
    public static void enterAlipayInMobile(HttpServletResponse response, String serverUrl, CmsConfigContentCharge config, Content content, String outTradeNo, Double totalAmount) {
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(
                serverUrl, config.getAlipayAppId()
                , config.getAlipayPrivateKey(), config.getAlipayPublicKey(), "UTF-8");
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //在公共参数中设置回跳地址
        alipayRequest.setReturnUrl(content.getUrlWhole());
        alipayRequest.setBizContent("{" +
                //商户订单号
                "    \"out_trade_no\":\"" + outTradeNo + "\"," +
                //卖家支付宝用户 ID
                "    \"seller_id\":\"" + config.getAlipayPartnerId() + "\"," +
                //订单标题
                "    \"subject\":\"" + content.getTitle() + "\"," +
                //订单总金额
                "    \"total_amount\":" + totalAmount + "," +
                //支付超时时间
                "    \"timeout_express\":\"90m\"" +
                "  }");
        String form;
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=UTF-8");
            //直接将完整的表单html输出到页面
            response.getWriter().write(form);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝在线支付订单
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:27
     */
    public static String enterAliPayImmediate(CmsSite site, CmsConfigContentCharge config, String orderNumber, Content content, Double rewardAmount, String ip) {
        if (orderNumber != null) {
            throw new RuntimeException("orderNumber is null");
        }
        if (content != null) {
            throw new RuntimeException("content is null");
        }
        return alipayImmediate(config, site, orderNumber, content, rewardAmount, ip);
    }

    /**
     * 支付宝即时到账（支付宝不推荐）
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/30 14:25
     */
    private static String alipayImmediate(CmsConfigContentCharge config, CmsSite site, String orderNumber, Content content, Double rewardAmount, String ip) {
        //支付类型 必填，不能修改
        String paymentType = "1";
        //服务器异步通知页面路径
        String notifyUrl = "http://" + site.getDomain() + "/order/payCallByAliPay.html";
        //页面跳转同步通知页面路径
        String returnUrl = content.getUrlWhole();
        //卖家支付宝帐户必填
        String sellerEmail = config.getAlipayAccount();
        //商户订单号商户网站订单系统中唯一订单号，必填
        String outTradeNo = orderNumber;
        //订单名称必填
        String subject = "(" + content.getTitle() + ")";
        //付款金额
        Double payAmount = content.getChargeAmount();
        if (rewardAmount != null) {
            payAmount = rewardAmount;
        }
        //必填
        String totalFee = String.valueOf(payAmount);
        //订单描述
        String body = "(" + content.getTitle() + ")";
        //商品展示地址
        String showUrl = "http://" + content.getUrl() + "/";
        //防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
        String antiPhishingKey = "";
        //客户端的IP地址非局域网的外网IP地址，如：221.0.0.1
        Map<String, String> sParaTemp = new HashMap<>(14);
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", config.getAlipayPartnerId());
        sParaTemp.put("_input_charset", "utf-8");
        sParaTemp.put("payment_type", paymentType);
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("return_url", returnUrl);
        sParaTemp.put("seller_email", sellerEmail);
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", totalFee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", showUrl);
        sParaTemp.put("anti_phishing_key", antiPhishingKey);
        sParaTemp.put("exter_invoke_ip", ip);
        //建立请求
        return PayUtil.buildAliPayRequest(sParaTemp, config.getAlipayKey(), "get", "确认");
    }


    /**
     * 交易查询
     *
     * @param outTradeNo 业务系统订单号
     * @param tradeNo     支付宝交易订单号
     */
    public static AlipayTradeQueryResponse query(String serverUrl, CmsConfigContentCharge config, final String outTradeNo, final String tradeNo) {
        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(serverUrl, config.getAlipayAppId(), config.getAlipayPrivateKey(), config.getAlipayPublicKey(), "UTF-8");
        AlipayTradeQueryRequest alipayQueryRequest = new AlipayTradeQueryRequest();
        String biz_content = "";
        if (StringUtils.isNotBlank(outTradeNo)) {
            //商户订单号
            biz_content = "{\"out_trade_no\":\"" + outTradeNo + "\"}";
        } else if (StringUtils.isNotBlank(tradeNo)) {
            //支付宝交易订单号
            biz_content = "{\"trade_no\":\"" + tradeNo + "\"}";
        }

        alipayQueryRequest.setBizContent(biz_content);
        AlipayTradeQueryResponse alipayQueryResponse = null;
        try {
            alipayQueryResponse = alipayClient.execute(alipayQueryRequest);

            if (null != alipayQueryResponse && alipayQueryResponse.isSuccess()) {
                if ("10000".equals(alipayQueryResponse.getCode())) {
                    if ("TRADE_SUCCESS".equalsIgnoreCase(alipayQueryResponse
                            .getTradeStatus())) {

                        List<TradeFundBill> fund_bill_list = alipayQueryResponse
                                .getFundBillList();
                        if (null != fund_bill_list) {
                            doFundBillList(fund_bill_list);
                        }
                    } else if ("TRADE_CLOSED".equalsIgnoreCase(alipayQueryResponse
                            .getTradeStatus())) {
                        // 表示未付款关闭，或已付款的订单全额退款后关闭
                    } else if ("TRADE_FINISHED".equalsIgnoreCase(alipayQueryResponse
                            .getTradeStatus())) {
                        // 此状态，订单不可退款或撤销
                    }
                } else {
                    // 如果请求未成功，请重试

                }
            }
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return alipayQueryResponse;
    }

    private static void doFundBillList(List<TradeFundBill> fundBillList) {
        // 根据付款的资金渠道，来决定哪些是商户优惠，哪些是支付宝优惠。 对账时要注意商户优惠部分
        for (TradeFundBill tfb : fundBillList) {
            System.out.println("付款资金渠道：" + tfb.getFundChannel() + " 付款金额：" + tfb.getAmount());
        }
    }

    private String serverUrl;
    private CmsConfigContentCharge config;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public CmsConfigContentCharge getConfig() {
        return config;
    }

    public void setConfig(CmsConfigContentCharge config) {
        this.config = config;
    }

}
