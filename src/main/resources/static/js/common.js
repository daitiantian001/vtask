//路径请求统一配置
const url_set = {
    // urlHead: 'http://localhost:7055/',
    urlHead: 'http://lmnml.com/',
    raffleId: getParam('raffleId'),
    token: getParam('token')
}

function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
const aConfig={
    baseURL:url_set.urlHead,
}
// axios.defaults.baseURL =url_set.urlHead;
// // axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
