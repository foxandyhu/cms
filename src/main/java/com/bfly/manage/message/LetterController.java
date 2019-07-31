package com.bfly.manage.message;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.message.entity.Letter;
import com.bfly.cms.message.service.ILetterService;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 站内信Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:03
 */
@RestController
@RequestMapping(value = "/manage/letter/")
public class LetterController extends BaseManageController {

    @Autowired
    private ILetterService letterService;

    /**
     * 站内信列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:03
     */
    @GetMapping("/list")
    @ActionModel(value = "站内信列表", need = false)
    public void listMessage(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> exactMap = new HashMap(3);

        String beginSendTime = request.getParameter("beginSendTime");
        String endSendTime = request.getParameter("endSendTime");
        String box = request.getParameter("box");
        String read = request.getParameter("read");
        if (beginSendTime != null && endSendTime != null) {
            exactMap.put("beginSendTime", DateUtil.parseStrDate(beginSendTime));
            exactMap.put("endSendTime", DateUtil.parseStrDate(endSendTime));
        }
        if (box != null) {
            exactMap.put("box", Integer.parseInt(box));
        }
        if (read != null) {
            exactMap.put("read", Boolean.parseBoolean(read));
        }

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("sendTime", Sort.Direction.DESC);

        Pager pager = letterService.getPage(exactMap, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager, "content");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 发布站内信
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 17:02
     */
    @PostMapping(value = "/add")
    @ActionModel("发布站内信")
    public void addLetter(@RequestBody Letter letter, BindingResult result, HttpServletResponse response) {
        validData(result);
        letter.setFromAdmin(true);
        letter.setSender(getUser().getUserName());
        letterService.save(letter);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除站内信
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 17:05
     */
    @PostMapping(value = "/del")
    @ActionModel("删除站内信")
    public void removeLetter(HttpServletResponse response, @RequestBody Integer... ids) {
        letterService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看站内信详情
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/31 20:55
     */
    @GetMapping(value = "/{letterId}")
    @ActionModel(value = "查看站内信", need = false)
    public void detailLetter(@PathVariable("letterId") int letterId, HttpServletResponse response) {
        Letter letter = letterService.get(letterId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(letter));
    }
}
