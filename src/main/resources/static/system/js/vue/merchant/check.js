new Vue({
    el: '#merchant_list',
    data: {
        type: 3,// 审核类型  3.个人 4.商户
        name:'',
        currentPage:0,
        items:[]
    },
    created: function () {
        this.getList();
    },
    methods:{
        getList:function () {
            var self = this;
            var parm={
                currentPage:self.currentPage,
                type:self.type,
                name:self.name==''?null:self.name
            }
            axios.post('sys/user/list',JSON.stringify(parm),aConfig).then(function (response) {
                if(response.data.result==1){
                    self.items=response.data.data;
                }else{
                    console.log(response.data.exception);
                }
            });
        },
        doPass:function(id,i) {
            var self = this;
            var j=this.type;
            var t=i==1?j-2:j+2;
            axios.post('sys/user/check',JSON.stringify({id:id,type:t}),aConfig).then(function (r) {
                if(r.data.result==1){
                    self.getList()
                }else{
                    console.log(r.data.exception)
                }

            })
        },
        tag:function () {
            var self = this;
            if(self.type==3){
                self.type=4;
            }else{
                self.type=3
            }
            self.getList();
        },
        search:function (i) {
            this.name=i
            this.currentPage=0
            this.getList();
        },
        page:function (i) {
            var self = this;
            if(i==0){
                self.currentPage=0
            }else{
                self.currentPage=self.currentPage+i
            }
            self.getList()
        }
    }
})