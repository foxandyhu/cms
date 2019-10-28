package com.bfly.web.member.directive;

import com.bfly.cms.member.entity.LoginConfig;
import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberLoginConfigService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.core.base.action.BaseTemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 登录验证码标签---根据登录错误次数判断是否显示验证码标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/18 12:42
 */
@Component("loginCaptchaDirective")
public class LoginCaptchaDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private IMemberLoginConfigService loginConfigService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Member member = memberService.getMemberBySession(getRequest().getSession().getId());
        LoginConfig config = loginConfigService.getLoginConfig();
        boolean showCaptcha = false;
        if (config != null && member != null) {
            showCaptcha = member.getErrorCount() >= config.getLoginError();
        }
        env.setVariable("showCaptcha", getObjectWrapper().wrap(showCaptcha));
        body.render(env.getOut());
    }
}
