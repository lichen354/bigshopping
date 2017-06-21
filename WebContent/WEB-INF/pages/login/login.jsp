<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<style type="text/css">
.formContent{
	margin-top: 100px;
}
img{
	cursor: pointer;
}
.msg{
	font-size: 12px;
	color: red;
	margin-left: 20%
}
.ch_code, .result{
	display: none;/*需要显示的时候后 我们不再写成 display:block 而是不填 使用的是浏览器默认的样式*/
	
}

</style>
<body>
	<div class="formContent">
		<form id="loginform" action="">
			<table class="login_table" align="center" width="300px" border="0">
				<tr class="tr_result"><td colspan="2" id="t_result"></td></tr>
				<tr><td>用户名</td><td><input type="text" name="username" id="username"></td></tr>
				<tr><td>密码</td><td><input type="password" name="userpwd" id="userpwd"></td></tr>
				<tr class="ch_code"><td>请输入验证码</td><td><input type="text" id="idcode"></td></tr>
				<tr class="ch_code"><td></td><td><img id="checkCode" src="${contPath }/login/checkCode" style="width: 90px; height: 30px"></td></tr>
				<tr><td><input type="checkbox" name="autoLogin" value="1"></td><td><font size="2">下次自动登录</font></td></tr>
				<tr><td><img  src="${contPath }/skins/images/login.png" onclick="checkForm()"></td>
					<td><img  src="${contPath }/skins/images/regist.png"></td></tr>
			</table>
			<input type="hidden" name="token" id="token" value="${sessionScope.token }">
		</form>
	</div>
</body>
<script type="text/javascript" src="${contPath }/skins/js/common/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
var loginNum = 0;
$(document).ready(function(){
	$("#checkCode").click(function(){
		//time变量解决的是IE的缓存问题，添加随机变量使url不同时，IE就会重新刷新缓存，否则IE会直接在缓存中提取数据，而不理会数据是否更新。
		this.src="${contPath }/login/checkCode?time="+Math.random();
	});
	
	$("#idcode").blur(function(){
		checkCode();
	});
});

function checkForm(){
	//表单验证
	var v_username = $("#username").val();
	var v_password = $("#userpwd").val();
	var v_token = $("#token").val();
	var v_autoLogin = $("input[name='autoLogin']:checked").val();
	
	if(loginNum > 2){
		$(".ch_code").removeClass();
//	 	$(".ch_code").css('display',''); //不起作用不知道为什么
		var Rightcode = checkCode();
		if(!Rightcode) return false;
	}
	//ajax登录请求
	$.ajax({
		url : "${contPath}/business/login/check",
		data:{username:v_username,password:v_password,token:v_token,autoLogin:v_autoLogin}
	}).done(function(result) {
		if(result&&result=="success"){
			window.location.href="${contPath }/index";
		}else{
			$("#t_result").html("<font class='msg'>用户名或密码错误</font>");
			$(".tr_result").css('display','');
			loginNum ++;
		}
	}).fail(function(result) {
		alert("服务器异常");
	});
}
function checkCode(){
	var ret = false;
	var v_code = $("#idcode").val();
	if(v_code == '' || v_code == null){
		alert("请输入验证码");
		return ret;
	}
	$.ajax({
		url : "${contPath}/business/login/wrong3timesCheck",
		async : false,
		data:{checkCode:v_code}
	}).done(function(result) {
		if(result&&result=="success"){
			ret = true;
		}else{
			alert("验证码错误");
		}
	}).fail(function(result) {
		alert("服务器异常");
	});
	return ret;
}

</script>
</html>