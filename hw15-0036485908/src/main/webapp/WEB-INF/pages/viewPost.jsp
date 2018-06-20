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
				If you would like to logout, click <a href="../../logout">here</a>
			</h3>
		</c:otherwise>
	</c:choose>

	<h2>Selected blog:</h2>
	<b>Title:</b> ${blogEntry.title}
	<br>
	<b>Text:</b> ${blogEntry.text}
	<br>
	<b>Created At:</b> ${blogEntry.createdAt}
	<br>
	<b>Last Modified At:</b> ${blogEntry.lastModifiedAt}

	<h2>Blog comments:</h2>

	<c:choose>
		<c:when test="${empty requestScope['comments']}">
		This post has no comments yet.
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="comment" items="${comments}">
					<li>${comment.message},by:${comment.usersMail}</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

	<h3>Post New Comment</h3>
	<form action="../postCommentServlet" method="POST">
		Message:<br> <input type="text" name="message"
			placeholder="Please enter your comment" size="30"><br>
			<input type="hidden" name="blogEntryID"	value="${blogEntry.id}">

		<c:if test="${not empty requestScope['invalidComment']}">
			Invalid comment entered.
		</c:if>
		<br> <input type="submit" value="Comment"> <input
			type="reset" value="Clear">
	</form>

</body>
</html>