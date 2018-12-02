package com.bfly.manage.job.action;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.service.ContentMng;
import com.bfly.cms.job.entity.CmsJobApply;
import com.bfly.cms.job.entity.CmsUserResume;
import com.bfly.cms.job.service.CmsJobApplyMng;
import com.bfly.cms.job.service.CmsUserResumeMng;
import com.bfly.cms.system.entity.MemberConfig;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.cms.user.service.CmsUserExtMng;
import com.bfly.common.page.Pagination;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

import static com.bfly.common.page.SimplePage.cpn;

/**
 * 简历中心Action
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 15:43
 */
@Controller
public class ResumeActController extends RenderController {
    private static final Logger log = LoggerFactory.getLogger(ResumeActController.class);

    /**
     * 简历输入页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 15:43
     */
    @GetMapping(value = "/member/resume.html")
    public String resumeInput(ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        return renderPage("member/resume_edit.html", model);
    }


    /**
     * 简历提交页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:04
     */
    @RequestMapping(value = "/member/resume.html", method = RequestMethod.POST)
    public String resumeSubmit(CmsUserResume resume, CmsUserExt ext, String nextUrl, ModelMap model) throws IOException {
        String result = check(model);
        if (result != null) {
            return result;
        }
        CmsUser user = getUser();
        ext.setId(user.getId());
        cmsUserExtMng.update(ext, user);
        resume.setId(user.getId());
        cmsUserResumeMng.update(resume, user);
        log.info("update CmsUserExt success. id={}", user.getId());
        return renderSuccessPage(model, nextUrl);
    }

    @RequestMapping(value = "/member/myapplys.html")
    public String myapplys(Integer pageNo, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        Pagination pagination = jobApplyMng.getPage(getUser().getId(), null, null, true, null, cpn(pageNo), 1);
        model.addAttribute("pagination", pagination);
        return renderPagination("member/job_applys.html", model);
    }

    @RequestMapping(value = "/member/jobapply_delete.html")
    public String delete(Integer[] ids, HttpServletRequest request, String nextUrl, ModelMap model) {
        String result = check(model);
        if (result != null) {
            return result;
        }
        WebErrors errors = validateDelete(ids, getUser(), request);
        if (errors.hasErrors()) {
            return renderErrorPage(model, errors);
        }
        CmsJobApply[] arr = jobApplyMng.deleteByIds(ids);
        log.info("member contribute delete Content success. ids={}", StringUtils.join(arr, ","));
        return renderSuccessPage(model, nextUrl);
    }

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:32
     */
    private String check(ModelMap model) {
        MemberConfig mcfg = getSite().getConfig().getMemberConfig();
        // 没有开启会员功能
        if (!mcfg.isMemberOn()) {
            return renderMessagePage(model, "没有开启会员功能");
        }
        if (getUser() == null) {
            return renderLoginPage(model);
        }
        return null;
    }

    @RequestMapping(value = "/member/jobapply.html")
    public void jobApply(Integer cId, HttpServletResponse response) throws JSONException {
        CmsUser user = getUser();
        JSONObject object = new JSONObject();
        if (user == null) {
            object.put("result", -1);
        } else if (cId == null) {
            object.put("result", -2);
        } else {
            Content c = contentMng.findById(cId);
            if (c == null) {
                object.put("result", -3);
            } else if (user.getUserResume() == null) {
                object.put("result", -4);
            } else if (user.hasApplyToday(cId)) {
                object.put("result", 0);
            } else {
                CmsJobApply jobApply = new CmsJobApply();
                jobApply.setApplyTime(Calendar.getInstance().getTime());
                jobApply.setContent(c);
                jobApply.setUser(user);
                jobApplyMng.save(jobApply);
                object.put("result", 1);
            }
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    private WebErrors validateDelete(Integer[] ids, CmsUser user, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldOpt(errors, user, ids)) {
            return errors;
        }
        return errors;
    }

    private boolean vldOpt(WebErrors errors, CmsUser user, Integer[] ids) {
        for (Integer id : ids) {
            if (errors.ifNull(id, "id", true)) {
                return true;
            }
            CmsJobApply jobapply = jobApplyMng.findById(id);
            // 数据不存在
            if (errors.ifNotExist(jobapply, CmsJobApply.class, id, true)) {
                return true;
            }
            // 非本用户数据
            if (!jobapply.getUser().getId().equals(user.getId())) {
                errors.noPermission(Content.class, id, true);
                return true;
            }
        }
        return false;
    }

    @Autowired
    private CmsUserExtMng cmsUserExtMng;
    @Autowired
    private CmsUserResumeMng cmsUserResumeMng;
    @Autowired
    private CmsJobApplyMng jobApplyMng;
    @Autowired
    private ContentMng contentMng;
}
