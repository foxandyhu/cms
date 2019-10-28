package com.bfly.web.member.directive;

import com.bfly.cms.member.entity.Member;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.context.ContextUtil;
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
 * 登录用户标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/17 13:11
 */
@Component("loginMemberDirective")
public class LoginMemberDirective extends BaseTemplateDirective implements TemplateDirectiveModel {
    @Autowired
    private IMemberService memberService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Member member = ContextUtil.getLoginMember();
        if(member!=null) {
            member = memberService.getMember(member.getUserName());
        }
        env.setVariable("loginMember", getObjectWrapper().wrap(member));
        body.render(env.getOut());
    }
}
