package com.bfly.web.message.action;

import com.bfly.cms.message.entity.GuestBook;
import com.bfly.cms.message.service.IGuestBookService;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.CommentStatus;
import com.bfly.core.enums.GuestBookStatus;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 留言Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/13 11:32
 */
@Controller("webGuestBookController")
@RequestMapping(value = "/guestbook")
public class GuestBookController extends RenderController {

    @Autowired
    private IGuestBookService guestBookService;
    @Autowired
    private ImageCaptchaService captchaService;

    /**
     * 加载留言
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/13 11:33
     */
    @GetMapping(value = "/load")
    public String getGuestBooks() {
        PagerThreadLocal.set(getRequest());

        Map<String, Object> map = new HashMap<>(1);
        map.put("status", GuestBookStatus.PASSED.getId());
        Pager pager = guestBookService.getPage(map);
        getRequest().setAttribute("guestBookPager", pager);
        return renderTplPath("guestbook_list.html", "guestbook");
    }

    /**
     * 发表留言
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/13 12:15
     */
    @PostMapping(value = "/post")
    public void postGuestBook(HttpServletResponse response, GuestBook book) {
        String captcha = getRequest().getParameter("captcha");
        Boolean success = captchaService.validateResponseForID(getSession().getId(), captcha);
        Assert.isTrue(success == null ? false : success.booleanValue(), "验证码不正确!");
        guestBookService.save(book);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
