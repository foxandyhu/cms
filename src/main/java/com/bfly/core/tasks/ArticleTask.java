package com.bfly.core.tasks;

import com.bfly.cms.content.service.IArticleService;
import com.bfly.common.FileUtil;
import com.bfly.core.config.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

/**
 * 文章内容定时任务类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/14 11:31
 */
@Configuration
public class ArticleTask extends AbstractScheduledTask implements IScheduled {

    @Autowired
    private IArticleService articleService;


    private final String ARTICLE_TOP_LEVEL_EXPIRED_RESET = "article_top_level_expired_reset";
    private final String ARTICLE_AUTO_PUBLISH = "article_auto_publish";
    private final String DELETE_ARTICLE_TEMP_FILE = "delete_article_temp_file";

    private final String TOP_LEVEL_FOR_EXPIRED_CRON = "0 5 0 * * ?";
    private final String AUTO_PUBLISH_CRON = "0 5 0 * * ?";
    private final String DELETE_ARTICLE_TEMP_FILE_CRON = "0 15 0 * * ?";

    /**
     * 每天凌晨5分重置所有置顶过期的文章 设置置顶级别为0
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 11:32
     */
    @Scheduled(cron = TOP_LEVEL_FOR_EXPIRED_CRON)
    @ScheduledInfo(name = ARTICLE_TOP_LEVEL_EXPIRED_RESET, remark = "每天凌晨5分重置所有置顶过期的文章 设置置顶级别为0")
    public void resetArticleTopLevelForExpired() {
        if (!allowRun(ARTICLE_TOP_LEVEL_EXPIRED_RESET)) {
            return;
        }
        String message = "执行成功!";
        try {
            articleService.resetArticleTopLevelForExpired(new Date());
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ARTICLE_TOP_LEVEL_EXPIRED_RESET, TOP_LEVEL_FOR_EXPIRED_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }

    /**
     * 每天凌晨5分自动发布文章"发布中"状态的文章
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 13:12
     */
    @Scheduled(cron = AUTO_PUBLISH_CRON)
    @ScheduledInfo(name = ARTICLE_AUTO_PUBLISH, remark = "每天凌晨5分自动发布文章 '发布中' 状态的文章")
    public void articleStatusToPassed() {
        if (!allowRun(ARTICLE_AUTO_PUBLISH)) {
            return;
        }
        String message = "执行成功!";
        try {
            articleService.autoPublishArticle();
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ARTICLE_AUTO_PUBLISH, AUTO_PUBLISH_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }

    /**
     * 每天凌晨15分自动清除临时文件夹中前三天的临时文件
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 13:46
     */
    @Scheduled(cron = DELETE_ARTICLE_TEMP_FILE_CRON)
    @ScheduledInfo(name = DELETE_ARTICLE_TEMP_FILE, remark = "每天凌晨15分自动清除临时文件夹中前三天的临时文件")
    public void deleteArticleTempFile() {
        int maxDay = 3;
        int dayMillis = 1000 * 60 * 60 * 24;
        String message = "执行成功!";
        String path = ResourceConfig.getTempDir();
        File tempDir = new File(path);
        if (!tempDir.exists()) {
            return;
        }
        File[] files = tempDir.listFiles();
        try {
            Stream<File> stream = Stream.of(files);
            stream.forEach(file -> {
                long lastModified = file.lastModified();
                long now = System.currentTimeMillis();
                if ((now - lastModified) > maxDay * dayMillis) {
                    // 清除大于三天的临时文件
                    FileUtil.deleteFiles(file);
                }
            });
        } catch (Exception e) {
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(DELETE_ARTICLE_TEMP_FILE, DELETE_ARTICLE_TEMP_FILE_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }
}
