package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.dao.IArticleDao;
import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.service.IArticleService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.enums.ArticleStatus;
import com.bfly.core.enums.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ArticleServiceImpl extends BaseServiceImpl<Article, Integer> implements IArticleService {

    @Autowired
    private IArticleDao articleDao;

    @Override
    public Pager getPage(Map<String, Object> exactQueryProperty, Map<String, String> unExactQueryProperty, Map<String, Sort.Direction> sortQueryProperty) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Pageable pageable = getPageRequest(pager);
        Page<Map<String, Object>> page = articleDao.getArticlePager((Integer) exactQueryProperty.get("status"),
                (Integer) exactQueryProperty.get("type"), (Integer) exactQueryProperty.get("channelId"), unExactQueryProperty.get("title"), pageable);
        List<Map<String, Object>> list = new ArrayList<>();

        List<Map<String, Object>> data = page.getContent();
        if (data != null) {
            data.forEach(itemMap -> {
                Map<String, Object> item = new HashMap<>(itemMap.size() + 2);
                int typeId = (int) itemMap.get("typeId");
                ContentType type = ContentType.getType(typeId);
                item.put("typeName", type == null ? "" : type.getName());

                int status = (int) itemMap.get("status");
                ArticleStatus s = ArticleStatus.getStatus(status);
                item.put("statusName", s == null ? "" : s.getName());
                item.putAll(itemMap);
                list.add(item);
            });
        }

        pager.setTotalCount(page.getTotalElements());
        pager.setData(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        if (integers != null) {
            for (Integer id : integers) {
                if (id != null) {
                    //解除文章和会员组之间的浏览关系
                    articleDao.removeArticleGroupViewShip(id);
                    //清除文章的评分记录
                    articleDao.removeArticleScore(id);
                }
            }
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyArticle(ArticleStatus status, Integer... articleIds) {
        articleDao.verifyArticle(status.getId(), articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendArticle(boolean recommend, int recommendLevel, Integer... articleIds) {
        if (!recommend) {
            //取消推荐 则级别为0
            recommendLevel = 0;
        }
        articleDao.recommendArticle(recommend, recommendLevel, articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editArticleTop(int articleId, int topLevel, Date expired) {
        //不置顶 有效期则为null
        if (topLevel == 0) {
            expired = null;
        }
        articleDao.editArticleTop(articleId, topLevel,expired);
    }
}
