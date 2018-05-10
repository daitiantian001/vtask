package com.lmnml.group.common.inter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by daitian on 2018/5/2.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String target = httpServletRequest.getRequestURI().split("/")[1];
        String[] requestURI = httpServletRequest.getRequestURI().split("/");
        if ("app".equals(target)) {
            return true;
        }

        String pname = "userInfo";
        String sname = "suserInfo";
        boolean pflag = true;
        boolean sflag = true;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null) {
                    if (pname.equals(cookie.getName())) {
                        pflag = true;
                    }
                    if (sname.equals(cookie.getName())) {
                        sflag = true;
                    }
                }
            }
        }
        switch (target) {
            case "pdata":
            case "platform":
                if (!pflag) {
                    httpServletResponse.sendRedirect("/platform/html/home/login.html");
                    return true;
                } else {
                    return true;
                }
            case "sys":
            case "system":
                if (!sflag) {
                    httpServletResponse.sendRedirect("/system/html/home/login.html");
                    return false;
                } else {
                    return true;
                }
            case "api":
                return true;
            default:
                httpServletResponse.sendRedirect("/index.html");
                return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
