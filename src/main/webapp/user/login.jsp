<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body style="padding: 0px; margin: 0px;">
	<jsp:include page="../common/header.jsp"></jsp:include>

	<div id="login" style="">
		<form action="${pageContext.request.contextPath }/user?action=login" method="post">
			<input type="text" name="userName"> <input type="text"
				name="password"> <input type="submit">
		</form>
	</div>

	<jsp:include page="../common/footter.jsp"></jsp:include>
</body>
</html>