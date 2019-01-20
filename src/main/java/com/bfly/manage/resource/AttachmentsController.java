package com.bfly.manage.resource;

import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 附件管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:20
 */
@RestController
@RequestMapping(value = "/manage/attachment")
public class AttachmentsController extends BaseManageController {

    /**
     * 附件列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 11:41
     */
    @GetMapping(value = "/list")
    public void listAttachment(HttpServletResponse response) {
        ResponseUtil.writeJson(response, "");
    }

    /**
     * 上传附件
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 11:43
     */
    @PostMapping(value = "/upload")
    public void uploadAttachment(HttpServletResponse response) {
        ResponseUtil.writeJson(response, "");
    }
}
