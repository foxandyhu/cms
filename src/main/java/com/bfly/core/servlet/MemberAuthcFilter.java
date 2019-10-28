package com.bfly.core.servlet;

import com.bfly.core.context.ContextUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会员权限验证自定义Filter
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/17 17:02
 */
public class MemberAuthcFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (ContextUtil.getLoginMember() == null) {
            return false;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
