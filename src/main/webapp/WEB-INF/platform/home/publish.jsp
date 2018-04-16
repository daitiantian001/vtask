<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>飞腾赚客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../import.jsp"></jsp:include>
	<style type="text/css">
	.form-check-block {
		color: #ed3f14;
	}
	.form-check-block div {
		padding-bottom: 10px;
		font-size: 13px;
	}
	.step-item {
		position: relative;
		padding-bottom: 20px;
	}
	.step-item .step-item-close {
		position: absolute;
		font-size: 14px;
		font-family: 'iconfont';
		top: 0;
		right: 0;
    	font-size: 20px;
    	cursor: pointer;
	}
	.step-item .step-item-close:hover {
		color: #ed3f14;
	}
	</style>
</head>
<body>
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-block">
		<div class="container-bg" style="padding:20px;">
			<div class="task-form">
				<div class="page-title"><span class="title-num">1</span>微任务描述</div>
				<div class="task-detail-form">
					<div class="form-title require">任务类型</div>
					<div class="form-value">
						<select id="categoryId" class="selectpicker" data-width="534px"></select>
					</div>
					<div class="form-title require">任务名称</div>
					<div class="form-value">
						<input id="name" type="text" placeholder="请输入任务名称"/>
					</div>
					<div id="name-tips" class="form-check-block"></div>
					<div class="form-title require">任务数量</div>
					<div class="form-value">
						<input id="num" type="text" value="100"/>
					</div>
					<div id="num-tips" class="form-check-block"></div>
					<div class="form-title require">任务单价（元）</div>
					<div class="form-value">
						<input id="price" type="text" value="1"/>
					</div>
					<div id="price-tips" class="form-check-block"></div>
					<div class="form-title require">截止时间</div>
					<div class="form-value">
						<input id="endTime" class="date-picker" type="text" placeholder="请选择截止时间" readonly="readonly"/>
					</div>
					<div id="endTime-tips" class="form-check-block"></div>
					<div class="form-title require">审核时间</div>
					<div class="form-value">
						<select id="checkTime" class="selectpicker" data-width="534px">
							<option value="1">1个工作日</option>
							<option value="2">2个工作日</option>
							<option value="3">3个工作日</option>
							<option value="4">4个工作日</option>
							<option value="5">5个工作日</option>
							<option value="6">6个工作日</option>
							<option value="7">7个工作日</option>
						</select>
					</div>
					<div class="form-title require">设备限制</div>
					<div class="form-value" style="line-height:36px;">
						<input name="device-limit" type="radio" value="0" checked="checked"/><span class="radio-text">不限</span>
						<input name="device-limit" type="radio" value="1"/><span class="radio-text">Android</span>
						<input name="device-limit" type="radio" value="2"/><span class="radio-text">iOS</span>
					</div>
				</div>
				<div class="page-title"><span class="title-num">2</span>操作步骤</div>
				<div class="task-detail-form">
					<div id="step-list">
						<div class="step-item">
							<div class="form-title step-num">步骤一</div>
							<div class="form-title require">步骤说明</div>
							<div class="form-value">
								<textarea name="stepExplain" placeholder="请输入该步骤的说明"></textarea>
							</div>
							<div class="form-check-block explain-tips"></div>
							<div class="form-title">步骤图示</div>
							<div class="form-value image-block-0">
								<div class="image-uploader" num="0">&#xe664;</div>
								<div class="file-block-0" style="display:none;"><input id="file-0" type="file" accept="image/jpeg, image/jpg, image/png"/></div>
							</div>
							<div class="form-title">关键链接</div>
							<div class="form-value">
								<input name="stepLink" type="text" placeholder="请输入链接地址"/>
							</div>
							<div class="form-check-block link-tips"></div>
						</div>
					</div>
					<div class="form-add-btn" onclick="addStep();"><span>&#xe6da;</span> 新增</div>
				</div>
				<div class="page-title"><span class="title-num">3</span>任务提示</div>
				<div class="task-detail-form">
					<div class="form-title">提交选择</div>
					<div class="form-value" style="line-height:36px;">
						<input name="tip-type" type="radio" value="0" checked="checked"/><span class="radio-text">提交文字内容和截图</span>
						<input name="tip-type" type="radio" value="1"/><span class="radio-text">仅提交文字</span>
						<input name="tip-type" type="radio" value="2"/><span class="radio-text">仅提交截图</span>
					</div>
					<div class="form-title require">文字说明</div>
					<div class="form-value">
						<textarea id="textExplain" placeholder="请输入需要用户提交的文字内容，如：提交文字~"></textarea>
					</div>
					<div class="form-title require">截图说明</div>
					<div class="form-value">
						<textarea id="imgExplain" placeholder="请输入需要用户提交的截图，如：提交app评论后截图~"></textarea>
					</div>
				</div>
				<div class="page-title"><span class="title-num">4</span>联系方式</div>
				<div class="task-detail-form">
					<div class="form-title require">联系人</div>
					<div class="form-value">
						<input id="contactorName" type="text" placeholder="请输入联系人"/>
					</div>
					<div class="form-title require">联系电话</div>
					<div class="form-value">
						<input id="contactorMobile" type="text" placeholder="请输入联系电话"/>
					</div>
					<div class="form-title require">联系邮箱</div>
					<div class="form-value">
						<input id="contactorEmail" type="text" placeholder="请输入联系邮箱"/>
					</div>
				</div>
				<div class="task-detail-form">
					<div class="form-value" style="text-align:center;">
						<input name="agree-check" type="checkbox" checked="checked"/><span class="radio-text">我已阅读并同意 <a class="link-btn">《飞腾赚客发布微任务协议》</a></span>
					</div>
					<div class="submit-btn" onclick="publishTask();">发 布</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
function initCategory(){
	$.ajax({
		type: "POST",
		url: "pdata/category",
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$.each(jsonObject.data, function(i, n){
					$("#categoryId").append("<option value=\""+n.id+"\">"+n.name+"</option>");
				});
				$("#categoryId").selectpicker("refresh");
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

var numArr = ['一', '二', '三', '四', '五'];
function addStep(){
	var num = $(".step-item").size();
	if(num<5){
		$("#step-list").append("<div class=\"step-item\">"+
			"<div class=\"form-title step-num\">步骤"+numArr[num]+"</div>"+
			"<div class=\"form-title require\">步骤说明</div>"+
			"<div class=\"form-value\">"+
				"<textarea name=\"stepExplain\" placeholder=\"请输入该步骤的说明\"></textarea>"+
			"</div>"+
			"<div class=\"form-check-block explain-tips\"></div>"+
			"<div class=\"form-title\">步骤图示</div>"+
			"<div class=\"form-value image-block-"+num+"\">"+
				"<div class=\"image-uploader\" num=\""+num+"\">&#xe664;</div>"+
				"<div class=\"file-block-"+num+"\" style=\"display:none;\"><input id=\"file-"+num+"\" type=\"file\" accept=\"image/jpeg, image/jpg, image/png\"/></div>"+
			"</div>"+
			"<div class=\"form-title\">关键链接</div>"+
			"<div class=\"form-value\">"+
				"<input name=\"stepLink\" type=\"text\" placeholder=\"请输入链接地址\"/>"+
			"</div>"+
			"<div class=\"form-check-block link-tips\"></div>"+
			"<div class=\"step-item-close\">&#xe646;</div>"+
		"</div>");
		$(".step-item-close").click(function(){
			$(this).parent().remove();
			$.each($(".step-num"), function(i, n){
				$(n).html("步骤"+numArr[i]);
			});
		});
		if(num==4) $(".form-add-btn").hide();
	}
	initUploader();
}

function uploadImage(num){
	$("#file-"+num).unbind("change");
	var index = document.getElementById("file-"+num).files[0].name.lastIndexOf(".");
	var ext = document.getElementById("file-"+num).files[0].name.substr(index+1);
	var formData = new FormData();
	formData.append("file", document.getElementById("file-"+num).files[0]);
	formData.append("ext", ext);
	$.ajax({
		type: "POST",
		url: "pdata/image/upload.do",
		data: formData,
		contentType: false,
		processData: false,
		dataType: "text", 
		async: true,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$(".image-block-"+num).prepend("<div class=\"image-uploader-item\">"+
					"<img src=\""+jsonObject.data+"\"/>"+
					"<div class=\"image-uploader-mask\"></div>"+
					"<div class=\"image-uploader-view\">&#xe73d;</div>"+
					"<div class=\"image-uploader-delete\">&#xe6b4;</div>"+
				"</div>");
				$(".file-block-"+num).html("<input id=\"file-"+num+"\" type=\"file\" accept=\"image/jpeg, image/jpg, image/png\"/>");
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function initUploader(){
	$(".image-uploader").unbind("click");
	$(".image-uploader").bind("click", function(){
		var num = $(this).attr("num");
		$("#file-"+num).bind("change", function(){
			uploadImage(num);
		});
		$("#file-"+num).click();
	});
}

function initForm(){
	$("#name").keydown(function(){
		$("#name-tips").html("");
	});
	$("#num").keydown(function(){
		$("#num-tips").html("");
	});
	$("#endTime").change(function(){
		$("#endTime-tips").html("");
	});
	$("textarea[name='stepExplain']").keydown(function(){
		$(this).parent().next().html("");
	});
}

function publishTask(){
	var categoryId = $("#categoryId").val();
	var name = $("#name").val();
	if(name==""){
		$("#name-tips").html("<div>请填写任务名称！</div>");
		$("#name").focus();
		return;
	}else if(name.length<5){
		$("#name-tips").html("<div>任务名称太短啦，至少5个字！</div>");
		$("#name").focus();
		return;
	}else if(name.length>20){
		$("#name-tips").html("<div>任务名称太长啦，最多20个字！</div>");
		$("#name").focus();
		return;
	}
	var reg = /^[0-9]*$/;
	var num = $("#num").val();
	if(num==""){
		$("#num-tips").html("<div>请填写任务数量！</div>");
		$("#num").focus();
		return;
	}else if(!reg.test(num) || parseInt(num)<100 || parseInt(num)>1000000){
		$("#num-tips").html("<div>请输入正确的任务数量，参考值：100 - 1000000！</div>");
		$("#num").focus();
		return;
	}
	var price = $("#price").val();
	if(price==""){
		$("#price-tips").html("<div>请填写任务单价！</div>");
		$("#price").focus();
		return;
	}else if(!reg.test(num) || parseInt(num)<1 || parseInt(num)>1000){
		$("#num-tips").html("<div>请输入正确的任务单价，参考值：1 - 1000！</div>");
		$("#num").focus();
		return;
	}
	var endTimeStr = $("#endTime").val();
	if(endTimeStr==""){
		$("#endTime-tips").html("<div>请选择截止时间！</div>");
		$("#endTime").focus();
		return;
	}
	var checkTime = $("#checkTime").val();
	
	var deviceType = 0;
	$.each($("input[name='device-limit']"), function(i, n){
		if($(n).prop("checked")){
			deviceType = $(n).val();
		}
	});
	
	var submitType = 0;
	$.each($("input[name='tip-type']"), function(i, n){
		if($(n).prop("checked")){
			submitType = $(n).val();
		}
	});
	
	var textExplain = $("#textExplain").val();
	var imgExplain = $("#imgExplain").val();
	if(submitType==0 || submitType==1){
		if(textExplain==""){
			$("#textExplain-tips").html("<div>请填写文字说明！</div>");
			$("#textExplain").focus();
			return;
		}
	}
	
	if(submitType==0 || submitType==2){
		if(imgExplain==""){
			$("#imgExplain-tips").html("<div>请填写图片说明！</div>");
			$("#imgExplain").focus();
			return;
		}
	}
	
	var contactorName = $("#contactorName").val();
	if(contactorName==""){
		$("#contactorName-tips").html("<div>请填写联系人！</div>");
		$("#contactorName").focus();
		return;
	}
	var contactorMobile = $("#contactorMobile").val();
	if(contactorMobile==""){
		$("#contactorMobile-tips").html("<div>请填写联系电话！</div>");
		$("#contactorMobile").focus();
		return;
	}
	var contactorEmail = $("#contactorEmail").val();
	if(contactorEmail==""){
		$("#contactorEmail-tips").html("<div>请填写联系邮箱！</div>");
		$("#contactorEmail").focus();
		return;
	}
	
	var stepNum = new Array();
	var stepExplain = new Array();
	var stepPhoto = new Array();
	var stepLink = new Array();
	
	var stopSubmit = false;
	$.each($(".step-item"), function(i, n){
		stepNum.push(i+1);
		stepExplain.push($(n).find("textarea[name='stepExplain']").val());
		if($(n).find("textarea[name='stepExplain']").val()==""){
			$(n).find("div.explain-tips").html("<div>请填写步骤说明！</div>");
			$(n).find("textarea[name='stepExplain']").focus();
			stopSubmit = true;
			return;
		}
		stepLink.push($(n).find("input[name='stepLink']").val());
		var photos = "";
		$.each($(n).find(".image-uploader-item img"), function(x, y){
			if(photos==""){
				photos += $(y).attr("src");
			}else{
				photos += ";"+$(y).attr("src");
			}
		});
		stepPhoto.push(photos);
	});
	if(stopSubmit){
		return;
	}
	
	$.ajax({
		type: "POST",
		url: "pdata/task/save.do",
		data: {
			categoryId: categoryId,
			name: name,
			num: num,
			price: price,
			endTimeStr: endTimeStr,
			checkTime: checkTime,
			deviceType: deviceType,
			submitType: submitType,
			textExplain: textExplain,
			imgExplain: imgExplain,
			contactorName: contactorName,
			contactorMobile: contactorMobile,
			contactorEmail: contactorEmail,
			stepNum: stepNum, 
			stepExplain: stepExplain, 
			stepPhoto: stepPhoto, 
			stepLink: stepLink
		},
		dataType: "text", 
		async: true,
		traditional: true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				window.location.href = "platform/pay.do?taskId="+jsonObject.data.id;
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

$(document).ready(function(){
	initCategory();
	initUploader();
	initForm();
	
	$(".date-picker").datetimepicker({
        format: "yyyy-mm-dd HH:mm:ss",
        todayHighlight: true,
        minView: 2,
        autoclose: true,
        todayBtn: true
    });
	
});
</script>
</html>