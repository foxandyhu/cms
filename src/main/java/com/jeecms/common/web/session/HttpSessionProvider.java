package com.jeecms.common.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * HttpSession提供类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/14 14:25
 */
public class HttpSessionProvider implements SessionProvider {

    public Serializable getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Serializable) session.getAttribute(name);
        } else {
            return null;
        }
    }

    public void setAttribute(HttpServletRequest request,
                             HttpServletResponse response, String name, Serializable value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }

    public String getSessionId(HttpServletRequest request,
                               HttpServletResponse response) {
        return request.getSession().getId();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
