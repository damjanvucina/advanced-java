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
				If you would like to logout, click <a href="../../logout">here</a>
			</h3>
		</c:otherwise>
	</c:choose>

	<h2>Selected blog:</h2>
	<div>
		<b>Title: </b> ${blogEntry.title} <br> <b>Text: </b>
		${blogEntry.text} <br> <b>Author: </b>
		${blogEntry.creator.nickName} <br> <b>Created At: </b>
		${blogEntry.createdAt} <br> <b>Last Modified At: </b>
		${blogEntry.lastModifiedAt}

		<c:if
			test="${blogEntry.creator.nickName eq sessionScope['current.user.nick']}">
			<br>
			<a href="edit?id=${blogEntry.id}"><b>Edit</b></a>
		</c:if>
	</div>


	<h2>Blog comments:</h2>

	<c:choose>
		<c:when test="${empty requestScope['comments']}">
		This post has no comments yet.
		</c:when>
		<c:otherwise>
			<c:forEach var="comment" items="${comments}">
				<div>
					<b>Message: </b>${comment.message}<br> <b>By: </b>${comment.usersEMail}<br>
					<b>Posted At: </b>${comment.postedOn}</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>



	<h3>Post New Comment</h3>
	<form action="/blog/servleti/postCommentServlet" method="POST">
		<c:if test="${empty sessionScope['current.user.id']}">
			Email:<br>
			<input type="text" name="commenterEmail" placeholder="Enter mail"
				size="30">
			<br>
			<c:if test="${not empty requestScope['invalidMail']}">
			Invalid mail entered.
		</c:if>
			<br>
		</c:if>
		Message:<br> <input type="text" name="message"
			placeholder="Comment here" size="30" value="${enteredMessage}"><br>
			 
			<input type="hidden" name="blogEntryID" value="${blogEntry.id}">

		<c:if test="${not empty requestScope['invalidComment']}">
			Invalid comment entered.
		</c:if>
		<br> <input type="submit" value="Comment"> <input
			type="reset" value="Clear">
	</form>

</body>
</html>