package com.bfly.manage.message;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.message.service.IGuestBookService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.GuestBookStatus;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 留言Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:14
 */
@RestController
@RequestMapping(value = "/manage/guestbook")
public class GuestBookController extends BaseManageController {

    @Autowired
    private IGuestBookService guestBookService;

    /**
     * 留言列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:14
     */
    @GetMapping("/list")
    @ActionModel(value = "留言列表", need = false)
    public void listGuestBook(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap(3);
        String type = request.getParameter("type");
        String recommend = request.getParameter("recommend");
        String status = request.getParameter("status");
        if (type != null) {
            property.put("type", DataConvertUtils.convertToInteger(type));
        }
        if (recommend != null) {
            property.put("recommend", DataConvertUtils.convertToBoolean(recommend));
        }
        if (status != null) {
            property.put("status", DataConvertUtils.convertToInteger(status));
        }

        Pager pager = guestBookService.getPage(property);
        JSONObject json = JsonUtil.toJsonFilter(pager, "guestBook");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 管理员审核留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/verify/{status}")
    @ActionModel(value = "审核留言")
    public void verifyGuestBook(HttpServletResponse response, @PathVariable("status") boolean status, @RequestBody Integer... ids) {
        guestBookService.verifyGuestBook(status ? GuestBookStatus.PASSED : GuestBookStatus.UNPASSED, ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 管理员回复留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/reply")
    @ActionModel("回复留言")
    public void replyGuestBook(HttpServletResponse response, @RequestBody Map<String, Object> params) {
        int guestBookId = (Integer) params.get("guestBookId");
        String content = String.valueOf(params.get("content"));
        guestBookService.replyGuestBook(getUser().getUserName(), guestBookId, content);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 推荐留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:23
     */
    @GetMapping(value = "/recommend/{guestBookId}-{recommend}")
    @ActionModel("推荐留言")
    public void viewGuestBook(@PathVariable("guestBookId") int guestBookId, @PathVariable("recommend") boolean recommend, HttpServletResponse response) {
        guestBookService.recommendGuestBook(guestBookId, recommend);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:25
     */
    @PostMapping(value = "/del")
    @ActionModel("删除留言")
    public void removeGuestBook(HttpServletResponse response, @RequestBody Integer... ids) {
        guestBookService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
