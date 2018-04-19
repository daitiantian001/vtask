package com.lmnml.group.common.model;

/**
 * Created by daitian on 2018/4/19.
 */
public enum WxPayEnum {
    JS("小程序/公众号", "JSAPI", "openid"),
    H5("H5支付", "MWEB", "scene_info"),
    SM("扫码支付", "NATIVE", "product_id"),
    APP("APP支付", "APP", "");

   private String name;
   private String type;
   private String content;

    WxPayEnum(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
