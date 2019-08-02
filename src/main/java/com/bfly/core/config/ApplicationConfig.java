package com.bfly.core.config;

import com.alibaba.druid.support.http.StatViewServlet;
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
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.util.IntrospectorCleanupListener;

import java.awt.*;
import java.util.EventListener;

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
    public ServletListenerRegistrationBean<EventListener> introSpecTorCleanupListener() {
        return new ServletListenerRegistrationBean<>(new IntrospectorCleanupListener());
    }

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        servletRegistrationBean.setLoadOnStartup(4);
        return servletRegistrationBean;
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

}
