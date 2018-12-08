//package com.bfly.web;
//
//import com.bfly.cms.channel.entity.Channel;
//import com.bfly.cms.channel.service.ChannelMng;
//import com.bfly.cms.content.entity.Content;
//import com.bfly.cms.content.entity.ContentCheck;
//import com.bfly.cms.content.service.ContentBuyMng;
//import com.bfly.cms.content.service.ContentMng;
//import com.bfly.cms.siteconfig.entity.Site;
//import com.bfly.cms.system.entity.SysConfig;
//import com.bfly.cms.member.entity.MemberGroup;
//import com.bfly.cms.member.entity.Member;
//import com.bfly.cms.words.service.CmsKeywordMng;
//import com.bfly.common.page.Paginable;
//import com.bfly.common.page.SimplePage;
//import com.bfly.common.web.springmvc.RealPathResolver;
//import com.bfly.core.annotation.Token;
//import com.bfly.core.base.action.RenderController;
//import com.bfly.core.web.util.FrontUtils;
//import com.bfly.core.web.util.URLHelper;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.util.Set;
//
//import static com.bfly.common.web.Constants.INDEX;
//import static com.bfly.common.web.Constants.INDEX_HTML;
//
///**
// * 网站首页Controller
// *
// * @author andy_hulibo@163.com
// * @date 2018/11/29 10:29
// */
//@Controller
//public class IndexController extends RenderController {
//    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
//
//    /**
//     * 站点首页
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/21 15:20
//     */
//    @GetMapping(value = {"/", "/index.html"})
//    @Token(save = true)
//    public String index(ModelMap model) {
//        //首页静态化且存在静态文件
//        if (existIndexPage(getSite())) {
//            return renderStaticPage("index/index.html");
//        }
//        return renderPage("index/index.html", model);
//    }
//
//    /**
//     * 动态页入口
//     * 尽量不要携带太多参数，多使用标签获取数据。
//     * 目前已知的需要携带翻页信息。获得页号和翻页信息吧。
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/29 10:32
//     */
//    @Token(save = true)
//    @RequestMapping(value = "/**/*.*", method = RequestMethod.GET)
//    public String dynamic(HttpServletRequest request, ModelMap model) {
//        int pageNo = URLHelper.getPageNo(request);
//        String[] paths = URLHelper.getPaths(request);
//        int len = paths.length;
//        switch (len) {
//            // 单页
//            case 1:
//                return channel(paths[0], true, model);
//            case 2:
//                // 栏目页
//                if (paths[1].equals(INDEX)) {
//                    return channel(paths[0], false, model);
//                }
//                // 内容页
//                try {
//                    Integer id = Integer.parseInt(paths[1]);
//                    return content(id, pageNo, model);
//                } catch (NumberFormatException e) {
//                    return renderNotFoundPage(model);
//                }
//            default:
//                log.warn("Illegal path length: {}, paths: {}", len, paths);
//                return renderNotFoundPage(model);
//        }
//    }
//
//    public String channel(String path, boolean checkAlone, ModelMap model) {
//        Channel channel = channelMng.findByPathForTag(path);
//        if (channel == null) {
//            log.warn("Channel path not found: {}", path);
//            return renderNotFoundPage(model);
//        }
//        //检查是否单页
//        if (checkAlone && channel.getHasContent()) {
//            return renderNotFoundPage(model);
//        }
//        model.addAttribute("channel", channel);
//        renderPagination(null, model);
//        return isMobileRequest() ? channel.getMobileTplChannelOrDef() : channel.getTplChannelOrDef();
//    }
//
//    public String content(Integer id, int pageNo, ModelMap model) {
//        Content content = contentMng.findById(id);
//        if (content == null) {
//            log.debug("Content id not found: {}", id);
//            return renderNotFoundPage(model);
//        }
//        Integer pageCount = content.getPageCount();
//        if (pageNo > pageCount || pageNo < 0) {
//            return renderNotFoundPage(model);
//        }
//
//        //非终审文章
//        SysConfig config = getSite().getConfig();
//        if (config.getViewOnlyChecked() && !content.getStatus().equals(ContentCheck.CHECKED)) {
//            return renderMessagePage(model, "未终审的文章");
//        }
//
//        Member user = getUser();
//        Site site = content.getSite();
//        Set<MemberGroup> groups = content.getViewGroupsExt();
//        int len = groups.size();
//        // 需要浏览权限
//        if (len != 0) {
//            // 没有登录
//            if (user == null) {
//                getSession().setAttribute("loginSource", "needPerm");
//                return renderLoginPage(model);
//            }
//            // 已经登录但没有权限
//            Integer gid = user.getGroup().getId();
//            boolean right = false;
//            for (MemberGroup group : groups) {
//                if (group.getId().equals(gid)) {
//                    right = true;
//                    break;
//                }
//            }
//            //无权限且不支持预览
//            Boolean preview = config.getConfigAttr().getPreview();
//            if (!preview) {
//                String gName = user.getGroup().getName();
//                return renderMessagePage(model, "所在组无权限且不支持预览", gName);
//            }
//            if (!right) {
//                //无权限支持预览
//                model.addAttribute("preview", true);
//                model.addAttribute("groups", groups);
//            }
//        }
//        //收费模式
//        if (content.getCharge()) {
//            if (user == null) {
//                getSession().setAttribute("loginSource", "charge");
//                return renderLoginPage(model);
//            }
//            //用户已登录判断是否已经购买
//            boolean hasBuy = contentBuyMng.hasBuyContent(user.getId(), content.getId());
//            if (!hasBuy) {
//                String rediretUrl = "/content/buy.html?contentId=" + content.getId();
//                if (StringUtils.isNotBlank(site.getContextPath())) {
//                    rediretUrl = site.getContextPath() + rediretUrl;
//                }
//                return redirect(rediretUrl);
//            }
//        }
//        String txt = content.getTxtByNo(pageNo);
//        // 内容加上关键字
//        txt = cmsKeywordMng.attachKeyword(site.getId(), txt);
//        Paginable pagination = new SimplePage(pageNo, 1, content.getPageCount());
//        model.addAttribute("pagination", pagination);
//        FrontUtils.frontPageData(getRequest(), model);
//        model.addAttribute("content", content);
//        model.addAttribute("channel", content.getChannel());
//        model.addAttribute("title", content.getTitleByNo(pageNo));
//        model.addAttribute("txt", txt);
//        model.addAttribute("pic", content.getPictureByNo(pageNo));
//        renderPage(null, model);
//        return isMobileRequest() ? content.getMobileTplContentOrDef(content.getModel()) : content.getTplContentOrDef(content.getModel());
//    }
//
//    /**
//     * 检查站点首页是否静态化
//     *
//     * @author andy_hulibo@163.com
//     * @date 2018/11/29 11:36
//     */
//    private boolean existIndexPage(Site site) {
//        boolean exist = false;
//        if (site.getStaticIndex()) {
//            File indexPage;
//            if (site.getIndexToRoot()) {
//                indexPage = new File(realPathResolver.get(INDEX_HTML));
//            } else {
//                indexPage = new File(realPathResolver.get(site.getStaticDir() + INDEX_HTML));
//            }
//            if (indexPage.exists()) {
//                exist = true;
//            }
//        }
//        return exist;
//    }
//
//    @Autowired
//    private ChannelMng channelMng;
//    @Autowired
//    private ContentMng contentMng;
//    @Autowired
//    private CmsKeywordMng cmsKeywordMng;
//    @Autowired
//    private RealPathResolver realPathResolver;
//    @Autowired
//    private ContentBuyMng contentBuyMng;
//}
