package com.bfly.manage.job;

import com.bfly.cms.job.entity.JobResume;
import com.bfly.cms.job.service.IJobApplyService;
import com.bfly.cms.job.service.IJobResumeService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 职位申请Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:38
 */
@RestController
@RequestMapping(value = "/manage/job")
public class JobApplyController extends BaseManageController {


    @Autowired
    private IJobApplyService jobApplyService;

    @Autowired
    private IJobResumeService jobResumeService;

    /**
     * 职位列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:35
     */
    @GetMapping("/apply/list")
    public void listMember(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("content.contentExt.title", request.getParameter("title"));
                put("content.contentExt.shortTitle", request.getParameter("title"));
            }
        };
        Pager pager = jobApplyService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 删除职位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/apply/del")
    public void removeJobApply(HttpServletRequest request, HttpServletResponse response) {
        String jobApplyIdStr = request.getParameter("ids");
        Integer[] jobApplyIds = DataConvertUtils.convertToIntegerArray(jobApplyIdStr.split(","));
        jobApplyService.remove(jobApplyIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 查看用户简历
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:41
     */
    @GetMapping(value = "/resume/{memberId}")
    public void detailJobResume(@PathVariable int memberId, HttpServletResponse response) {
        JobResume resume = jobResumeService.get(memberId);
        ResponseUtil.writeJson(response, resume);
    }
}
