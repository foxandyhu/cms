package com.bfly.manage.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.job.entity.JobApply;
import com.bfly.cms.job.entity.JobResume;
import com.bfly.cms.job.service.IJobApplyService;
import com.bfly.cms.job.service.IJobResumeService;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.entity.MemberExt;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
    @ActionModel(value = "职位申请列表", need = false)
    public void listJobApply(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);

        String title = request.getParameter("title");
        Map<String, String> property = new HashMap<>(1);
        property.put("title", title);

        Map<String, Sort.Direction> sort = new HashMap<>(1);
        sort.put("applyTime", Sort.Direction.DESC);

        Pager pager = jobApplyService.getPage(null, property, sort);
        List list = pager.getData();
        JSONArray array = new JSONArray();
        if (list != null) {
            list.forEach(o -> {
                JobApply apply = (JobApply) o;
                JSONObject json = new JSONObject();
                json.put("id", apply.getId());
                json.put("title", apply.getTitle());
                json.put("applyTime", apply.getApplyTime());

                Member member = apply.getMember();
                if (member != null) {
                    json.put("userName", member.getUserName());
                    json.put("memberId", member.getId());
                    if (member.getMemberExt() != null && StringUtils.hasLength(member.getMemberExt().getFace())) {
                        json.put("face", ResourceConfig.getServer() + member.getMemberExt().getFace());
                    }
                }
                array.add(json);
            });
        }
        pager.setData(array);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 删除职位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/apply/del")
    @ActionModel(value = "删除申请职位")
    public void removeJobApply(HttpServletResponse response, @RequestBody Integer... ids) {
        jobApplyService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看用户简历
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:41
     */
    @GetMapping(value = "/resume/{memberId}")
    @ActionModel(value = "查看用户简历",need = false)
    public void detailJobResume(@PathVariable int memberId, HttpServletResponse response) {
        JobResume resume = jobResumeService.get(memberId);
        JSONObject json = JsonUtil.toJsonFilter(resume, "member");
        if (resume.getMember() != null) {
            Member member = resume.getMember();
            MemberExt ext = member.getMemberExt();
            if (ext != null) {
                json.put("realName", ext.getRealName());
                json.put("sex", ext.isGirl());
                json.put("birthday", DateUtil.formatterDateStr(ext.getBirthday()));
                json.put("comeFrom", ext.getComeFrom());
                json.put("mobile", ext.getMobile());
                json.put("phone", ext.getPhone());
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }
}
