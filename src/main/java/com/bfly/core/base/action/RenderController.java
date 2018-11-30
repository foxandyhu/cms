package com.bfly.core.base.action;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.common.web.RequestUtils;
import com.bfly.core.web.WebErrors;
import com.bfly.core.web.util.URLHelper;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Map;

import static com.bfly.core.Constants.RES_PATH;
import static com.bfly.core.servlet.ProcessTimeFilter.START_TIME;

/**
 * 页面渲染controller父类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 11:46
 */
public class RenderController extends AbstractController {

    /**
     * 封装公共数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:41
     */
    private void renderCommonData(Map<String, Object> model) {
        if (model == null) {
            return;
        }
        Long startTime = (Long) getRequest().getAttribute(START_TIME);
        String location = RequestUtils.getLocation(getRequest());
        CmsSite site = getSite();
        CmsUser user = getUser();

        model.put("token", getRequest().getAttribute("token"));
        model.put("_start_time", startTime);
        if (user != null) {
            model.put("user", user);
        }
        if (startTime != null) {
            model.put("_start_time", startTime);
        }

        model.put("site", site);
        String ctx = site.getContextPath() == null ? "" : site.getContextPath();
        model.put("base", ctx);
        model.put("resSys", ctx + "/r/cms");
        String res = ctx + "/r/cms" + "/" + site.getPath() + "/" + site.getTplSolution();
        String mobileRes = ctx + "/r/cms" + "/" + site.getPath() + "/" + site.getTplMobileSolution();
        // res路径需要去除第一个字符'/'
        model.put("res", res.substring(1));
        model.put("mobileRes", mobileRes.substring(1));
        model.put("location", location);
    }

    /**
     * 封装分页公共数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:41
     */
    protected void renderCommonPageData(Map<String, Object> model) {
        if (model == null) {
            return;
        }
        int pageNo = URLHelper.getPageNo(getRequest());
        URLHelper.PageInfo info = URLHelper.getPageInfo(getRequest());
        String href = info.getHref();
        String hrefFormer = info.getHrefFormer();
        String hrefLatter = info.getHrefLatter();
        model.put("pageNo", pageNo);
        model.put("href", href);
        model.put("hrefFormer", hrefFormer);
        model.put("hrefLatter", hrefLatter);
    }

    /**
     * 返回要渲染动态页面的路径
     *
     * @param page 页面相对路径
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderPage(String page) {
        String path;
        if (isMobileRequest()) {
            path = getSite().getMobileSolutionPath();
        } else {
            path = getSite().getSolutionPath();
        }
        path += "/"+page;
        return path;
    }

    /**
     * 返回要渲染动态页面的路径
     *
     * @param page 页面相对路径
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderPage(String page, Map<String, Object> model) {
        renderCommonData(model);
        return renderPage(page);
    }

    /**
     * 返回要渲染动态页面的路径并初始化分页数据
     *
     * @param page 页面相对路径
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderPagination(String page, Map<String, Object> model) {
        renderCommonData(model);
        renderCommonPageData(model);
        return renderPage(page);
    }

    /**
     * 返回要消息提示动态页面的路径
     *
     * @param model   数据模型
     * @param message 消息提示
     * @param args    参数
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderMessagePage(Map<String, Object> model, String message, String... args) {
        if (model != null) {
            model.put("message", message);
            if (args != null) {
                model.put("args", args);
            }
        }
        return renderPage("common/message_page.html", model);
    }

    /**
     * 成功页面
     *
     * @param nextUrl nextUrl
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:07
     */
    protected String renderSuccessPage(Map<String, Object> model, String nextUrl) {
        if (model != null) {
            model.put("nextUrl", nextUrl);
        }
        return renderPage("common/success_page.html", model);
    }

    /**
     * 错误页面
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:07
     */
    protected String renderErrorPage(Map<String, Object> model, WebErrors errors) {
        errors.toModel(model);
        return renderPage("common/error_page.html", model);
    }

    /**
     * 返回要模板找不到提示动态页面的路径
     *
     * @param model 数据模型
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderNotFoundPage(Map<String, Object> model) {
        return renderPage("common/page_not_found.html", model);
    }

    /**
     * 返回登录页面的路径
     *
     * @param model 数据模型
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderLoginPage(Map<String, Object> model) {
        if (model != null) {
            model.put("message", "true");
        }
        StringBuilder buff = new StringBuilder("redirect:");
        buff.append(getSite().getLoginUrl()).append("?");
        buff.append("returnUrl").append("=");
        buff.append(RequestUtils.getLocation(getRequest()));
        if (!StringUtils.isBlank(getSite().getProcessUrl())) {
            buff.append("&").append("returnUrl").append(getSite().getProcessUrl());
        }
        return buff.toString();
    }

    /**
     * 返回要渲染静态页面的路径
     *
     * @param page 页面相对路径
     * @return 模板路径
     * @author andy_hulibo@163.com
     * @date 2018/11/29 13:41
     */
    protected String renderStaticPage(String page) {
        String path;
        if (isMobileRequest()) {
            path = getSite().getMobileSolutionPath();
            path += getSite().getStaticMobileDir();
        } else {
            path = getSite().getSolutionPath();
            path += getSite().getStaticDir();
        }
        path += page;
        return path;
    }

    /**
     * URL重定向
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:59
     */
    public String redirect(String url){
        return "redirect:"+url;
    }
}
