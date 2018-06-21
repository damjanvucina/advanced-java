<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style>
div {
	padding: 5px;
	border: 2px solid red;
	margin: 2;
}
</style>
<body>
	Current User:
	<c:choose>
		<c:when test="${empty sessionScope['current.user.id']}">
		Anonymous user
		</c:when>
		<c:otherwise>
		${sessionScope["current.user.nick"]}
		<br>
			<h3>
				If you would like to logout, click <a href="./logout">here</a>
			</h3>
		</c:otherwise>
	</c:choose>
	<h1>Error occurred</h1>
	<br>
	<h2>Error description:</h2>
	<br>
	<div>
		<p>Warning: ${errorMessage}</p>
	</div>
	<br>
</body>
</html>