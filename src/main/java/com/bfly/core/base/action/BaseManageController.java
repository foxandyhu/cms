package com.bfly.core.base.action;

import com.bfly.cms.user.entity.User;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.enums.SysError;
import com.bfly.core.exception.UnAuthException;
import com.bfly.core.exception.UnRightException;
import com.bfly.core.exception.WsResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
        return ContextUtil.getLoginUser();
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
        String message = e.getMessage();
        SysError error = SysError.ERROR;
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (e instanceof WsResponseException) {
            WsResponseException exception = (WsResponseException) e;
            error = SysError.get(exception.getCode());
        } else if (e instanceof UnAuthException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else if (e instanceof UnRightException) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else if (e instanceof NullPointerException) {
            message = "不存在的数据对象!";
        }
        String data = ResponseData.getFail(error, message);
        ResponseUtil.writeJson(response, data);
    }

    /**
     * 校验数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 12:50
     */
    public void validData(BindingResult result) {
        if (result.hasErrors()) {
            throw new WsResponseException(SysError.PARAM_ERROR, result.getFieldError().getDefaultMessage());
        }
    }
}
