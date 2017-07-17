<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/bootstrap/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}
</style>
</head>
<body>
	<div id="header" style="width: 1000px; margin: 0 auto;">
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Vote</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/user/login.jsp" style="font-family: 黑体; font-size: 12px;">${user eq null?'登录':user.userName }</a></li>
				</ul>
				
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${pageContext.request.contextPath}/vote/publish.jsp" style="font-family: 黑体; font-size: 12px;">${user eq null?'':'发布'}</a></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>
</body>
</html>