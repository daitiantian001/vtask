package com.lmnml.group.common.inter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by daitian on 2018/5/10.
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    private NamedThreadLocal<Long> longNamedThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long beginTime = System.currentTimeMillis();
        longNamedThreadLocal.set(beginTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = longNamedThreadLocal.get();
        long consumeTime = (endTime - beginTime) / 1000;
        logger.info(String.format("请求：%s 耗时 %d 秒", httpServletRequest.getRequestURI(), consumeTime));
    }
}
