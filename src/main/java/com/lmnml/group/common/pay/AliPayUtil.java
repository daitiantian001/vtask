package com.lmnml.group.common.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
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
    public static final String APP_ID = "2018011701936525";
    //应用私钥
    public static final String PRIVATE_KEY ="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzgDRyea8yu0ElleMHuO2VGN0HQ6HZJXrNSw9p78cY1HAfjx9OkYbAjH5yRymS8xIeB8MfFatStz0ILfrTQpEjNfD1Bzlt5sbEYV0LKP+qhea1hyg479kOh9xW6a9eltLjRfyjGxmO7/FHeAfuXWBsoFF3s/0syvAktf1RMsQYPGz2QIx2UCeBMMtIH3qioL6YM14zE7zfYJ57TK+t1GAlnHwLis6kJWc/pfN3QuuSqkv5xo6T/+ASuXnCK58w1fxytbNT68QVLJX5py+m60k+KoT3RXcPf5Bb8w7wEz7+cRYThZoIzf0yI2P49MI7HdUA1rMFxDHhXgB5gFsywxdlAgMBAAECggEBAJ8DfvK+W3b8rYynSHTJ3SZM3VkXD+S2sB/3U5NMR7r2TtGWT2yhvJcMnuOLqQe17yHNTIxX/OqMyzNY0C+gc8SWf3wBR13Vp9XGTs+ZJUwhzKyZLAesakPWFVqlL6cCw5V0pYa0diLK6pqeeaAu9kGkkCcTur+Yk75hviPVbiPsv3gpeDOTbKlvSBQ/YhgOSn+zTsz4unZWHwibK2oJADbvC5x2yoyqhqrlKbmwQ/x4ceJeQBlFq9JNpryOt2vLgzB6l6ibRYy71hqqKA7ST1bj8YqQPGgi9yFbbOgUWTqQu4V8T2v5xYtp61poZ7CLBRZqJzqAfj1MFCp331CLDgECgYEA4O6mnt3wvGWl7fJCW5Xld3i10B26YdGaAw6HxlOmj3p+gK6p4bfCcK7j9ux6cI82G+SOmyjWk05RB41Ze/1vqEbGxscnWYFTcoj+Mw2tLwSN+d7Ir/4b1Za+mnEHyZ07QP7Ai/EsND5IBYaSVCfGLO8qscTsLDQq1ljHoPLGA4ECgYEAzEsmapKDovsAOXkfyVGvW0q6/6XV/LVvIpJVHS8/I4bhCCyozmUJslKjVofxh5TkwV75mVpjhZ92pPeUjPj/KSYHrZl8BEct4VFp3LNfFaEnNuvWlK8NdRKve3PBYxXTimtQoMoT2dprzUOPvt2r945zIPw73wGrpGZUQ2wIdeUCgYB1RrkFN/6em/kJuTgz/FGqGvvgyL825UwBr//GvjKnuFxgx7C3iVIN4oHlaqLkMScrH93itEY8g6bn4yD27sra2Bwn5Ap2NmHF4/t1rvnO+/bygt/sMvdcQ76ZU7mVuy770leeH17G1npCt0lwK1S+dOyzx1LEMWbsX1mmZy4rAQKBgQDIOouvfmRolAwXSIEABwCQwIDhWb+OzKDdSIEFdq4yetsJ8QOOLvc/AMG4aMswcJ14bUbDqBTM/YsCiunc9ZrY0L5LxpgHdWAha3MPbfqur2FI8gNBfQT6jtLRc5XCML1B+IZw4T5tkieAakxz7L3Eh6iNVsqzeuao3eaOok70hQKBgQCcEa2jIR82i/3kcV3GW0fKBnI9Q/ppJ7iYoIBUnXYnqEGvxr8yFAQKfrlJuL6TF2uwB9uVGXJDxv4tcdmnMbqwVupjPEECaHDB3DrI1QlV7vPcjXf6J/Kqc0Hh8CCD3Es0lLxm6+D9+wHRNElvSg7jkMx3EBwiq1BqOY8mNYYpIw==";
//    public static final String PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCAE3RBgCqonxUjwdv5GvfyDdbIbwiTdleA/6AY4e6jiZG4zhSg3mI4QP+C3wJp4l1h3TyY9lbIow2vn+UXhok2W9J91l1h62hk8sBEiXbKvefhKUC29vSJreOfFmWSHXvpd8pZ4M6iX68dUiB2EEnfRe2RXqNAt1n6AV4xbojanb7gjJeoI9RC0pbJzMUDH82pjlBAB7qK+AnQh1Umv0QACdV3NlUrv7JSXTRs4936twkNcy00yTQyCwSMhVHBcqqr5gx4y+v++4NK+SzKCZxEp4vfZhFje0y8+UFkQovmFLQP+pczda9kYEruSrIFW1UpQazzFf/ScnhSDyNoqh9BAgMBAAECggEAJSn//RW8yytGThbtxs4Njp53abfWbDvVzmfew/mjbq73qwGZuMhvBdXOjuI5aBI4eLJi6BvrONMfoIusRXRTkoN4g+1VMZnGtCxAoGKnJtOOWqbG0ZC9isn7l+k+1fRaRXfuLsBuFW3geYigRMIbPX+kHR+yDZBmuoX1GDDz40OjukGdkQtRHM76wZ6E3+u+2yBLPJOmPIOXk2DeqPUdJsUvDQKB22K5Ew8i7mjtJANqcuaEZraH5gS18Wc6Uj82O0RbjcD9C/P6t7AB18U25JT67UtOzszLTX2gkjei68pay/Kq3YlV7F7SEef8q7UjssbxzqXmcQSGSoXX80etwQKBgQC8QaH49rrEr3GtcbNxiBbjxrcla5ypvKOj+O2WLFUYAY93SUy33RXW/q2092IFuTVGchJ8CxoeKpZd602prsSYMG1mpNEHNALmXNCirVup2RXosqfFrCEIrrdpsndAkDW7CGXutCDN2XLg3FtCJ+XketAz/WFPPy6rpmD61biSuQKBgQCuKfGU+nD81d1GpvQZF2/wRMWbwt2j4rnzg0w7vjQwcB3cphqhToxJ7j/Oie4wqMbu7o7A80JqW+D9ngnE/uJZy65nCp0Rm4FLIN8/SCIEitQaqBbU441yE8OSULgtSIbbwosMkYdsXPIlm5tSJVixsFUrE4OKQ7hwwQBxcLBMyQKBgQCqawO0e64hJO1UZKxbpyWJdrcOsfb1FOZSJqL1Iha6D5ePFZTUkNgXcx9Oxe8/XSTkK5meHvK6wjSyurLoW2RunNA8bhEnYycI4Jah3Sswe2tA5pHH+QVCdd9lWIBpuuJY+MZFq1xS3HkJI5GdNr7+8UqO9d3kPj8CdjAugdB/SQKBgHlrAmHDKywJCOetkvU/MqnFuCH7+zq64LpUqbvowUeXkin2nxAfy4vrDg62eBjyQSXnqBv6Oy4c+cC2AceVjkXdyqtJafERgVTYCOt8JUMXagFfd/NY5I5IGwsRPSVMr7/bY8cSWeAA+EuhrOna4XeXH/kU9Jpa1eW9l8p+LlqJAoGAXdBE+SGdv7dVLIELAxnpVuBfukVUu8+isknzfgImECXH/jrhtwIDAYxfbwO3sd+2Q/QjKwDjmNJbD/YR2OtFkns4v5NOtyxnAOx4S4+r2Rwj39962hoqiVZLYNu7tn3+WJImMsNj+NtJ1zyIrWlsQlAT0cyTIWWZG5eZofxNQJ0=";
    //应用公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs4A0cnmvMrtBJZXjB7jtlRjdB0Oh2SV6zUsPae/HGNRwH48fTpGGwIx+ckcpkvMSHgfDHxWrUrc9CC3600KRIzXw9Qc5bebGxGFdCyj/qoXmtYcoOO/ZDofcVumvXpbS40X8oxsZju/xR3gH7l1gbKBRd7P9LMrwJLX9UTLEGDxs9kCMdlAngTDLSB96oqC+mDNeMxO832Cee0yvrdRgJZx8C4rOpCVnP6Xzd0LrkqpL+caOk//gErl5wiufMNX8crWzU+vEFSyV+acvputJPiqE90V3D3+QW/MO8BM+/nEWE4WaCM39MiNj+PTCOx3VANazBcQx4V4AeYBbMsMXZQIDAQAB";
//    public static final String ALIPAY_PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    //支付宝公钥
    public static final String ZFB_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlXi7KbEjNksSphZezo2qdHq5kZk5swspb6kjcbXd363QeGZmSdE94iIWVFRfcyi+SwKVe+2CJV8q46KjCiwGNf9ATsr9P/sndXmE9Dh8gDJ4q0v/zgQnG4XWjjdx2cao/AoM9vGMamzDVlCQ+JWECqvWvLRLa9tAlWgOP4orcQdCh38RRW1oglCeDPVmun7FrTZIXqXcJwMNbFwAnIxXs7rt2kyFb8h72gsQMyuHPb7vcw/kTMNzQjCPvYSuCXQNzJoJ7Dwgc0nH58wNvip9dbr5o4X6wpe8mWh6tRakGrWXzeys2Kt0X4kVn8t3ME9rb8eNlRFoA708cdchhW5VAwIDAQAB";
    //支付宝回调地址
//    public static final String NOTIFY_URL = "http://lmnml.com/openApi/notify/";
    public static final String NOTIFY_URL = "http://www.judianshenghuo.top/vtask/data/alipay/zfbcallback.do";
    public static final String Ali_SM_PAY = "ali";
    public static final String Ali_JS_PAY = "ali";
    public static final String Ali_JS2_PAY = "ali2";

    private static AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY, APP_ID, PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY,"RSA2");

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
        String passbackParams = URLEncoder.encode(aliPay.getAttach());
        model.setPassbackParams(passbackParams);
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL + Ali_JS2_PAY);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return alipayClient.pageExecute(request).getBody();
//            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String smPay(AliPay aliPay) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody(aliPay.getBody());
        model.setSubject(aliPay.getBody());
        model.setTimeoutExpress("30m");
        model.setOutTradeNo(aliPay.getId());
        model.setStoreId(aliPay.getStoreId());
        model.setTotalAmount(StrKit.addPoint(aliPay.getTotal() + ""));
//        model.setProductCode("QUICK_MSECURITY_PAY");
        String passbackParams = URLEncoder.encode(aliPay.getAttach());
        model.setPassbackParams(passbackParams);
        request.setBizModel(model);
        request.setNotifyUrl(NOTIFY_URL );//+ Ali_SM_PAY
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradePrecreateResponse execute= alipayClient.execute(request);
            return execute.getBody();
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
