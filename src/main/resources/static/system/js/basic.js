var baseURL = "http://www.letsgetripped.cn/";
var ESTAB = {
	wxajax:function(url,dataparam,async,_function){
		$.ajax({
			type: "POST",
			url:url,
			data:dataparam,
			dataType: "text", 
			async:async,
			traditional:true,
			error: function(request) {
			
			},
			success: function(data) {
				_function(data);
			}
		});
	},
	ajax:function(url,dataparam,async,_function){
		$.ajax({
			type: "POST",
			url:url,
			data:dataparam,
			dataType: "text", 
			async:async,
			traditional:true,
			error: function(request) {
			
			},
			success: function(data) {
				var jsonObj = eval('(' + data + ')');
				if(jsonObj.result=="failure" && jsonObj.exception=="nosession"){
					window.location.href = "/yuejinStore/system"
				}
				_function(jsonObj);
			}
		});
	},
	ajaxFile:function(url,dataparam,async,_function){
		$.ajax({
			type: "POST",
			url:url,
			data:dataparam,
			dataType: "text", 
			async:async,
			traditional:true,
			processData:false,
			error: function(request) {
			
			},
			success: function(data) {
				var jsonObj = eval('(' + data + ')');
				_function(jsonObj);
			}
		});
	},
	appajax:function(url,dataparam,async,_function){
		var dataStr = "";
		for(var key in dataparam){
			if(dataStr!="") dataStr += "&";  
		   dataStr += (key + "=" + dataparam[key]);  
		}
		$.ajax({  
		        type:'post',  
		        url : url+'?'+dataStr,  
		        dataType:'jsonp',  
		        jsonp:"jsoncallback",  
		        success:function(data) { 
					var jsonObj = eval('(' + data + ')');
					_function(jsonObj);
		        },  
		        error:function(data) {
		        	alert(data);
		        	var jsonObj = eval('(' + data + ')');
					_function(jsonObj);  
		        }  
		    }  
		); 
		/**
		try { xmlHttp = new XMLHttpRequest(); } 
		catch (trymicrosoft) {
			try { xmlHttp = new ActiveXObject("Msxml2.XMLHTTP"); } 
			catch (othermicrosoft) {  
				try { xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); } 
				catch (failed) { xmlHttp = null; }
			}
		}
		if ( xmlHttp!=null ) {
			var data = "";
			for(var key in dataparam){
				if(data!="") data += "&";  
			   data += (key + "=" + dataparam[key]);  
			}
			xmlHttp.open("POST", url+"?"+data, true); 
			xmlHttp.onreadystatechange = function(){
				if ( xmlHttp.readyState==4 ) { 
    				if ( xmlHttp.status==200 ) alert("SUCCEED!\n"+xmlHttp.responseText);
    				else alert("ERROR! Status="+xmlHttp.status);
				}
			};
			xmlHttp.setRequestHeader("Content-type", "application/xml");
			xmlHttp.setRequestHeader("Content-length", data.length);
			xmlHttp.setRequestHeader("Connection", "close");
			xmlHttp.setRequestHeader("Access-Control-Allow-Origin", "*");
			xmlHttp.send("");														 		   
		} else {
			alert("Couldn't Create XMLHTTP!");
		}
		**/
	},
	uuid:function(){
		var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "-";
	    var uuid = s.join("");
	    return uuid;
	},
	orderid:function(){
		var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "";
	    var uuid = s.join("");
	    return uuid;
	},
	orderid17:function(){
		var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 18; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "";
	    var uuid = s.join("");
	    return uuid.toUpperCase();
	},
	showLoading:function(tips){
		if(tips==null) tips = "加载中";
		if($(".loading-window", window.top.document)!=null){
			$("body", window.top.document).append(
				"<div class=\"loading-window\" style=\"position:fixed;bottom:0;left:0;right:0;top:0;background:rgba(0, 0, 0, 0.4);z-index:1000;\">"+
					"<div style=\"position:relative;width:100%;height:100%;\"><div style=\"position:absolute;top:50%;left:50%;margin-top:-42px;margin-left:-40px;\">"+
						"<div class=\"spinner\"><div class=\"double-bounce1\"></div><div class=\"double-bounce2\"></div></div>"+
						"<div class=\"loading\"><span></span><span></span><span></span><span></span><span></span></div>"+
						"<div class=\"loading-text\" style=\"color:#ffffff;\">"+tips+"</div>"+
					"</div></div>"+
				"</div>");
		}
	},
	hideLoading:function(){
		$(".loading-window", window.top.document).fadeOut(300, function(){
			$(".loading-window", window.top.document).remove();
		});
	}
};

/* 
*  方法:Array.remove(dx) 通过遍历,重构数组 
*  功能:删除数组元素. 
*  参数:dx删除元素的下标. 
*/ 
Array.prototype.remove=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){return false;} 
    for(var i=0,n=0;i<this.length;i++) 
    { 
        if(this[i]!=this[dx]) 
        { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
} 

//对Date的扩展，将 Date 转化为指定格式的String 
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function(fmt) 
{ //author: meizz 
	var o = { 
			"M+" : this.getMonth()+1,                 //月份 
			"d+" : this.getDate(),                    //日 
			"H+" : this.getHours(),                   //小时 
			"m+" : this.getMinutes(),                 //分 
			"s+" : this.getSeconds(),                 //秒 
			"q+" : Math.floor((this.getMonth()+3)/3), //季度 
			"S"  : this.getMilliseconds()             //毫秒 
	}; 
	if(/(y+)/.test(fmt)) 
		fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	for(var k in o) 
		if(new RegExp("("+ k +")").test(fmt)) 
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	return fmt; 
}
Date.prototype.ages = function() 
{
	var datestr = new Date(this).format("yyyy-MM-dd");
	var r = datestr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(r==null)return false;     
    var d = new Date(r[1], r[3]-1, r[4]);     
    if   (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4])   
    {   
          var Y = new Date().getFullYear();   
          return (Y-r[1]);   
    }   
}

function bytesToSize(bytes) {  
	if (bytes === 0) return '0 B';  
	var k = 1024;  
    sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];  
	i = Math.floor(Math.log(bytes) / Math.log(k));  
	return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];   
	//toPrecision(3) 后面保留一位小数，如1.0GB                                                                                                                  //return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];  
}

String.prototype.replaceAll = function (FindText, RepText) {
    regExp = new RegExp(FindText, "g");
    return this.replace(regExp, RepText);
}

Array.prototype.contains = function ( needle ) {
  for (i in this) {
    if (this[i] == needle) return true;
  }
  return false;
}

function setCookie(name, value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) {
	var arr, reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2].replaceAll("\"",""));
	else
		return null;
}

function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}