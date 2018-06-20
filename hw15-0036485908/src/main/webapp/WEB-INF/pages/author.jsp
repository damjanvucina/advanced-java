<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style>
div {
	padding: 5px;
	border: 2px solid gray;
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
				If you would like to logout, click <a href="../logout">here</a>
			</h3>
		</c:otherwise>
	</c:choose>

	<br>
	<h2>Blog Entries:</h2>

	<c:choose>
		<c:when test="${empty requestScope['owner']}">
		</c:when>
		<c:otherwise>
			<a href="${sessionScope['current.user.nick']}/new">Add New Post</a>
		</c:otherwise>
	</c:choose>	
	<br><br>
	<c:choose>
		<c:when test="${empty requestScope['userEntries']}">
		This user does not have any posts yet.
		</c:when>
		<c:otherwise>
			<c:forEach var="entry" items="${userEntries}">
				<div>
					<a href="${entry.creator.nickName}/${entry.id}">${entry.title}</a>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<br>
	<br>

</body>
</html>