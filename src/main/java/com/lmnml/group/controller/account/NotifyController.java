package com.lmnml.group.controller.account;

import com.lmnml.group.common.pay.AliPayUtil;
import com.lmnml.group.common.pay.PayUtil;
import com.lmnml.group.controller.BaseController;
import com.lmnml.group.service.app.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by daitian on 2018/4/21.
 */
@RequestMapping("openApi/notify")
@Controller
@Api(value = "支付回调接口", tags = {"支付接口"}, description = "plat支付相关接口")
public class NotifyController extends BaseController {

    @Autowired
    private IUserService userService;

    @PostMapping("wx")
    @ApiOperation(value = "wx支付/充值")
    public void wxNotify(HttpServletRequest request, HttpServletResponse resp){
        Map<String, String> m = null;
        try {
            m = PayUtil.parseXml(request);
            userService.wxPay(m);
            resp.getWriter().write(PayUtil.setXML("SUCCESS", "OK"));
        } catch (Exception e) {
            try {
                resp.getWriter().write(PayUtil.setXML("ERROR", "error"));
            } catch (IOException e1) {
            }
        }
    }

    @PostMapping("ali")
    @ApiOperation(value = "ali支付/充值")
    public String aliNotify(HttpServletRequest request, HttpServletResponse resp){
        Map<String, String> m = null;
        try {
            m = AliPayUtil.parseReq(request);
            System.out.println("============ali======");
            m.forEach((k,v)-> System.out.println(k+"=="+v));
            userService.aliPay(m,request,resp);
            System.out.println("===========支付逻辑完成========");
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

}
