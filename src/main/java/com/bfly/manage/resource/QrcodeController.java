package com.bfly.manage.resource;

import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 二维码生成Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:44
 */
@RestController
@RequestMapping(value = "/manage/qrcode")
public class QrcodeController extends BaseManageController {

    /**
     * 二维码生成
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 11:47
     */
    @PostMapping(value = "/create")
    public void createQrcode(HttpServletResponse response) {
        ResponseUtil.writeJson(response, "");
    }
}
