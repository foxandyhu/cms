package com.bfly.manage.statistic;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.cms.member.service.IMemberService;
import com.bfly.cms.message.service.ICommentService;
import com.bfly.cms.message.service.IGuestBookService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseManageController;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 系统内容统计
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/14 19:03
 */
@RestController
@RequestMapping(value = "/manage/statistic")
public class StatisticContentController extends BaseManageController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IGuestBookService guestBookService;
    @Autowired
    private IMemberService memberService;

    /**
     * 系统内容统计
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/14 19:04
     */
    @GetMapping("/content")
    @ActionModel(value = "内容汇总统计", need = false)
    public void statisticContent(HttpServletResponse response) {
        Map<String, BigInteger> articleMap = articleService.getTodayAndTotalArticle();
        Map<String, BigInteger> commentMap = commentService.getTodayAndTotalComment();
        Map<String, BigInteger> guestBookMap = guestBookService.getTodayAndTotalGuestBook();
        Map<String, BigInteger> memberMap = memberService.getTodayAndTotalMember();
        JSONObject json = new JSONObject();
        json.put("article", articleMap);
        json.put("comment", commentMap);
        json.put("guestBook", guestBookMap);
        json.put("member", memberMap);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 统计最新数据--首页展示
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/15 12:10
     */
    @GetMapping(value = "/latest")
    @ActionModel(value = "最新内容统计", need = false)
    public void statisticLatest(HttpServletResponse response) {
        List<Map<String, Object>> members = memberService.getLatestMember(5);
        List<Map<String, Object>> comments = commentService.getLatestComment(5,null);
        List<Map<String, Object>> guestBooks = guestBookService.getLatestGuestBook(5);
        List<Map<String, Object>> clickTops = articleService.getClickTopArticle(10);
        List<Map<String, Object>> commentsTops = articleService.getCommentsTopArticle(10);

        JSONObject json = new JSONObject();
        json.put("members", members);
        json.put("comments", comments);
        json.put("guestBooks", guestBooks);
        json.put("clickTops", clickTops);
        json.put("commentTops", commentsTops);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }
}
