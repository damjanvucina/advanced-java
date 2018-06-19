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
	<br> BlogEntries List:
	<br>
	<br>
	<c:choose>
		<c:when test="${empty requestScope['userEntries']}">
		This user does not have any posts yet.
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="entry" items="${userEntries}">
					<li>${entry.title}</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
	<br><br>
	<c:choose>
		<c:when test="${empty requestScope['owner']}">
		Cannot edit other user's profile
		</c:when>
		<c:otherwise>
			<br>
			Add new entry
			Edit entry
		</c:otherwise>
	</c:choose>

</body>
</html>