package com.lmnml.group.controller.web;

import com.lmnml.group.util.CookieTools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daitian on 2018/4/16.
 */
@Controller
@RequestMapping("platform")
public class CommonController {

    @RequestMapping("login")
    @ResponseBody
    protected ModelAndView login(HttpServletRequest request,
                                 HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/login", data);
    }

    @RequestMapping("logout")
    @ResponseBody
    protected ModelAndView logout(HttpServletRequest request,
                                  HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        CookieTools.clearCookie(request, response);

        return new ModelAndView("redirect:/platform", data);
    }

    @RequestMapping("task")
    @ResponseBody
    protected ModelAndView task(String userId,HttpServletRequest request,
                                HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();
        return new ModelAndView("platform/home/task", data);
    }

    @RequestMapping("publish")
    @ResponseBody
    protected ModelAndView publish(HttpServletRequest request,
                                   HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/publish", data);
    }

    @RequestMapping("pay")
    @ResponseBody
    protected ModelAndView pay(String taskId, String orderId,
                               HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("taskId", taskId);
        data.put("orderId", orderId);

        return new ModelAndView("platform/home/pay", data);
    }

    @RequestMapping("purse")
    @ResponseBody
    protected ModelAndView purse(HttpServletRequest request,
                                 HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/purse", data);
    }

    @RequestMapping("charge")
    @ResponseBody
    protected ModelAndView charge(HttpServletRequest request,
                                  HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/charge", data);
    }

    @RequestMapping("takeout")
    @ResponseBody
    protected ModelAndView takeout(HttpServletRequest request,
                                   HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/takeout", data);
    }

    @RequestMapping("merchant")
    @ResponseBody
    protected ModelAndView merchant(HttpServletRequest request,
                                    HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();

        return new ModelAndView("platform/home/merchant", data);
    }
}
