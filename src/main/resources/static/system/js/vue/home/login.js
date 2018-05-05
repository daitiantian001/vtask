var app = new Vue({
    el:'#box',
    data:{
        mobile:'',
        password:''
    },
    methods:{
        login:function () {
            console.log(11111111111)
            // var self = this;
            // var body={mobile:self.mobile,password:self.password};
            // axios.post(url_set.urlHead+'/sys/user/login',body).then(function (response) {
            //     self.list = response.data;
            // });
        }
    }
});