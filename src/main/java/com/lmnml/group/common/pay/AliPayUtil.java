package com.lmnml.group.common.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.lmnml.group.common.model.AliPay;
import com.lmnml.group.util.StrKit;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by daitian on 2018/4/26.
 */
public class AliPayUtil {
    //网关
    public static final String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
    //appid
    public static final String APP_ID = "2016062001534697";
    //支付宝私钥
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAE3RBgCqonxUjwdv5GvfyDdbIbwiTdleA/6AY4e6jiZG4zhSg3mI4QP+C3wJp4l1h3TyY9lbIow2vn+UXhok2W9J91l1h62hk8sBEiXbKvefhKUC29vSJreOfFmWSHXvpd8pZ4M6iX68dUiB2EEnfRe2RXqNAt1n6AV4xbojanb7gjJeoI9RC0pbJzMUDH82pjlBAB7qK+AnQh1Umv0QACdV3NlUrv7JSXTRs4936twkNcy00yTQyCwSMhVHBcqqr5gx4y+v++4NK+SzKCZxEp4vfZhFje0y8+UFkQovmFLQP+pczda9kYEruSrIFW1UpQazzFf/ScnhSDyNoqh9BAgMBAAECggEAJSn//RW8yytGThbtxs4Njp53abfWbDvVzmfew/mjbq73qwGZuMhvBdXOjuI5aBI4eLJi6BvrONMfoIusRXRTkoN4g+1VMZnGtCxAoGKnJtOOWqbG0ZC9isn7l+k+1fRaRXfuLsBuFW3geYigRMIbPX+kHR+yDZBmuoX1GDDz40OjukGdkQtRHM76wZ6E3+u+2yBLPJOmPIOXk2DeqPUdJsUvDQKB22K5Ew8i7mjtJANqcuaEZraH5gS18Wc6Uj82O0RbjcD9C/P6t7AB18U25JT67UtOzszLTX2gkjei68pay/Kq3YlV7F7SEef8q7UjssbxzqXmcQSGSoXX80etwQKBgQC8QaH49rrEr3GtcbNxiBbjxrcla5ypvKOj+O2WLFUYAY93SUy33RXW/q2092IFuTVGchJ8CxoeKpZd602prsSYMG1mpNEHNALmXNCirVup2RXosqfFrCEIrrdpsndAkDW7CGXutCDN2XLg3FtCJ+XketAz/WFPPy6rpmD61biSuQKBgQCuKfGU+nD81d1GpvQZF2/wRMWbwt2j4rnzg0w7vjQwcB3cphqhToxJ7j/Oie4wqMbu7o7A80JqW+D9ngnE/uJZy65nCp0Rm4FLIN8/SCIEitQaqBbU441yE8OSULgtSIbbwosMkYdsXPIlm5tSJVixsFUrE4OKQ7hwwQBxcLBMyQKBgQCqawO0e64hJO1UZKxbpyWJdrcOsfb1FOZSJqL1Iha6D5ePFZTUkNgXcx9Oxe8/XSTkK5meHvK6wjSyurLoW2RunNA8bhEnYycI4Jah3Sswe2tA5pHH+QVCdd9lWIBpuuJY+MZFq1xS3HkJI5GdNr7+8UqO9d3kPj8CdjAugdB/SQKBgHlrAmHDKywJCOetkvU/MqnFuCH7+zq64LpUqbvowUeXkin2nxAfy4vrDg62eBjyQSXnqBv6Oy4c+cC2AceVjkXdyqtJafERgVTYCOt8JUMXagFfd/NY5I5IGwsRPSVMr7/bY8cSWeAA+EuhrOna4XeXH/kU9Jpa1eW9l8p+LlqJAoGAXdBE+SGdv7dVLIELAxnpVuBfukVUu8+isknzfgImECXH/jrhtwIDAYxfbwO3sd+2Q/QjKwDjmNJbD/YR2OtFkns4v5NOtyxnAOx4S4+r2Rwj39962hoqiVZLYNu7tn3+WJImMsNj+NtJ1zyIrWlsQlAT0cyTIWWZG5eZofxNQJ0=";
    //支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    public static final String PARTNER = "2088121420573564";
    //支付宝回调地址
    public static final String ALIPAY_NOTIFYURL = "2088121420573564";
    public static final String NOTIFY_URL = "http://lmnml.com/openApi/notify/";
    public static final String Ali_JS_PAY = "ali";
    public static final String Ali_JS2_PAY = "ali2";

    private static AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY, APP_ID, PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY);

    public static String jsPay(AliPay aliPay) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(aliPay.getBody());
        model.setSubject(aliPay.getIp());
        model.setOutTradeNo(aliPay.getId());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(StrKit.addPoint(aliPay.getTotal() + ""));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL + Ali_JS_PAY);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String passbackParams = URLEncoder.encode(aliPay.getAttach());
            model.setPassbackParams(passbackParams);
//            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String jsPay2(AliPay aliPay) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(aliPay.getBody());
        model.setSubject(aliPay.getIp());
        model.setTimeoutExpress("30m");
        model.setOutTradeNo(aliPay.getId());
        model.setTotalAmount(StrKit.addPoint(aliPay.getTotal() + ""));
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL + Ali_JS2_PAY);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String passbackParams = URLEncoder.encode(aliPay.getAttach());
            model.setPassbackParams(passbackParams);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map parseReq(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    public static boolean verify(Map<String, String> params) {
        boolean sign = false;
        try {
            sign = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return sign;
    }

}
