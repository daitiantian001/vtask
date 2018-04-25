package com.lmnml.group.controller.web;

import com.lmnml.group.util.CookieTools;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daitian on 2018/4/22.
 */
@RestController
@RequestMapping("system")
public class ComController {

    @GetMapping("login")
    @ResponseBody
    protected ModelAndView logout(HttpServletRequest request,
                                  HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();
//        CookieTools.clearCookie(request, response);
        return new ModelAndView("redirect:/system/index", data);
    }
}
