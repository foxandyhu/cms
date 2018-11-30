package com.bfly.config;

import com.bfly.common.image.ImageScaleImpl;
import com.bfly.common.ipseek.IpSeekerImpl;
import com.bfly.common.web.springmvc.SimpleFreeMarkerView;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.persistence.EntityManagerFactory;
import java.awt.*;

/**
 * 系统总体配置
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/28 14:54
 */
@Configuration
@EnableTransactionManagement
@EnableCaching
public class ApplicationConfig {

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory.");
        }
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
    public HibernateTransactionManager txManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    /**
     * 图片处理
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/14 14:22
     */
    @Bean(initMethod = "init")
    public ImageScaleImpl imageScale() {
        ImageScaleImpl image = new ImageScaleImpl();
        image.setTryMagick(true);
        return image;
    }

    /**
     * IP解析器
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/14 14:30
     */
    @Bean(initMethod = "init")
    public IpSeekerImpl ipSeeker() {
        IpSeekerImpl ipSeeker = new IpSeekerImpl();
        ipSeeker.setFilename("QQWry.Dat");
        return ipSeeker;
    }

    /**
     * 验证码配置Bean
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 10:06
     */
    @Bean
    public GenericManageableCaptchaService captchaService() {
        String acceptedChars = "aabbccddeefgghhkkmnnooppqqsstuuvvwxxyyzz";

        RandomWordGenerator wordGen = new RandomWordGenerator(acceptedChars);

        RandomFontGenerator fontGenRandom = new RandomFontGenerator(26, 34, new Font[]{new Font("Arial", Font.PLAIN, 32)});

        UniColorBackgroundGenerator background = new UniColorBackgroundGenerator(110, 60);

        SingleColorGenerator colorGen = new SingleColorGenerator(new Color(50, 50, 50));
        BaffleTextDecorator baffleDecorator = new BaffleTextDecorator(1, new Color(255, 255, 255));
        DecoratedRandomTextPaster textPaster = new DecoratedRandomTextPaster(4, 4, colorGen, new TextDecorator[]{baffleDecorator});

        ComposedWordToImage wordToImage = new ComposedWordToImage(fontGenRandom, background, textPaster);

        GimpyFactory captchaFactory = new GimpyFactory(wordGen, wordToImage);

        GenericCaptchaEngine captchaEngine = new GenericCaptchaEngine(new GimpyFactory[]{captchaFactory});
        return new GenericManageableCaptchaService(captchaEngine, 180, 100000, 75000);
    }

    @Bean
    public CommandLineRunner customFreemarkerView(FreeMarkerViewResolver resolver) {
        //设置Freemarker解析视图;
        return (String... args) -> resolver.setViewClass(SimpleFreeMarkerView.class);
    }
}
