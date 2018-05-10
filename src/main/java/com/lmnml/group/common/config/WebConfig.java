package com.lmnml.group.common.config;

import com.lmnml.group.common.inter.LogInterceptor;
import com.lmnml.group.common.inter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by daitian on 2018/5/10.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/pdata/user/**","/app/**","/sys/user/login");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/platform", "/platform/html/home/login.html");
        registry.addRedirectViewController("/system", "/system/html/home/login.html");
        registry.addRedirectViewController("/api", "/swagger-ui.html");
        registry.addRedirectViewController("/error", "/index.html");
    }
}
