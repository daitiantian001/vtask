package com.lmnml.group.common.pay;

import com.lmnml.group.common.model.WxPay;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by daitian on 2017/5/13.
 */
@Component
public class PayUtil {
    private static final String KEY = "p9z8v7b4j5h6u7iioq2we64n7vcx8ds6";
    public static final String APPID = "wx692baa083acfd459";
    private static final String SCRKEY = "";
    public static final String MCH_ID = "1304440901";
    public static final String NOTIFY_URL = "http://lmnml.com/openApi/notify/";
    public static final String WX_JS_PAY = "wx";
    public static final String WX_JS_2_PAY = "wx2";
    private static final String API_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String WX_P12_PATH = PayUtil.class.getClassLoader().getResource("crt/wx/apiclient_cert.p12").getPath();

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    private static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String sign(WxPay wxPay) throws Exception {
        SortedMap<Object, Object> m = new TreeMap<>();
        Field[] fields = wxPay.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            Object object = field.get(wxPay);
            if (null == object || "".equals(object) || object.equals(0) || "key".equals(object) || "sign".equals(object)) {
                continue;
            }
            m.put(fieldName, object);
        }
        return getSign(m);
    }

    public static String getSign(SortedMap<Object, Object> m) {
        StringBuffer sb = new StringBuffer();
        m.forEach((k, v) -> sb.append(k + "=" + v + "&"));
        sb.append("key=" + KEY);
        return MD5Encode(sb.toString(), "UTF-8").toUpperCase();
    }

    public static String getSign(Map<String, String> map) {
        SortedMap<Object, Object> m = new TreeMap<>();
        map.remove("sign");
        m.putAll(map);
        StringBuffer sb = new StringBuffer();
        m.forEach((k, v) -> sb.append(k + "=" + v + "&"));
        sb.append("key=" + KEY);
        return MD5Encode(sb.toString(), "UTF-8").toUpperCase();
    }

    public static String nonceStr() {
        return getRandomStrByLen(30);
    }

    private static String getRandomStrByLen(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static Map<String, String> jsPay(WxPay wxPay) throws Exception {
        Map map = parseXml(doPost(API_URL,toXml(wxPay)));
        SortedMap<String, String> resultMap = new TreeMap<>();
        resultMap.put("appId", wxPay.getAppid());
        resultMap.put("signType", "MD5");
        resultMap.put("package", "prepay_id=" + map.get("prepay_id"));
        resultMap.put("nonceStr", nonceStr());
        resultMap.put("timeStamp", timeStamp());
        resultMap.put("paySign", getSign(resultMap));
        return resultMap;
    }

    public static Map<String, String> parseXml(String strxml) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        return getReturnMap(inputStream);
    }

    private static String timeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        return getReturnMap(inputStream);
    }

    private static String toXml(WxPay wxPay) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Field[] declaredFields = wxPay.getClass().getDeclaredFields();
        Field.setAccessible(declaredFields,true);
        for (Field field : declaredFields) {
            if (field.get(wxPay) != null && !field.get(wxPay).equals(0)) {
                String fieldName = field.getName();
//                if ("attach".equalsIgnoreCase(fieldName)) {
//                    sb.append("<" + fieldName + "><![CDATA[" + field.get(wxPay) + "]]</" + fieldName + ">");
//                } else {
//                    sb.append("<" + fieldName + ">" + field.get(wxPay) + "</" + fieldName + ">");
//                }
                sb.append("<" + fieldName + ">" + field.get(wxPay) + "</" + fieldName + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    private static Map<String, String> getReturnMap(InputStream inputStream) throws DocumentException, IOException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        return map;
    }


    public static String doPost(String url, String parameter) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(parameter);
            // flush输出流的缓冲
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendXMLDataByPost(String url, String assetsPath, String assetsPwd, String xmlData)
            throws IOException {
        String result = "";
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(assetsPath));
            try {
                keyStore.load(instream, assetsPwd.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, assetsPwd.toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(xmlData);
            post.setEntity(entity);
            post.setHeader("Content-Type", "text/xml;charset=UTF-8");
            HttpResponse response = httpclient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resultEntity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(resultEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
