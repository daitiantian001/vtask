package com.lmnml.group.common.model;

import com.lmnml.group.common.pay.PayUtil;
import lombok.Data;

/**
 * Created by daitian on 2018/4/19.
 */
@Data
public class WxPay {

    //===================公共属性=======================//
    //公众账号ID
    private String appid;
    //商户号
    private String mch_id;
    private String nonce_str;
    private String sign;
    //商品描述(128) APP名字-实际商品名称
    private String body;
    //商户系统内部订单号
    private String out_trade_no;
    //订单总金额，单位为分
    private int total_fee;
    //终端ip
    private String spbill_create_ip;
    //异步接收微信支付结果通知的回调地址 不能带参数
    private String notify_url;
    //交易类型 H5为MWEB,
    private String trade_type;

    //=======================带条件必传=============================//
    //IOS移动应用
    //    {"h5_info": {"type":"IOS","app_name": "王者荣耀","bundle_id": "com.tencent.wzryIOS"}}
    //安卓移动应用
    //    {"h5_info": {"type":"Android","app_name": "王者荣耀","package_name": "com.tencent.tmgp.sgame"}}
    //WAP网站应用
    //    {"h5_info": {"type":"Wap","wap_url": "https://pay.qq.com","wap_name": "腾讯充值"}}
    private String scene_info;
    //trade_type=NATIVE，此参数必传
    private String product_id;
    //trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
    private String openid;

    //=========================非必传属性===============================//
    //附加数据(127)
    private String attach;
    //终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    private String device_info;
    //签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
    private String sign_type;
    //单品优惠字段
    private String detail;
    //交易起始时间格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
    private String time_start;
    //yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
    private String time_expire;
    //商品标记
    private String goods_tag;

    //==============================退款属性===================================
    private int refund_fee;
    private String out_refund_no;

    //=================================企业付款属性=======================================
    private String mch_appid;
    private String mchid;
    private String partner_trade_no;
    private String check_name;//NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
    private String re_user_name;
    private int amount;
    private String desc;

    private WxPay() {
        this.appid = PayUtil.APPID;
        this.mch_id = PayUtil.MCH_ID;
        this.notify_url=PayUtil.NOTIFY_URL;
        this.nonce_str = PayUtil.nonceStr();
    }

    private WxPay(String body,String url,String tradeType,String out_trade_no,int total_fee,String attach,String spbill_create_ip, String openid,String product_id,String scene_info) throws Exception {
        this();
        this.trade_type=tradeType;
        this.body=body;
        this.notify_url+=url;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.openid = openid;
        this.product_id=product_id;
        this.scene_info=scene_info;
        this.attach=attach;
        this.sign = PayUtil.sign(this);
    }

    private WxPay(String out_trade_no,String out_refund_no,Integer total_fee,Integer refund_fee) throws Exception {
        this.appid = PayUtil.APPID;
        this.mch_id = PayUtil.MCH_ID;
        this.nonce_str = PayUtil.nonceStr();
        this.out_trade_no=out_trade_no;
        this.out_refund_no=out_refund_no;
        this.total_fee=total_fee;
        this.refund_fee=refund_fee;
        this.sign = PayUtil.sign(this);
    }
    //
    private WxPay(String partner_trade_no,String openid,int amount,String desc,String ip) throws Exception {
        this.mch_appid = PayUtil.APPID;
        this.mchid = PayUtil.MCH_ID;
        this.nonce_str = PayUtil.nonceStr();
        this.partner_trade_no=partner_trade_no;
        this.check_name="NO_CHECK";//"FORCE_CHECK";
        this.amount=amount;
        this.desc=desc;
        this.spbill_create_ip=ip;
        this.openid=openid;
        this.sign = PayUtil.sign(this);
    }

    /**
     *
     * @param body 商品简单描述
     * @param out_trade_no 商户订单号
     * @param total_fee 标价金额
     * @param attach 附加数据
     * @param spbill_create_ip 终端IP
     * @param scene_info 场景信息
     * @return
     * @throws Exception
     */
    public static WxPay h5Pay(String body,String out_trade_no, int total_fee, String attach, String spbill_create_ip, String scene_info) throws Exception {
        return new WxPay(body,PayUtil.WX_JS_PAY,WxPayEnum.H5.getType(),out_trade_no,total_fee,attach,spbill_create_ip,null,null,scene_info);
    }

    /**
     *
     * @param body 商品简单描述
     * @param out_trade_no 商户订单号
     * @param total_fee 标价金额
     * @param attach 附加数据
     * @param spbill_create_ip 终端IP
     * @return
     * @throws Exception
     */
    public static WxPay appPay(String body, String out_trade_no, int total_fee, String attach, String spbill_create_ip) throws Exception {
        return new WxPay(body,PayUtil.WX_JS_PAY,WxPayEnum.APP.getType(),out_trade_no,total_fee,attach,spbill_create_ip,null,null,null);
    }

    /**
     *
     * @param body 商品简单描述
     * @param out_trade_no 商户订单号
     * @param total_fee 标价金额
     * @param attach 附加数据
     * @param spbill_create_ip 终端IP
     * @param openid 用户标识
     * @return
     * @throws Exception
     */
    public static WxPay jsPay(String body, String out_trade_no, int total_fee, String attach, String spbill_create_ip, String openid) throws Exception {
        return new WxPay(body,PayUtil.WX_JS_PAY,WxPayEnum.JS.getType(),out_trade_no,total_fee,attach,spbill_create_ip,openid,null,null);
    }
    public static WxPay js2Pay(String body, String out_trade_no, int total_fee, String attach, String spbill_create_ip, String openid) throws Exception {
        return new WxPay(body,PayUtil.WX_JS_2_PAY,WxPayEnum.JS.getType(),out_trade_no,total_fee,attach,spbill_create_ip,openid,null,null);
    }

    /**
     *
     * @param body 商品简单描述
     * @param out_trade_no 商户订单号
     * @param total_fee 标价金额
     * @param attach 附加数据
     * @param spbill_create_ip 终端IP
     * @param product_id 商品id
     * @return
     * @throws Exception
     */
    public static WxPay smPay(String body, String out_trade_no, int total_fee, String attach, String spbill_create_ip, String product_id) throws Exception {
        return new WxPay(body,PayUtil.WX_SM_PAY,WxPayEnum.SM.getType(),out_trade_no,total_fee,attach,spbill_create_ip,null,product_id,null);
    }

    /**
     * 退款接口
     * @param out_trade_no 订单号
     * @param out_refund_no 退款号（任务id防止重复提交）
     * @param total_fee 总额
     * @param refund_fee 退款金额
     * @return
     * @throws Exception
     */
    public static WxPay smPayBack(String out_trade_no, String out_refund_no,int total_fee,int refund_fee) throws Exception {
        return new WxPay(out_trade_no,out_refund_no,total_fee,refund_fee);
    }

    /**
     * 微信企业付款
     * @param partner_trade_no 订单号
     * @param openid 微信openId
     * @param amount 金额
     * @param desc 描述
     * @param ip ip
     * @return
     * @throws Exception
     */
    public static WxPay companyPay(String partner_trade_no,String openid,int amount,String desc,String ip) throws Exception {
        return new WxPay(partner_trade_no,openid,amount,desc,ip);
    }
}
