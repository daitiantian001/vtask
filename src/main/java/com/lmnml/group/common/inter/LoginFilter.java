//package com.lmnml.group.common.inter;
//
//
//import com.lmnml.group.entity.app.VPlatformUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Created by daitian on 2018/5/2.
// */
//@Component
//public class LoginFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String uri = request.getRequestURI();
//        if (isInclude(uri)) {
//            filterChain.doFilter(request, response);
//            return;
//        } else {
//            Cookie[] cookies = request.getCookies();
//            if(cookies==null || cookies.length==0){
//                response.sendRedirect(request.getContextPath()+"/platform/html/home/login.html");
//                return;
//            }
//            for (Cookie cookie : cookies) {
//                if("userInfo".equals(cookie.getName())){
//                    filterChain.doFilter(request,response);
//                    return;
//                }
//            }
//            response.sendRedirect(request.getContextPath()+"/platform/html/home/login.html");
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Autowired
//    List<Pattern> patterns;
//    /**
//     * 是否需要过滤
//     * @param url
//     * @return
//     */
//    private boolean isInclude(String url) {
//        try {
//            for (Pattern pattern : patterns) {
//                Matcher matcher = pattern.matcher(url);
//                if (matcher.matches()) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }
//}
