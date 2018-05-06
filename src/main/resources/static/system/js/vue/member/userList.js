new Vue({
    el: '#user-list',
    data: {
        name:'',
        currentPage:0,
        // status:[2],
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
        sexType:function (i) {
            return i==0?'保密':i==1?'男':'女'
        }
    },
    methods:{
        getList:function () {
            var self = this;
            var parm={
                currentPage:self.currentPage,
                name:self.name==''?null:self.name,
                // status:self.status
            }
            axios.post('sys/user/userList',JSON.stringify(parm),aConfig).then(function (response) {
                if(response.data.result==1){
                    self.items=response.data.data;
                }else{
                    console.log(response.data.exception);
                }
            });
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