package com.bfly.manage.comment;

import com.bfly.cms.comment.entity.GuestBookConfig;
import com.bfly.cms.comment.service.IGuestBookConfigService;
import com.bfly.cms.comment.service.IGuestBookService;
import com.bfly.core.context.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    @Autowired
    private IGuestBookConfigService configService;

    /**
     * 留言列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:14
     */
    @GetMapping("/list")
    public void listGuestBook(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("type.id", DataConvertUtils.convertToInteger(request.getParameter("type")));
                put("recommend", DataConvertUtils.convertToBoolean(request.getParameter("recommend")));
                put("status", DataConvertUtils.convertToBoolean(request.getParameter("status")));
            }
        };
        Pager pager = guestBookService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 管理员修改留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:19
     */
    @PostMapping(value = "/edit")
    public void editGuestBook(HttpServletRequest request, HttpServletResponse response) {
        int guestBookId = DataConvertUtils.convertToInteger(request.getParameter("guestBookId"));
        String content = request.getParameter("content");
        guestBookService.edit(guestBookId, content);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 管理员审核留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/verify")
    public void verifyGuestBook(HttpServletRequest request, HttpServletResponse response) {
        String guestBookIdsStr = request.getParameter("guestBookIds");
        Assert.notNull(guestBookIdsStr, "未选择要审核的留言!");
        String[] cguestBookIds = guestBookIdsStr.split(",");
        int status = DataConvertUtils.convertToInteger(request.getParameter("status"));
        for (String id : cguestBookIds) {
            guestBookService.verifyGuestBook(status, DataConvertUtils.convertToInteger(id));
        }
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 管理员回复留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/reply")
    public void replyGuestBook(HttpServletRequest request, HttpServletResponse response) {
        int guestBookId = DataConvertUtils.convertToInteger(request.getParameter("guestBookId"));
        String content = request.getParameter("content");
        guestBookService.replyGuestBook(guestBookId, content, getUser().getId());
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 推荐留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:23
     */
    @GetMapping(value = "/recommend/{guestBookId}")
    public void viewGuestBook(@PathVariable("guestBookId") int guestBookId, HttpServletResponse response) {
        guestBookService.recommendGuestBook(guestBookId, true);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 删除留言
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:25
     */
    @PostMapping(value = "/del")
    public void removeGuestBook(HttpServletRequest request, HttpServletResponse response) {
        String guestBookStr = request.getParameter("ids");
        Integer[] guestBookIds = DataConvertUtils.convertToIntegerArray(guestBookStr.split(","));
        guestBookService.remove(guestBookIds);
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 获得留言配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:18
     */
    @GetMapping(value = "/config/info")
    public void getGuestBookConfig(HttpServletResponse response) {
        GuestBookConfig config = configService.getGuestBookConfig();
        ResponseUtil.writeJson(response, config);
    }

    /**
     * 编辑留言配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:19
     */
    @PostMapping(value = "/config/edit")
    public void editGuestBookConfig(@Valid GuestBookConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        configService.edit(config);
        ResponseUtil.writeJson(response, "");
    }
}
