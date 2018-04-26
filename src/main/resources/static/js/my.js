var app = new Vue({
   el:'#box',
    data:{
       list:'12121',
    },
    methods:{
        fetchData: function () {
            var self = this;
            var body={
                userId:'11',
                type:3,//微信
                total:22,
                openId:'111'
            };
            axios.post(url_set.urlHead+'/pdata/account/recharge',body).then(function (response) {
                self.list = response.data;
                if(body.type==2){
                    self.wxPay(response.data);
                }else if(body.type==3){
                    // console.log(response.data);
                    // app.el.concat(response.data)
                }
            });
        },
        wxPay:function (payData) {
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId":payData.appId,     //公众号名称，由商户传入
                    "timeStamp":payData.timeStamp,         //时间戳，自1970年以来的秒数
                    "nonceStr":payData.nonceStr, //随机串
                    "package":payData.package,
                    "signType":payData.signType,         //微信签名方式：
                    "paySign":payData.paySign //微信签名
                },
                function(res){
                    if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                        // location.href="chatsuccess.html";
                        console.log(支付成功);
                    }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
            )
        }
    }
});