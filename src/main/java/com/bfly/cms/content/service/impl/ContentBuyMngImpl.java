package com.bfly.cms.content.service.impl;


import com.alipay.api.response.AlipayTradeQueryResponse;
import com.bfly.cms.content.dao.ContentBuyDao;
import com.bfly.cms.system.entity.CmsConfigContentCharge;
import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentBuy;
import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.cms.system.service.CmsConfigContentChargeMng;
import com.bfly.cms.content.service.ContentBuyMng;
import com.bfly.cms.content.service.ContentChargeMng;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.common.util.AliPay;
import com.bfly.common.util.Num62;
import com.bfly.common.util.WeixinPay;
import com.bfly.core.config.SocialInfoConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.funds.service.CmsUserAccountMng;
import com.bfly.cms.user.service.CmsUserMng;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentBuyMngImpl implements ContentBuyMng {

    @Override
    public ContentBuy contentOrder(Integer contentId, Integer orderType,
                                   Short chargeReward, Integer buyUserId, String outOrderNum) {
        ContentBuy contentBuy = new ContentBuy();
        if (contentId != null) {
            Content content = contentMng.findById(contentId);
            if (content != null) {
                //外部订单号和内部订单号要一一对应，否则会出现一个外部订单可以用于形成多个内部订单
                //内容收费模式且本次订单是购买流程
                boolean buy = false;
                if (content.getCharge() && chargeReward.equals(ContentCharge.MODEL_CHARGE)) {
                    buy = true;
                }
                CmsConfigContentCharge config = configContentChargeMng.getDefault();
                Double orderAmount = 0d;
                // 这里是把微信商户的订单号放入了交易号中
                if (orderType.equals(ContentBuy.PAY_METHOD_WECHAT)) {
                    contentBuy.setOrderNumWeiXin(outOrderNum);
                    orderAmount = getWeChatOrderAmount(outOrderNum, config);
                } else if (orderType.equals(ContentBuy.PAY_METHOD_ALIPAY)) {
                    contentBuy.setOrderNumAliPay(outOrderNum);
                    orderAmount = getAliPayOrderAmount(outOrderNum, config);
                }
                //订单金额不能等于0
                if (!orderAmount.equals(0d)) {
                    //购买行为订单金额需要大于内容收费金额 或者是打赏新闻
                    if ((buy && orderAmount >= content.getChargeAmount()) || !buy) {
                        Double ratio = config.getChargeRatio();
                        contentBuy.setAuthorUser(content.getUser());
                        //打赏可以匿名
                        if (buyUserId != null) {
                            contentBuy.setBuyUser(userMng.findById(buyUserId));
                        }
                        contentBuy.setContent(content);
                        contentBuy.setHasPaidAuthor(false);
                        String orderNumber = System.currentTimeMillis() + RandomStringUtils.random(5, Num62.N10_CHARS);
                        contentBuy.setOrderNumber(orderNumber);
                        contentBuy.setBuyTime(Calendar.getInstance().getTime());
                        Double chargeAmount = content.getChargeAmount();
                        Double platAmount = content.getChargeAmount() * ratio;
                        Double authorAmount = content.getChargeAmount() * (1 - ratio);
                        if (orderAmount != null) {
                            chargeAmount = orderAmount;
                            platAmount = orderAmount * ratio;
                            authorAmount = orderAmount * (1 - ratio);
                        }
                        if (chargeReward.equals(ContentCharge.MODEL_REWARD)) {
                            contentBuy.setChargeReward(ContentCharge.MODEL_REWARD);
                        } else {
                            contentBuy.setChargeReward(ContentCharge.MODEL_CHARGE);
                        }
                        contentBuy.setChargeAmount(chargeAmount);
                        contentBuy.setPlatAmount(platAmount);
                        contentBuy.setAuthorAmount(authorAmount);
                        contentBuy = save(contentBuy);
                        CmsUser authorUser = contentBuy.getAuthorUser();
                        //笔者所得统计
                        userAccountMng.userPay(contentBuy.getAuthorAmount(), authorUser);
                        //平台所得统计
                        configContentChargeMng.afterUserPay(contentBuy.getPlatAmount());
                        //内容所得统计
                        contentChargeMng.afterUserPay(contentBuy.getChargeAmount(), contentBuy.getContent());
                        contentBuy.setPrePayStatus(ContentBuy.PRE_PAY_STATUS_SUCCESS);
                    } else {
                        contentBuy.setPrePayStatus(ContentBuy.PRE_PAY_STATUS_SUCCESS);
                    }
                } else {
                    contentBuy.setPrePayStatus(ContentBuy.PRE_PAY_STATUS_ORDER_NUM_ERROR);
                }
            }
        }
        return contentBuy;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getPage(String orderNum, Integer buyUserId, Integer authorUserId,
                              Short payMode, int pageNo, int pageSize) {
        Pagination page = dao.getPage(orderNum, buyUserId,
                authorUserId, payMode, pageNo, pageSize);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBuy> getList(String orderNum, Integer buyUserId,
                                    Integer authorUserId, Short payMode, Integer first, Integer count) {
        return dao.getList(orderNum, buyUserId, authorUserId,
                payMode, first, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination getPageByContent(Integer contentId,
                                       Short payMode, int pageNo, int pageSize) {
        return dao.getPageByContent(contentId, payMode, pageNo, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBuy> getListByContent(Integer contentId, Short payMode, Integer first, Integer count) {
        return dao.getListByContent(contentId, payMode, first, count);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentBuy findById(Long id) {
        ContentBuy entity = dao.findById(id);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public ContentBuy findByOrderNumber(String orderNumber) {
        return dao.findByOrderNumber(orderNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentBuy findByOutOrderNum(String orderNum, Integer payMethod) {
        return dao.findByOutOrderNum(orderNum, payMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasBuyContent(Integer buyUserId, Integer contentId) {
        ContentBuy buy = dao.find(buyUserId, contentId);
        //用户已经购买并且是收费订单非打赏订单
        if (buy != null && buy.getUserHasPaid() && ContentCharge.MODEL_CHARGE.equals(buy.getChargeReward())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ContentBuy save(ContentBuy bean) {
        dao.save(bean);
        return bean;
    }

    @Override
    public ContentBuy update(ContentBuy bean) {
        Updater<ContentBuy> updater = new Updater<>(bean);
        bean = dao.updateByUpdater(updater);
        return bean;
    }

    @Override
    public ContentBuy deleteById(Long id) {
        ContentBuy bean = dao.deleteById(id);
        return bean;
    }

    @Override
    public ContentBuy[] deleteByIds(Long[] ids) {
        ContentBuy[] beans = new ContentBuy[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    private Double getWeChatOrderAmount(String outOrderNum,
                                        CmsConfigContentCharge config) {
        Map<String, String> map = WeixinPay.weixinOrderQuery(outOrderNum,
                null, socialInfoConfig.getWeixin().getOrder().getPayUrl(), config);
        String returnCode = map.get("return_code");
        Double orderAmount = 0d;
        if (StringUtils.isNotBlank(returnCode)) {
            if ("SUCCESS".equalsIgnoreCase(returnCode)) {
                if ("SUCCESS".equalsIgnoreCase(map.get("result_code"))) {
                    String trade_state = map.get("trade_state");
                    //支付成功
                    if ("SUCCESS".equalsIgnoreCase(trade_state)) {
                        String total_fee = map.get("total_fee");
                        Integer totalFee = Integer.parseInt(total_fee);
                        if (totalFee != 0) {
                            orderAmount = totalFee / 100.0;
                        }
                    }
                }
            }
        }
        return orderAmount;
    }

    private Double getAliPayOrderAmount(String outOrderNum,
                                        CmsConfigContentCharge config) {
        AlipayTradeQueryResponse res = AliPay.query(socialInfoConfig.getAlipay().getOpenapiUrl(), config,
                null, outOrderNum);
        Double orderAmount = 0d;
        if (null != res && res.isSuccess()) {
            if ("10000".equals(res.getCode())) {
                if ("TRADE_SUCCESS".equalsIgnoreCase(res
                        .getTradeStatus())) {
                    String totalAmout = res.getTotalAmount();
                    if (StringUtils.isNotBlank(totalAmout)) {
                        orderAmount = Double.parseDouble(totalAmout);
                    }
                }
            }
        }
        return orderAmount;
    }

    @Autowired
    private ContentBuyDao dao;
    @Autowired
    private ContentMng contentMng;
    @Autowired
    private ContentChargeMng contentChargeMng;
    @Autowired
    private CmsConfigContentChargeMng configContentChargeMng;
    @Autowired
    private CmsUserAccountMng userAccountMng;
    @Autowired
    private CmsUserMng userMng;
    @Autowired
    private SocialInfoConfig socialInfoConfig;
}