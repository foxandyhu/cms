package com.bfly.cms.system.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置属性
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 16:45
 */
public class SysConfigAttr {

    public SysConfigAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    private Map<String, String> attr;

    public Map<String, String> getAttr() {
        if (attr == null) {
            attr = new HashMap<>(15);
        }
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }


    public static final String PICTURENEW = "new_picture";
    public static final String DAYNEW = "day";
    public static final String PREVIEW = "preview";
    public static final String QQ_ENABLE = "qqEnable";
    public static final String QQ_ID = "qqID";
    public static final String QQ_KEY = "qqKey";
    public static final String SINA_ENABLE = "sinaEnable";
    public static final String SINA_ID = "sinaID";
    public static final String SINA_KEY = "sinaKey";
    public static final String QQWEBO_ENABLE = "qqWeboEnable";
    public static final String QQWEBO_ID = "qqWeboID";
    public static final String QQWEBO_KEY = "qqWeboKey";
    public static final String WEIXIN_ENABLE = "weixinEnable";
    public static final String WEIXIN_ID = "weixinID";
    public static final String WEIXIN_KEY = "weixinKey";
    public static final String SSO_ENABLE = "ssoEnable";
    public static final String FLOW_SWITCH = "flowSwitch";
    public static final String CONTENT_FRESH_MINUTE = "contentFreshMinute";
    public static final String CODE_IMG_WIDTH = "codeImgWidth";
    public static final String CODE_IMG_HEIGHT = "codeImgHeight";

    public static final String WEIXIN_APP_ID = "weixinAppId";
    public static final String WEIXIN_APP_SECRET = "weixinAppSecret";
    public static final String WEIXIN_LOGIN_ID = "weixinLoginId";
    public static final String WEIXIN_LOGIN_SECRET = "weixinLoginSecret";

    public static final String COMMENT_OPEN = "commentOpen";
    public static final String GUESTBOOK_OPEN = "guestbookOpen";
    public static final String GUESTBOOK_NEED_LOGIN = "guestbookNeedLogin";
    public static final String GUESTBOOK_DAY_LIMIT = "guestbookDayLimit";
    public static final String COMMENT_DAY_LIMIT = "commentDayLimit";

}
