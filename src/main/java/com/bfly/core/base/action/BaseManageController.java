package com.bfly.core.base.action;

import com.bfly.cms.user.entity.User;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.Constants;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台管理接口父类Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:44
 */
public class BaseManageController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(BaseManageController.class);

    /**
     * 获得当前登录管理员对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 20:36
     */
    public User getUser() {
        return (User) getSession().getAttribute(Constants.USER_LOGIN_KEY);
    }

    /**
     * 后台管理Controller统一异常处理
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 16:51
     */
    @ExceptionHandler
    public void exceptionHandler(HttpServletResponse response, Exception e) {
        logger.error("", e);
        SysError error = SysError.ERROR;
        if (e instanceof WsResponseException) {
            WsResponseException exception = (WsResponseException) e;
            error = SysError.get(exception.getCode());
        }
        String data = ResponseData.getFail(error, e.getMessage());
        ResponseUtil.writeJson(response, data);
    }
}
