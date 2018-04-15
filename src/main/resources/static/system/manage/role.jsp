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
			<span class="page-tip-item">权限管理</span><span class="page-tip-item">角色权限</span>
		</div>
		<div class="normal-absolute toolbar-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:5px;">
					<span class="layui-btn layui-btn-normal layui-btn-sm" onclick="addRole();">新建角色</span>
				</div>
				<div class="absolute-right" style="padding:5px;">
					<input class="normal-text" type="text" placeholder="请输入角色名称" style="margin-right:5px;"/>
					<span class="layui-btn layui-btn-normal layui-btn-sm">搜索</span>
				</div>
			</div>
		</div>
		<div class="normal-absolute data-block">
			<table class="data-table">
				<thead class="data-thead">
					<tr>
						<td>角色名称</td>
						<td>角色权限</td>
						<td style="width:160px;">操作</td>
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
		url: "system/role/list.do",
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
						"<td>"+n.name+"</td>"+
						"<td id=\"role-"+n.id+"\"></td>"+
						"<td>"+
							(n.id=="superuser"?"":"<span class=\"layui-btn layui-btn-warm layui-btn-sm\" onclick=\"editRole('"+n.id+"');\">编辑</span><span class=\"layui-btn layui-btn-danger layui-btn-sm\" onclick=\"deleteRole('"+n.id+"');\">删除</span>")+
						"</td>"+
					"</tr>");
					$.each(n.modules.sort(function(a,b){return a.id-b.id}), function(x, y){
						if(y.pId=="0"){
							$("#role-"+n.id).append("<div class=\"modules-item module-"+y.id+"\"><span class=\"module-parent\"><i>"+y.icon+"</i>"+y.name+"</span></div>");
						}else{
							$("#role-"+n.id+" .module-"+y.pId).append("<span class=\"module-child\"><i>&#xe6d6;</i>"+y.name+"</span>");
						}
					});
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function addRole(){
	window.parent.showMask();
	layerIndex = layer.open({
		type: 1,
		title: "新建角色",
		area: ['600px', '360px'],
		offset: (document.body.clientHeight/2-360/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['保存', '取消'],
		content: "<div class=\"layer-form-win\">"+
				"<table class=\"layer-form-table\">"+
					"<tr><td class=\"form-title\">角色名称：</td><td><input id=\"name\" class=\"normal-text\" type=\"text\"/></td></tr>"+
					"<tr><td class=\"form-title\">角色权限：</td><td id=\"form-modules\">"+
					"</td></tr>"+
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
			$.ajax({
				type: "POST",
				url: "system/index/home.do",
				data: {
					id: getCookie("userId")
				},
				dataType: "text", 
				async: true,
				traditional: true,
				success: function(data){
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						$.each(jsonObject.data.role.modules.sort(function(a,b){return a.id-b.id}), function(i, n){
							if(n.pId=="0"){
								$("#form-modules").append("<div id=\""+n.id+"\" class=\"modules-block\">"+
									"<div class=\"modules-item-parent\"><input class=\"role-check\" name=\"moduleId\" type=\"checkbox\" value=\""+n.id+"\"/><span>"+n.name+"</span></div>"+
								"</div>");
							}else{
								$("#"+n.pId).append("<div class=\"modules-item-child\"><input class=\"role-check\" name=\"moduleId\" type=\"checkbox\" value=\""+n.id+"\"/>"+n.name+"</div>");
							}
						});
					}else{
						layer.msg(jsonObject.exception);
					}
				}
			});
		}, 
		yes: function(index, layero){
			ESTAB.showLoading();
			if($("#name").val().trim()==""){
				layer.msg("请填写角色名称！");
				ESTAB.hideLoading();
				return;
			}
			var moduleIds = new Array();
			$.each($(".role-check"), function(i, n){
				if(n.checked){
					moduleIds.push($(n).val());
				}
			});
			if(moduleIds.length==0){
				layer.msg("请选择角色权限！");
				ESTAB.hideLoading();
				return;
			}
			$.ajax({
				type: "POST",
				url: "system/role/save.do",
				data: {
					name: $("#name").val(),
					moduleIds: moduleIds
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

function editRole(id){
	window.parent.showMask();
	var role = null;
	$.ajax({
		type: "POST",
		url: "system/role/queryById.do",
		data: {
			id: id
		},
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				role = jsonObject.data;
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
	layerIndex = layer.open({
		type: 1,
		title: "编辑角色",
		area: ['600px', '360px'],
		offset: (document.body.clientHeight/2-360/2)+'px',
		shadeClose: false, //点击遮罩关闭
		btn: ['保存', '取消'],
		content: "<div class=\"layer-form-win\">"+
				"<table class=\"layer-form-table\">"+
					"<tr><td class=\"form-title\">角色名称：</td><td><input id=\"name\" class=\"normal-text\" type=\"text\" value=\""+role.name+"\"/></td></tr>"+
					"<tr><td class=\"form-title\">角色权限：</td><td id=\"form-modules\">"+
					"</td></tr>"+
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
			var modules = new Array();
			$.each(role.modules, function(i, n){
				modules.push(n.id);
			});
			$.ajax({
				type: "POST",
				url: "system/index/home.do",
				data: {
					id: getCookie("userId")
				},
				dataType: "text", 
				async: true,
				traditional: true,
				success: function(data){
					var jsonObject = eval('(' + data + ')');
					if(jsonObject.result==1){
						$.each(jsonObject.data.role.modules.sort(function(a,b){return a.id-b.id}), function(i, n){
							var checked = "";
							if(modules.indexOf(n.id)>=0){
								checked = " checked=\"checked\"";
							}
							if(n.pId=="0"){
								$("#form-modules").append("<div id=\""+n.id+"\" class=\"modules-block\">"+
									"<div class=\"modules-item-parent\"><input class=\"role-check\" name=\"moduleId\" type=\"checkbox\" value=\""+n.id+"\" "+checked+"/><span>"+n.name+"</span></div>"+
								"</div>");
							}else{
								$("#"+n.pId).append("<div class=\"modules-item-child\"><input class=\"role-check\" name=\"moduleId\" type=\"checkbox\" value=\""+n.id+"\" "+checked+"/>"+n.name+"</div>");
							}
						});
					}else{
						layer.msg(jsonObject.exception);
					}
				}
			});
		}, 
		yes: function(index, layero){
			ESTAB.showLoading();
			if($("#name").val().trim()==""){
				layer.msg("请填写角色名称！");
				ESTAB.hideLoading();
				return;
			}
			var moduleIds = new Array();
			$.each($(".role-check"), function(i, n){
				if(n.checked){
					moduleIds.push($(n).val());
				}
			});
			if(moduleIds.length==0){
				layer.msg("请选择角色权限！");
				ESTAB.hideLoading();
				return;
			}
			$.ajax({
				type: "POST",
				url: "system/role/save.do",
				data: {
					id: role.id,
					name: $("#name").val(),
					moduleIds: moduleIds
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

function deleteRole(id){
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
				url: "system/role/delete.do",
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