//package com.bfly.web.weixin.action;
//
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.cms.weixin.entity.WeixinMenu;
//import com.bfly.cms.weixin.service.WeixinMessage;
//import com.bfly.cms.weixin.service.WeixinMessageMng;
//import com.bfly.cms.weixin.service.WeixinMng;
//import com.bfly.common.page.Pagination;
//import com.bfly.common.web.CookieUtils;
//import com.bfly.core.web.WebErrors;
//import com.bfly.core.web.util.CmsUtils;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//import static com.bfly.common.page.SimplePage.cpn;
//
//@Controller
//public class WeixinMessageAct {
//
//    private static final Logger log = LoggerFactory.getLogger(WeixinMenuAct.class);
//
//    @RequiresPermissions("weixinMessage:v_list")
//    @RequestMapping("/weixinMessage/v_list.html")
//    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
//        Site site = CmsUtils.getSite(request);
//        Pagination p = manager.getPage(site.getId(), cpn(pageNo), CookieUtils.getPageSize(request));
//
//        model.addAttribute("pagination", p);
//        model.addAttribute("pageNo", pageNo);
//        return "weixinMessage/list";
//    }
//
//    @RequiresPermissions("weixinMessage:v_default_set")
//    @RequestMapping("/weixinMessage/v_default_set.html")
//    public String setDefault(HttpServletRequest request, ModelMap model) {
//        WeixinMessage defaultMsg = manager.getWelcome(CmsUtils.getSiteId(request));
//        model.addAttribute("sessionId", request.getSession().getId());
//        if (defaultMsg == null) {
//            return "weixinMessage/adddefault";
//        } else {
//            model.addAttribute("menu", defaultMsg);
//            return "weixinMessage/editdefault";
//        }
//    }
//
//    @RequiresPermissions("weixinMessage:o_default_save")
//    @RequestMapping("/weixinMessage/o_default_save.html")
//    public String saveDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) {
//        bean.setSite(CmsUtils.getSite(request));
//        bean.setWelcome(true);
//        manager.save(bean);
//        return setDefault(request, model);
//    }
//
//    @RequiresPermissions("weixinMessage:o_default_update")
//    @RequestMapping("/weixinMessage/o_default_update.html")
//    public String updateDefault(WeixinMessage bean, HttpServletRequest request, ModelMap model) {
//        manager.update(bean);
//        return setDefault(request, model);
//    }
//
//    @RequiresPermissions("weixinMessage:v_add")
//    @RequestMapping("/weixinMessage/v_add.html")
//    public String add(Integer pageNo, HttpServletRequest request, ModelMap model) {
//        model.addAttribute("sessionId", request.getSession().getId());
//        model.addAttribute("pageNo", pageNo);
//        return "weixinMessage/ADD";
//    }
//
//    @RequiresPermissions("weixinMessage:v_edit")
//    @RequestMapping("/weixinMessage/v_edit.html")
//    public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
//        WeixinMessage entity = manager.findById(id);
//        model.addAttribute("pageNo", pageNo);
//        model.addAttribute("menu", entity);
//        model.addAttribute("sessionId", request.getSession().getId());
//        return "weixinMessage/EDIT";
//    }
//
//    @RequiresPermissions("weixinMessage:o_save")
//    @RequestMapping("/weixinMessage/o_save.html")
//    public String save(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
//        Site site = CmsUtils.getSite(request);
//        bean.setSite(site);
//        bean.setWelcome(false);
//        bean.setType(0);
//        manager.save(bean);
//        return list(pageNo, request, model);
//    }
//
//    @RequiresPermissions("weixinMessage:o_update")
//    @RequestMapping("/weixinMessage/o_update.html")
//    public String update(WeixinMessage bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
//        manager.update(bean);
//        return list(pageNo, request, model);
//    }
//
//
//    @RequiresPermissions("weixinMessage:o_delete")
//    @RequestMapping("/weixinMessage/o_delete.html")
//    public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
//        WebErrors errors = validateDelete(ids, request);
//        if (errors.hasErrors()) {
//            return errors.showErrorPage(model);
//        }
//        WeixinMessage[] beans = manager.deleteByIds(ids);
//        for (WeixinMessage bean : beans) {
//            log.info("delete WeixinMessage id={}", bean.getId());
//        }
//        return list(pageNo, request, model);
//    }
//
//    private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
//        WebErrors errors = WebErrors.create(request);
//        if (errors.ifEmpty(ids, "ids", true)) {
//            return errors;
//        }
//        for (Integer id : ids) {
//            vldExist(id, errors);
//        }
//        return errors;
//    }
//
//    private boolean vldExist(Integer id, WebErrors errors) {
//        if (errors.ifNull(id, "id", true)) {
//            return true;
//        }
//        WeixinMessage entity = manager.findById(id);
//        return errors.ifNotExist(entity, WeixinMenu.class, id, true);
//    }
//
//    /**
//     * 微信开发者验证URL
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/30 17:55
//     */
//    @RequestMapping(value = "/sendMessage.html")
//    public void weixin(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        //开发者验证填写TOKEN值
//        Site site = CmsUtils.getSite(request);
//        String token = site.getWxToken();
//        Object[] tmpArr = new Object[]{token, timestamp, nonce};
//        Arrays.sort(tmpArr);
//        String str = tmpArr[0].toString() + tmpArr[1].toString() + tmpArr[2].toString();
//        String tmpStr = DigestUtils.sha1Hex(str);
//        if (tmpStr.equals(signature)) {
//            // 调用核心业务类接收消息、处理消息
//            processRequest(echostr, request, response);
//        } else {
//            log.error("false");
//        }
//
//    }
//
//
//    private void processRequest(String echostr, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("UTF-8");
//        String postStr = readStreamParameter(request.getInputStream());
//        Document document = null;
//        try {
//            if (StringUtils.isNotBlank(postStr)) {
//                document = DocumentHelper.parseText(postStr);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (null == document) {
//            response.getWriter().write(echostr);
//            return;
//        }
//        Site site = CmsUtils.getSite(request);
//        Element root = document.getRootElement();
//        String fromUsername = root.elementText("FromUserName");
//        String toUsername = root.elementText("ToUserName");
//        String userMsgType = root.elementText("MsgType");
//
//        String keyword = root.elementTextTrim("Content");
//        String time = System.currentTimeMillis() + "";
//
//        // 默认返回的文本消息内容
//        String respContent;
//        String welcome = weixinMng.find(CmsUtils.getSiteId(request)).getWelcome();
//        if ("event".equals(userMsgType)) {
//            // 事件类型
//            String eventType = root.elementText("Event");
//            // 订阅
//            if ("subscribe".equals(eventType)) {
//                respContent = welcome;
//                respContent = text(respContent, fromUsername, toUsername, time);
//                send(respContent, response);
//                return;
//            }
//            // 取消订阅
//            if ("unsubscribe".equals(eventType)) {
//                return;
//            }
//            // 自定义菜单点击事件
//            // 事件KEY值，与创建自定义菜单时指定的KEY值对应
//            String eventKey = root.elementText("EventKey");
//            //返回自定义回复的定义
//            autoReply(eventKey, fromUsername, toUsername, time, site, request, response);
//            return;
//        }
//        //回复内容
//        if (keyword != null) {
//            keyword = keyword.trim();
//        }
//        if (keyword != null && "text".equals(userMsgType)) {
//            autoReply(keyword, fromUsername, toUsername, time, site, request, response);
//        }
//        return;
//    }
//
//    private void autoReply(String keyword, String fromUsername, String toUsername, String time, Site site, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        WeixinMessage entity = manager.findByNumber(keyword, site.getId());
//        if (entity != null) {
//            String text = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
//            send(text, response);
//        } else {
//            entity = manager.getWelcome(site.getId());
//            if (entity != null) {
//                StringBuffer buffer = new StringBuffer();
//                String textTpl = "";
//                //内容+关键字 标题 提示
//                if (entity.getType().equals(WeixinMessage.CONTENT_WITH_KEY)) {
//                    buffer.append(entity.getContent()).append("\n");
//                    List<WeixinMessage> lists = manager.getList(site.getId());
//                    for (WeixinMessage msg : lists) {
//                        buffer.append("  【" + msg.getNumber() + "】" + msg.getTitle()).append("\n");
//                    }
//                    textTpl = text(buffer.toString(), fromUsername, toUsername, time);
//                } else if (entity.getType().equals(WeixinMessage.CONTENT_ONLY)) {
//                    //仅限内容
//                    buffer.append(entity.getContent()).append("\n");
//                    textTpl = text(buffer.toString(), fromUsername, toUsername, time);
//                } else if (entity.getType().equals(WeixinMessage.CONTENT_WITH_IMG)) {
//                    //图文类型（图片 标题 文字 链接组成）
//                    textTpl = contentWithImgUseMessage(entity, fromUsername, toUsername, time, request);
//                }
//                send(textTpl, response);
//            }
//        }
//    }
//
//    private String readStreamParameter(ServletInputStream in) {
//        StringBuilder buffer = new StringBuilder();
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != reader) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return buffer.toString();
//    }
//
//    private String contentWithImgUseMessage(WeixinMessage entity, String fromUsername, String toUsername, String time, HttpServletRequest request) {
//        Site site = CmsUtils.getSite(request);
//        String path = site.getDomain();
//        return text(fromUsername, toUsername, time, entity.getTitle(), entity.getContent(), "http://" + path + entity.getPath(), entity.getUrl());
//    }
//
//
//    private String text(String fromUsername, String toUsername, String time, String title, String desc, String img, String url) {
//        return "<xml>" +
//                "<ToUserName><![CDATA[" + fromUsername + "]]></ToUserName>" +
//                "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" +
//                "<CreateTime>" + time + "</CreateTime>" +
//                "<MsgType><![CDATA[news]]></MsgType>" +
//                "<ArticleCount>1</ArticleCount>" +
//                "<Articles>" +
//                "<item>" +
//                "<Title><![CDATA[" + title + "]]></Title>" +
//                "<Description><![CDATA[" + desc + "]]></Description>" +
//                "<PicUrl><![CDATA[" + img + "]]></PicUrl>" +
//                "<Url><![CDATA[" + url + "]]></Url>" +
//                "</item>" +
//                "</Articles>" +
//                "</xml>";
//    }
//
//    private String text(String str, String fromUsername, String toUsername, String time) {
//        return "<xml>" +
//                "<ToUserName><![CDATA[" + fromUsername + "]]></ToUserName>" +
//                "<FromUserName><![CDATA[" + toUsername + "]]></FromUserName>" +
//                "<CreateTime>" + time + "</CreateTime>" +
//                "<MsgType><![CDATA[text]]></MsgType>" +
//                "<Content><![CDATA[" + str + "]]></Content>" +
//                "</xml>";
//    }
//
//    private void send(String textTpl, HttpServletResponse response) throws IOException {
//        String type = "text/xml;charset=UTF-8";
//        response.setContentType(type);
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.getWriter().write(textTpl);
//    }
//
//    @Autowired
//    private WeixinMessageMng manager;
//    @Autowired
//    private WeixinMng weixinMng;
//}
