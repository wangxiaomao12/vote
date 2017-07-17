<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vote index page</title>
</head>
<body style="margin: 0px; margin: 0px;">
	<jsp:include page="common/header.jsp"></jsp:include>
	<%-- <jsp:include page="${pageContent }"></jsp:include> --%>
	<div id="index_main" style="width: 1000px; margin: 0 auto;">


		<div class="panel panel-default">
			<div class="panel-heading">最新投票</div>


			<div class="panel-body">
				<c:forEach items="${voteList }" var="vote">
					<div class="media">
						<div class="media-left media-middle">
							<a href="#"> <img class="media-object"
								src="${pageContext.request.contextPath }/image/default_head.png"
								style="width: 100px; border: 1px solid #333;">
							</a>
						</div>
						<div class="media-left media-middle">
							<button type="button" class="btn btn-default">
								<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true">${ vote.votes}</span>
							</button>
							<br /> <br />
							<button type="button" class="btn btn-default">
								<span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span>
							</button>
						</div>
						<div class="media-body">
							<h4 class="media-heading">${vote.title }</h4>
							${vote.content }
						</div>
					</div>

				</c:forEach>
			</div>
		</div>


	</div>
	<jsp:include page="common/footter.jsp"></jsp:include>
</body>
</html>