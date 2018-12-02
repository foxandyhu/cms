package com.bfly.web.words.action;

import com.bfly.cms.words.entity.CmsSearchWords;
import com.bfly.cms.words.service.CmsSearchWordsMng;
import com.bfly.cms.words.service.SearchWordsCache;
import com.bfly.common.util.StrUtils;
import com.bfly.common.web.RequestUtils;
import com.bfly.common.web.ResponseUtils;
import com.bfly.core.annotation.Token;
import com.bfly.core.base.action.RenderController;
import com.bfly.core.web.WebErrors;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/30 10:34
 */
@Controller
public class SearchAct extends RenderController {

    @Token(remove = true)
    @GetMapping(value = "/search*.html")
    public String index(ModelMap model) {
        // 将request中所有参数保存至model中。
        model.putAll(RequestUtils.getQueryParams(getRequest()));
        String q = getRequest().getParameter("q");
        String channelId = getRequest().getParameter("channelId");
        if (StringUtils.isBlank(q) && StringUtils.isBlank(channelId)) {
            return renderPagination("special/search_input.html", model);
        }
        WebErrors errors = WebErrors.create(getRequest());
        if (StringUtils.isNotBlank(channelId) && !StrUtils.isGreaterZeroNumeric(channelId)) {
            errors.addErrorCode("error.channelId.notNum");
            return renderErrorPage(model, errors);
        }
        String parseQ = parseKeywords(q);
        model.addAttribute("input", q);
        model.addAttribute("q", parseQ);
        searchWordsCache.cacheWord(q);
        return renderPagination("special/search_result.html", model);
    }

    @Token(remove = true)
    @GetMapping(value = "/searchJob*.html")
    public String searchJob(ModelMap model) {
        HttpServletRequest request = getRequest();
        String q = request.getParameter("q");
        String category = request.getParameter("category");
        String workplace = request.getParameter("workplace");
        String parseQ = "";
        model.putAll(RequestUtils.getQueryParams(request));
        if (StringUtils.isBlank(q)) {
            model.remove("q");
        } else {
            //处理lucene查询字符串中的关键字
            parseQ = parseKeywords(q);
            parseQ = StrUtils.xssEncode(parseQ);
            if (!q.equals(parseQ)) {
                return redirect("/searchJob.html");
            }
            model.addAttribute("q", parseQ);
        }
        model.addAttribute("input", parseQ);
        model.addAttribute("queryCategory", category);
        model.addAttribute("queryWorkplace", workplace);
        return renderPagination("special/search_job.html", model);
    }

    @RequestMapping(value = "/createToken.html")
    public void createToken(HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String token = UUID.randomUUID().toString();
        json.put("token", token);
        getSession().setAttribute("token", token);
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping("/search/v_ajax_list.html")
    public void ajaxList(HttpServletResponse response) throws JSONException {
        JSONObject object = new JSONObject();
        Map<String, String> wordsMap = new LinkedHashMap<>();
        String word = getRequest().getParameter("term");
        if (StringUtils.isNotBlank(word)) {
            List<CmsSearchWords> words = manager.getList(getSite().getId(), word, null, CmsSearchWords.HIT_DESC, 0, 20, true);
            for (CmsSearchWords w : words) {
                wordsMap.put(w.getName(), w.getName());
            }
        }
        object.put("words", wordsMap);
        ResponseUtils.renderJson(response, object.get("words").toString());
    }

    @RequestMapping(value = "/searchCustom*.html")
    public String searchCustom(String tpl, ModelMap model) {
        if (StringUtils.isNotBlank(tpl)) {
            model.putAll(RequestUtils.getQueryParams(getRequest()));
            return renderPagination("special/" + tpl + ".html", model);
        } else {
            return renderNotFoundPage(model);
        }
    }

    private static String parseKeywords(String q) {
        char c = '\\';
        int cIndex = q.indexOf(c);
        if (cIndex != -1 && cIndex == 0) {
            q = q.substring(1);
        }
        if (cIndex != -1 && cIndex == q.length() - 1) {
            q = q.substring(0, q.length() - 1);
        }
        try {
            String regular = "[\\+\\-\\&\\|\\!\\(\\)\\{\\}\\[\\]\\^\\~\\*\\?\\:\\\\]";
            Pattern p = Pattern.compile(regular);
            Matcher m = p.matcher(q);
            String src;
            while (m.find()) {
                src = m.group();
                q = q.replaceAll("\\" + src, ("\\\\" + src));
            }
            q = q.replaceAll("AND", "and").replaceAll("OR", "or").replace("NOT", "not").replace("[", "［").replace("]", "］");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return q;
    }

    @Autowired
    private CmsSearchWordsMng manager;
    @Autowired
    private SearchWordsCache searchWordsCache;
}
