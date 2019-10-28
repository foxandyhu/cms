package com.bfly.core.config;

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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.util.IntrospectorCleanupListener;
import org.springframework.web.util.WebAppRootListener;

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

    /**
     * 注册WebApp监听器可以方便获取webapp相关信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/20 14:21
     */
    @Bean
    public ServletListenerRegistrationBean<WebAppRootListener> webAppRootListener() {
        return new ServletListenerRegistrationBean<>(new WebAppRootListener());
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

        RandomFontGenerator fontGenRandom = new RandomFontGenerator(20, 30, new Font[]{new Font("Arial", Font.PLAIN, 30)});

        UniColorBackgroundGenerator background = new UniColorBackgroundGenerator(100, 40);

        SingleColorGenerator colorGen = new SingleColorGenerator(new Color(53, 130, 16));
        BaffleTextDecorator baffleDecorator = new BaffleTextDecorator(1, new Color(255, 255, 255));
        DecoratedRandomTextPaster textPaster = new DecoratedRandomTextPaster(4, 4, colorGen, new TextDecorator[]{baffleDecorator});

        ComposedWordToImage wordToImage = new ComposedWordToImage(fontGenRandom, background, textPaster);

        GimpyFactory captchaFactory = new GimpyFactory(wordGen, wordToImage);

        GenericCaptchaEngine captchaEngine = new GenericCaptchaEngine(new GimpyFactory[]{captchaFactory});
        return new GenericManageableCaptchaService(captchaEngine, 180, 100000, 75000);
    }

}
