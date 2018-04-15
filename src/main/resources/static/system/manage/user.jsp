<%@page import="cn.net.vtask.util.CookieTools"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>后台管理系统</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<jsp:include page="../import.jsp"></jsp:include>
</head>
<body>
	<div class="normal-relative">
		<div class="normal-absolute page-title">
			<span class="page-tip-item">权限管理</span><span class="page-tip-item">管理员</span>
		</div>
		<div class="normal-absolute toolbar-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:5px;">
					<span class="layui-btn layui-btn-normal layui-btn-sm" onclick="addUser();">新建管理员</span>
				</div>
				<div class="absolute-right" style="padding:5px;">
					<input class="normal-text" type="text" placeholder="请输入登录账号或姓名" style="margin-right:5px;"/>
					<span class="layui-btn layui-btn-normal layui-btn-sm">搜索</span>
				</div>
			</div>
		</div>
		<div class="normal-absolute data-block">
			<table class="data-table">
				<thead class="data-thead">
					<tr>
						<td>登录账号</td>
						<td>姓名</td>
						<td>角色</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="data-list" class="data-tbody"></tbody>
			</table>
		</div>
		<div class="normal-absolute page-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:15px;line-height:20px;"></div>
				<div class="absolute-right" style="padding:10px;line-height:30px;"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var currentPage = 1, totalPage = 1;
function loadData(){
	$.ajax({
		type: "POST",
		url: "system/user/list.do",
		data: {
			currentPage: currentPage
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$("#data-list").html("");
				$(".page-block .absolute-left").html("<span class=\"page-text\">总<b>"+jsonObject.pageInfo.pageInfo.totalPages+"</b>页，共<b>"+jsonObject.pageInfo.pageInfo.totalRows+"</b>条记录</span>");
				if(jsonObject.pageInfo.pageInfo.totalPages>1){
					totalPage = jsonObject.pageInfo.pageInfo.totalPages;
					$(".page-block .absolute-right").html("<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"firstPage();\">首页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"prevPage();\">上一页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"nextPage();\">下一页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"lastPage();\">尾页</span>"+
						"<input id=\"turn-page-num\" class=\"normal-text\" type=\"text\" style=\"margin:0 5px;vertical-align:-1px;text-align:center;width:50px;\" value=\""+jsonObject.pageInfo.pageInfo.currentPage+"\"/>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"turnPage();\">跳转</span>");
				}
				$.each(jsonObject.data, function(i, n){
					$("#data-list").append("<tr>"+
						"<td>"+n.mobile+"</td>"+
						"<td>"+n.name+"</td>"+
						"<td>"+n.role.name+"</td>"+
						"<td>"+
							(n.id=="admin"?"":"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"repassUser('"+n.id+"');\">重置密码</span><span class=\"layui-btn layui-btn-warm layui-btn-sm\" onclick=\"editUser('"+n.id+"');\">编辑</span><span class=\"layui-btn layui-btn-danger layui-btn-sm\" onclick=\"deleteUser('"+n.id+"');\">删除</span>")+
						"</td>"+
					"</tr>");
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function addUser(){
	window.parent.showMask();
	var roleOptions = "";
	$.ajax({
		type: "POST",
		url: "system/role/queryAll.do",
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$.each(jsonObject.data, function(i, n){
					roleOptions += "<option value=\""+n.id+"\">"+n.name+"</option>";
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
	layerIndex = layer.open({
		type: 1,
		title: "新建管理员",
		area: ['500px', '300px'],
		offset: (document.body.clientHeight/2-300/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['保存', '取消'],
		content: "<div class=\"layer-form-win\">"+
				"<table class=\"layer-form-table\">"+
					"<tr><td class=\"form-title\">管理员角色：</td><td id=\"form-modules\">"+
						"<select id=\"roleId\" class=\"selectpicker\" data-width=\"240px\" data-size=\"5\">"+roleOptions+"</select>"+
					"</td></tr>"+
					"<tr><td class=\"form-title\">管理员电话：</td><td><input id=\"mobile\" class=\"normal-text\" type=\"text\"/></td></tr>"+
					"<tr><td class=\"form-title\">管理员名称：</td><td><input id=\"name\" class=\"normal-text\" type=\"text\"/></td></tr>"+
					"<tr><td class=\"form-title\">管理员密码：</td><td><input id=\"password\" class=\"normal-text\" type=\"text\"/></td></tr>"+
				"</table>"+
			"</div>",
		cancel: function(index, layero){ 
			window.parent.hideMask();
		},
		btn2: function(index, layero){ 
			window.parent.hideMask();
		},
		success: function(){
			$(".selectpicker").selectpicker();
		}, 
		yes: function(index, layero){
			ESTAB.showLoading();
			if($("#mobile").val().trim()==""){
				layer.msg("请填写管理员电话！");
				ESTAB.hideLoading();
				return;
			}
			if($("#name").val().trim()==""){
				layer.msg("请填写管理员姓名！");
				ESTAB.hideLoading();
				return;
			}
			if($("#password").val().trim()==""){
				layer.msg("请填写管理员密码！");
				ESTAB.hideLoading();
				return;
			}
			$.ajax({
				type: "POST",
				url: "system/user/save.do",
				data: {
					roleId: $("#roleId").val(),
					name: $("#name").val(),
					mobile: $("#mobile").val(),
					password: $("#password").val()
				},
				dataType: "text", 
				async:true,
				traditional:true,
				success: function(data) {
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						ESTAB.hideLoading();
						layer.msg("操作成功！");
						firstPage();
						layer.close(index);
						window.parent.hideMask();
					}else{
						ESTAB.hideLoading();
						layer.msg(jsonObject.exception);
					}
				}
			});
		}
	});
}

function editUser(id){
	window.parent.showMask();
	var user = null;
	$.ajax({
		type: "POST",
		url: "system/user/queryById.do",
		data: {
			id: id
		},
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				user = jsonObject.data;
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
	var roleOptions = "";
	$.ajax({
		type: "POST",
		url: "system/role/queryAll.do",
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$.each(jsonObject.data, function(i, n){
					if(n.id==user.role.id){
						roleOptions += "<option value=\""+n.id+"\" selected=\"selected\">"+n.name+"</option>";
					}else{
						roleOptions += "<option value=\""+n.id+"\">"+n.name+"</option>";
					}
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
	layerIndex = layer.open({
		type: 1,
		title: "编辑管理员",
		area: ['500px', '260px'],
		offset: (document.body.clientHeight/2-260/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['保存', '取消'],
		content: "<div class=\"layer-form-win\">"+
				"<table class=\"layer-form-table\">"+
					"<tr><td class=\"form-title\">管理员角色：</td><td id=\"form-modules\">"+
						"<select id=\"roleId\" class=\"selectpicker\" data-width=\"240px\" data-size=\"5\">"+roleOptions+"</select>"+
					"</td></tr>"+
					"<tr><td class=\"form-title\">管理员电话：</td><td><input id=\"mobile\" class=\"normal-text\" type=\"text\" value=\""+user.mobile+"\"/></td></tr>"+
					"<tr><td class=\"form-title\">管理员名称：</td><td><input id=\"name\" class=\"normal-text\" type=\"text\" value=\""+user.name+"\"/></td></tr>"+
				"</table>"+
			"</div>",
		cancel: function(index, layero){ 
			window.parent.hideMask();
		},
		btn2: function(index, layero){ 
			window.parent.hideMask();
		},
		success: function(){
			$(".selectpicker").selectpicker();
		}, 
		yes: function(index, layero){
			ESTAB.showLoading();
			if($("#mobile").val().trim()==""){
				layer.msg("请填写管理员电话！");
				ESTAB.hideLoading();
				return;
			}
			if($("#name").val().trim()==""){
				layer.msg("请填写管理员姓名！");
				ESTAB.hideLoading();
				return;
			}
			$.ajax({
				type: "POST",
				url: "system/user/save.do",
				data: {
					id: user.id,
					roleId: $("#roleId").val(),
					name: $("#name").val(),
					mobile: $("#mobile").val()
				},
				dataType: "text", 
				async:true,
				traditional:true,
				success: function(data) {
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						ESTAB.hideLoading();
						layer.msg("操作成功！");
						firstPage();
						layer.close(index);
						window.parent.hideMask();
					}else{
						ESTAB.hideLoading();
						layer.msg(jsonObject.exception);
					}
				}
			});
		}
	});
}

function repassUser(id){
	window.parent.showMask();
	layerIndex = layer.open({
		type: 1,
		title: "重置密码",
		area: ['500px', '180px'],
		offset: (document.body.clientHeight/2-180/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['保存', '取消'],
		content: "<div class=\"layer-form-win\">"+
				"<table class=\"layer-form-table\">"+
					"<tr><td class=\"form-title\">管理员密码：</td><td><input id=\"password\" class=\"normal-text\" type=\"text\"/></td></tr>"+
				"</table>"+
			"</div>",
		cancel: function(index, layero){ 
			window.parent.hideMask();
		},
		btn2: function(index, layero){ 
			window.parent.hideMask();
		},
		success: function(){
			$(".selectpicker").selectpicker();
		}, 
		yes: function(index, layero){
			ESTAB.showLoading();
			if($("#password").val().trim()==""){
				layer.msg("请填写管理员密码！");
				ESTAB.hideLoading();
				return;
			}
			$.ajax({
				type: "POST",
				url: "system/user/repass.do",
				data: {
					id: id,
					password: $("#password").val()
				},
				dataType: "text", 
				async:true,
				traditional:true,
				success: function(data) {
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						ESTAB.hideLoading();
						layer.msg("操作成功！");
						firstPage();
						layer.close(index);
						window.parent.hideMask();
					}else{
						ESTAB.hideLoading();
						layer.msg(jsonObject.exception);
					}
				}
			});
		}
	});
}

function deleteUser(id){
	window.parent.showMask();
	layerIndex = layer.open({
		type: 1,
		title: "信息",
		area: ['260px', '145px'],
		offset: (document.body.clientHeight/2-145/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['确认', '取消'],
		content: "<div class=\"layer-form-win\" style=\"padding:20px 10px;color:#666666;\">确认要删除这条记录么？</div>",
		cancel: function(index, layero){ 
			window.parent.hideMask();
		},
		btn2: function(index, layero){ 
			window.parent.hideMask();
		},
		yes: function(index, layero){
			ESTAB.showLoading();
			$.ajax({
				type: "POST",
				url: "system/user/delete.do",
				data: {
					id: id
				},
				dataType: "text", 
				async:true,
				traditional:true,
				success: function(data) {
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						ESTAB.hideLoading();
						layer.msg("操作成功！");
						firstPage();
						layer.close(index);
						window.parent.hideMask();
					}else{
						ESTAB.hideLoading();
						layer.msg(jsonObject.exception);
					}
				}
			});
		}
	});
}

function firstPage(){
	currentPage = 1;
	loadData();
}

function prevPage(){
	if(currentPage>1){
		currentPage--;
	}
	loadData();
}

function nextPage(){
	if(currentPage<totalPage){
		currentPage++;
	}
	loadData();
}

function lastPage(){
	currentPage = totalPage;
	loadData();
}

function turnPage(){
	var page = $("#turn-page-num").val();
	var reg = /^[0-9]+$/ ;
    if(reg.test(page)){
    	if(page>=1 && page<=totalPage){
    		currentPage = page;
    	}
    }
	loadData();
}

$(document).ready(function(){
	firstPage();
});
</script>
</html>