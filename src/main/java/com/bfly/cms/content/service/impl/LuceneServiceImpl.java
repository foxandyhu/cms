package com.bfly.cms.content.service.impl;

import com.bfly.cms.content.entity.Article;
import com.bfly.cms.content.entity.ArticleExt;
import com.bfly.cms.content.entity.ArticleTxt;
import com.bfly.cms.content.entity.Channel;
import com.bfly.cms.content.entity.dto.ArticleLuceneDTO;
import com.bfly.cms.content.service.IChannelService;
import com.bfly.cms.content.service.ILuceneService;
import com.bfly.common.DateUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.config.ResourceConfig;
import com.bfly.core.context.PagerThreadLocal;
import com.hankcs.lucene.HanLPIndexAnalyzer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/10/25 11:10
 */
@Service
public class LuceneServiceImpl implements ILuceneService {

    private Logger logger = LoggerFactory.getLogger(LuceneServiceImpl.class);

    private static final String ID = "id";
    private static final String STATUS = "status";
    private static final String TITLE = "title";
    private static final String SUMMARY = "summary";
    private static final String TXT = "txt";
    private static final String DATE = "date";
    private static final String TITLE_IMG = "titleImg";
    private static final String CHANNEL_PATH = "channel_path";
    private static final SimpleHTMLFormatter SIMPLE_HTML_FORMATTER = new SimpleHTMLFormatter("<font style='color:red;font-weight:bold;'>", "</font>");

    @Autowired
    private IChannelService channelService;

    /**
     * 得到索引库对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:20
     */
    private Directory getDir() throws Exception {
        synchronized (LuceneServiceImpl.class) {
            String path = ResourceConfig.getArticleIndexDir();
            Directory dir = NIOFSDirectory.open(Paths.get(path));
            return dir;
        }
    }

    /**
     * 得到写对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 11:21
     */
    private IndexWriter getWriter(Directory dir) throws Exception {
        IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(new HanLPIndexAnalyzer()));
        return writer;
    }

    /**
     * 得到读对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 12:01
     */
    private IndexReader getReader(Directory dir) throws Exception {
        IndexReader reader = DirectoryReader.open(dir);
        return reader;
    }

    /**
     * 获得文档对象
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/25 12:05
     */
    private Document getDoc(ArticleLuceneDTO article) {
        Document doc = new Document();

        doc.add(new IntPoint(ID, article.getId()));
        doc.add(new StoredField(ID, article.getId()));

        doc.add(new IntPoint(STATUS, article.getId()));
        doc.add(new StoredField(STATUS, article.getId()));

        String title = article.getTitle();
        title = title == null ? "" : title;
        doc.add(new TextField(TITLE, title, Field.Store.YES));

        String titleImg = article.getTitleImg();
        titleImg = titleImg == null ? "" : titleImg;
        doc.add(new StringField(TITLE_IMG, titleImg, Field.Store.YES));

        String summary = article.getSummary();
        summary = summary == null ? "" : summary;
        doc.add(new StringField(SUMMARY, summary, Field.Store.YES));

        String txt = article.getTxt();
        txt = txt == null ? "" : txt;
        doc.add(new TextField(TXT, txt, Field.Store.YES));

        Date date = article.getPostDate();
        String dateStr = "";
        if (date != null) {
            dateStr = DateUtil.formatterDateStr(date);
        }
        doc.add(new StringField(DATE, dateStr, Field.Store.YES));

        doc.add(new StringField(CHANNEL_PATH, article.getChannelPath() != null ? article.getChannelPath() : "", Field.Store.YES));
        return doc;
    }

    /**
     * 获得高亮显示后的字符串
     *
     * @param query 查询对象
     * @param field 字段名称
     * @param data  值
     * @return 返回高亮显示的字符串
     * @author 胡礼波
     * 2013-5-16 下午6:05:04
     */
    private static String getHighLighterText(Query query, String field, String data) {
        String tempData = data;
        Highlighter high = new Highlighter(SIMPLE_HTML_FORMATTER, new QueryScorer(query));
        //最大显示200字符
        high.setTextFragmenter(new SimpleFragmenter(200));
        if (data != null) {
            try (HanLPIndexAnalyzer analyzer = new HanLPIndexAnalyzer()) {
                TokenStream tokenStream = analyzer.tokenStream(field, data);
                data = high.getBestFragment(tokenStream, data);
                //如果对应的Field字段不存在高亮显示的字符串则显示原始字符串
                data = data == null ? tempData : data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * Article转换DTO
     *
     * @author andy_hulibo@163.com
     * @date 2019/10/28 11:41
     */
    private ArticleLuceneDTO convertToLuceneDTO(Article article) {
        ArticleLuceneDTO dto = new ArticleLuceneDTO();
        dto.setId(article.getId());

        String title = article.getArticleExt() != null ? article.getArticleExt().getTitle() : "";
        dto.setTitle(title);

        String txt = article.getArticleTxt() != null ? article.getArticleTxt().getTxt() : "";
        dto.setTxt(txt);

        dto.setTitleImg(article.getArticleExt() != null ? article.getArticleExt().getTitleImg() : "");
        dto.setStatus(article.getStatus());

        String summary = article.getArticleExt() != null ? article.getArticleExt().getSummary() : "";
        dto.setSummary(summary);

        Channel channel = channelService.get(article.getChannelId());
        dto.setChannelPath(channel != null ? channel.getPath() : "");
        return dto;
    }

    @Override
    public boolean createIndex(Article... articles) {
        try (Directory dir = getDir(); IndexWriter writer = getWriter(dir)) {
            for (Article article : articles) {
                Document doc = getDoc(convertToLuceneDTO(article));
                writer.addDocument(doc);
            }
            writer.commit();
        } catch (Exception e) {
            logger.error("写入索引错误", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean createIndex(ArticleLuceneDTO... articles) {
        try (Directory dir = getDir(); IndexWriter writer = getWriter(dir)) {
            for (ArticleLuceneDTO article : articles) {
                Document doc = getDoc(article);
                writer.addDocument(doc);
            }
            writer.commit();
        } catch (Exception e) {
            logger.error("写入索引错误", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateIndex(Article... articles) {
        try (Directory dir = getDir(); IndexWriter writer = getWriter(dir)) {
            for (Article article : articles) {
                Document doc = getDoc(convertToLuceneDTO(article));
                writer.updateDocument(new Term("id", article.getId() + ""), doc);
            }
            writer.commit();
        } catch (Exception e) {
            logger.error("更新索引错误", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteIndex(Integer... articleId) {
        try (Directory dir = getDir(); IndexWriter writer = getWriter(dir)) {
            for (Integer id : articleId) {
                writer.deleteDocuments(new Term("id", id + ""));
            }
            writer.commit();
        } catch (Exception e) {
            logger.error("删除索引错误", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try (Directory dir = getDir(); IndexWriter writer = getWriter(dir)) {
            writer.deleteAll();
            writer.commit();
        } catch (Exception e) {
            logger.error("删除索引库错误", e);
            return false;
        }
        return true;
    }

    @Override
    public Pager<ArticleLuceneDTO> query(String keyWord) {
        Pager pager = PagerThreadLocal.get();
        try (Directory dir = getDir(); IndexReader reader = getReader(dir)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = MultiFieldQueryParser.parse(keyWord,
                    new String[]{TITLE, SUMMARY, TXT},
                    new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD},
                    new HanLPIndexAnalyzer());

            int pageNo = pager == null ? 1 : pager.getPageNo();
            int pageSize = pager == null ? 10 : pager.getPageSize();

            TopDocs docs = searcher.search(query, 100);
            long total = docs.totalHits;
            ScoreDoc[] scoreDocs = docs.scoreDocs;

            pager = new Pager(pageNo, pageSize, total);

            List<ArticleLuceneDTO> list = new ArrayList<>(scoreDocs.length);
            scoreDocs = ArrayUtils.subarray(scoreDocs, (pageNo - 1) * pageSize, pageNo * pageSize);

            for (ScoreDoc scoreDoc : scoreDocs) {
                Document document = searcher.doc(scoreDoc.doc);

                ArticleLuceneDTO article = new ArticleLuceneDTO();
                article.setId(Integer.parseInt(document.get(ID)));
                article.setStatus(Integer.parseInt(document.get(STATUS)));
                article.setChannelPath(document.get(CHANNEL_PATH));

                article.setTitleImg(document.get(TITLE_IMG));
                article.setTitle(getHighLighterText(query, TITLE, document.get(TITLE)));
                article.setSummary(getHighLighterText(query, SUMMARY, document.get(SUMMARY)));
                article.setPostDate(DateUtil.parseStrDate(document.get(DATE)));
                article.setTxt(getHighLighterText(query, TXT, document.get(TXT)));
                list.add(article);
            }
            pager.setData(list);
        } catch (Exception e) {
            logger.error("搜索索引库出错", e);
        }
        return pager;
    }
}
