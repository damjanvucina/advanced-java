<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
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
	<br> <h2>Blog Entries:</h2>
	<br>
	<br>
	<c:choose>
		<c:when test="${empty requestScope['userEntries']}">
		This user does not have any posts yet.
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="entry" items="${userEntries}">
					<li> <a href="${entry.creator.nickName}/${entry.id}">${entry.title}</a></li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>
	<br>
	<br>
	<c:choose>
		<c:when test="${empty requestScope['owner']}">
		</c:when>
		<c:otherwise>
			<br>
			<a href="${sessionScope['current.user.nick']}/new">Add New Post</a>
		</c:otherwise>
	</c:choose>

</body>
</html>