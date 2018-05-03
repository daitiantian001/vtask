//package com.lmnml.group.common.inter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
///**
// * Created by daitian on 2018/5/2.
// */
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 多个拦截器组成一个拦截器链
//        // addPathPatterns 用于添加拦截规则
//        // excludePathPatterns 用户排除拦截
//        String[] url = {"/openApi/**", "/pdata/user/**", "/platform/html/home/login"};
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(url);
//        super.addInterceptors(registry);
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "DELETE", "PUT");
//    }
//
//    @Bean
//    public List<Pattern> filterRegistrationBean() {
//        List<Pattern> patterns = new ArrayList();
//        patterns.add(Pattern.compile(".*/pdata/user/.*"));
//        patterns.add(Pattern.compile(".*/openApi/*"));
//        patterns.add(Pattern.compile(".*/platform/html/home/login.html"));
//        patterns.add(Pattern.compile("/swagger.*"));
//        return patterns;
//    }
//
//}
