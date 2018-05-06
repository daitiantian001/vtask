var app=new Vue({
    el: '#task_list',
    data: {
        name:'',
        currentPage:0,
        status:[],
        items:[]
    },
    created: function () {
        this.getList();
    },
    filters:{
        doublePrice:function (i) {
            if(!i) return ''
            return parseFloat(i*0.01).toFixed(2)
        },
        fTime:function (i) {
            var date= new Date(i).format("yyyy-MM-dd HH:mm");
            return date;
        },
        fStatus:function (i) {
            switch(i){
                case 0:return '领取任务';
                case 1:return '待审核';
                case 2:return '已完成';
                case 3:return '已被抢光';
                case 4:return '审核不通过';
                case 5:return '待提交';
                default: return'';
            }
        }
    },
    methods:{
        getList:function () {
            var self = this;
            var parm={
                currentPage:self.currentPage,
                name:self.name==''?null:self.name,
                status:null//self.status.size==0?null:self.status
            }
            axios.post('sys/task/userList',JSON.stringify(parm),aConfig).then(function (response) {
                if(response.data.result==1){
                    self.items=response.data.data;
                }else{
                    console.log(response.data.exception);
                }
            });
        },
        doBack:function(id,i) {
            var self = this;
            axios.post('sys/task/updateTask',JSON.stringify({taskId:id,type:i}),aConfig).then(function (r) {
                if(r.data.result==1){
                    self.getList()
                }else{
                    console.log(r.data.exception)
                }

            })
        },
        doPay:function(id) {
            var self = this;
            axios.post('sys/task/updateUserTask',JSON.stringify({taskId:id}),aConfig).then(function (r) {
                if(r.data.result==1){
                    self.getList()
                }else{
                    console.log(r.data.exception)
                }

            })
        },
        exportData:function(i,j) {
            window.location.href=url_set.urlHead+'sys/task/export/'+i+'/'+j
        },
        detail:function (i,j) {
            var self = this;
            axios.post('sys/task/taskInfo',JSON.stringify({taskId:i}),aConfig).then(function (r) {
                if(r.data.result==1){
                    console.log(r.data.data)
                }else{
                    console.log(r.data.exception)
                }
            })
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