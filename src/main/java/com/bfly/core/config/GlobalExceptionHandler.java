package com.bfly.core.config;

import com.bfly.common.ResponseUtil;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.exception.UnAuthException;
import com.octo.captcha.service.CaptchaServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/19 11:19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        try {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            if (ex instanceof UnAuthException) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                //表示异步Ajax请求
                if (ContextUtil.isAjax(request)) {
                    message = "未登录!";
                } else {
                    String returnUrl = request.getRequestURL().toString();
                    response.sendRedirect("/login.html?returnUrl=" + returnUrl);
                    return;
                }
            } else if (ex instanceof CaptchaServiceException) {
                message = "验证码已失效!";
            }
            ResponseUtil.writeText(response, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}