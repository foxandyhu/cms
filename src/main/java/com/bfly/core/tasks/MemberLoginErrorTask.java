package com.bfly.core.tasks;

import com.bfly.cms.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * 定时清除会员登录错误次数数据--下次登录不需要验证码
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/18 17:05
 */
@Configuration
public class MemberLoginErrorTask extends AbstractScheduledTask implements IScheduled {

    @Autowired
    private IMemberService memberService;


    private final String CLEAR_MEMBER_LOGIN_ERROR = "member_login_error_clear";

    private final String CLEAR_MEMBER_LOGIN_ERROR_CRON = "0 0/3 * * * ?";

    /**
     * 每3分钟定时清理一次登录错误计数数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/18 17:07
     */
    @Scheduled(cron = CLEAR_MEMBER_LOGIN_ERROR_CRON)
    @ScheduledInfo(name = CLEAR_MEMBER_LOGIN_ERROR, remark = "3分钟清理一次登录超时错误计数数据,下次登录不需要验证码")
    public void resetArticleTopLevelForExpired() {
        if (!allowRun(CLEAR_MEMBER_LOGIN_ERROR)) {
            return;
        }
        String message = "执行成功!";
        try {
            memberService.clearMemberLoginError();
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(CLEAR_MEMBER_LOGIN_ERROR, CLEAR_MEMBER_LOGIN_ERROR_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }
}
