package com.bfly.manage.member;

import com.bfly.cms.member.service.IMemberThirdAccountService;
import com.bfly.common.ContextUtil;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseManageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员第三方平台账号绑定Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:26
 */
@RestController
@RequestMapping(value = "/manage/member/thirdaccount")
public class MemberThirdAccountController extends BaseManageController {

    @Autowired
    private IMemberThirdAccountService thirdAccountService;

    /**
     * 第三方平台账号授权列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:22
     */
    @GetMapping("/list")
    public void listMemberThridAccount(HttpServletRequest request, HttpServletResponse response) {
        ContextUtil.initPager(request);
        Map<String, Object> property = new HashMap<String, Object>(3) {
            private static final long serialVersionUID = -9126101626116724049L;

            {
                put("source", request.getParameter("source"));
                put("username", request.getParameter("username"));
            }
        };
        Pager pager = thirdAccountService.getPage(property);
        ResponseUtil.writeJson(response, pager);
    }

    /**
     * 解除第三方应用授权
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:25
     */
    @GetMapping("/del")
    public void removeThridAccount(HttpServletRequest request, HttpServletResponse response) {
        String accountIdStr = request.getParameter("ids");
        Integer[] accountIds = DataConvertUtils.convertToIntegerArray(accountIdStr.split(","));
        thirdAccountService.remove(accountIds);
        ResponseUtil.writeJson(response, "");
    }
}
