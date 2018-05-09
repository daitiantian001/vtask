new Vue({
    el: '#task_check',
    data: {
        currentPage: 0,
        checkType: 1,
        items: []
    },
    created: function () {
        this.getList();
    },
    filters: {
        doublePrice: function (i) {
            if (!i) return ''
            return parseFloat(i * 0.01).toFixed(2)
        },
        fTime: function (i) {
            var date = new Date(i).format("yyyy-MM-dd HH:mm");
            return date;
        },
        divType: function (i) {
            return i == 0 ? '不限' : i == 1 ? 'android' : 'ios'
        }
    },
    methods: {
        getList: function () {
            var self = this;
            var parm = {
                currentPage: self.currentPage,
                taskId: getParam("taskId"),
                checkType: self.checkType
            }
            axios.post('pdata/task/checkList', JSON.stringify(parm), aConfig).then(function (response) {
                if (response.data.result == 1) {
                    self.items = response.data.data;
                } else {
                    console.log(response.data.exception);
                }
            });
        },
        doBack: function (id, i) {
            var self = this;
            axios.post('pdata/task/checkTask', JSON.stringify({userTaskId: id, isAgree: i}), aConfig).then(function (r) {
                if (r.data.result == 1) {
                    self.getList()
                } else {
                    console.log(r.data.exception)
                }

            })
        },
        chose: function (i) {
            this.checkType=i;
            this.getList();
        },
        detail: function (i) {
            var self = this;
            axios.post('sys/task/taskInfo', JSON.stringify({taskId: i}), aConfig).then(function (r) {
                if (r.data.result == 1) {
                    console.log(r.data.data)
                } else {
                    console.log(r.data.exception)
                }
            })
        },
        search: function (i) {
            this.name = i
            this.currentPage = 0
            this.getList();
        },
        page: function (i) {
            var self = this;
            if (i == 0) {
                self.currentPage = 0
            } else {
                self.currentPage = self.currentPage + i
            }
            self.getList()
        }
    }
})