package com.bfly.web.words.directive;

import com.bfly.cms.words.entity.SensitiveWords;
import com.bfly.cms.words.service.ISensitiveWordsService;
import freemarker.template.TemplateMethodModelEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 敏感词过滤方法
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/16 11:43
 */
@Component("sensitiveFilterMethod")
public class SensitiveFilterMethod implements TemplateMethodModelEx {

    @Autowired
    private ISensitiveWordsService sensitiveWordsService;

    @Override
    public Object exec(List arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return null;
        }
        String word = String.valueOf(arguments.get(0));
        if (!StringUtils.hasLength(word)) {
            return null;
        }

        List<SensitiveWords> list = sensitiveWordsService.getList();
        if (list != null) {
            for (SensitiveWords sensitiveWords : list) {
                if (word.contains(sensitiveWords.getWord())) {
                    word = word.replaceAll(sensitiveWords.getWord(), sensitiveWords.getReplace());
                }
            }
        }
        return word;
    }
}
