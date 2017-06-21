<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contPath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<link rel="stylesheet" href="${contPath}/skins/css/common.css" />
</head>
<body>
	<div class="content">
		<div class="top">
			<%@ include file="../common/top.jsp" %>
		</div>
		<div class="mid">
			<div class="left">
				<%@ include file="../common/left_menu.jsp" %>
			</div>
			<div class="right">
				<div><label>this is User Manager module</label></div>
				<table style="width: 900px; height: 200px" border="1">
					<thead>
						<tr><td>序号</td><td>选择</td><td>name</td><td>account</td><td>password</td><td>role</td></tr>
					</thead>
					<c:forEach var="user" items="${list }" varStatus="s">
						<tr><td>${s.index+1 }</td><td>x</td><td>${user.name }</td><td>${user.account }</td><td>${user.pwd }</td><td>${user.role_id}</td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
<%@ include file="../common/bottom.jsp" %>
</body>
</html>