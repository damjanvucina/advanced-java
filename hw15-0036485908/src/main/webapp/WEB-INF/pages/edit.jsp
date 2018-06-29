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

	<h2>Edit Existing Post</h2>

	<c:choose>
		<c:when test="${sessionScope['current.user.id'] eq creator.id}">

			<form action="./../../../servleti/updatePost" method="POST">
				Title:<br> <input type="text" name="title"
					placeholder="Please enter post title" size="30"
					value="${enteredTitle}"><br> <br> Text:<br>
				<input type="text" name="text" placeholder="Please enter post text"
					size="30" value="${enteredText}"> <br> <br> <input
					type="hidden" name="entryID" value="${enteredID}"> <br>

				<c:if test="${not empty requestScope['invalidPost']}">
					Invalid title or text
				</c:if>
				<br> <input type="submit" value="Post"> <input
					type="reset" value="Clear">
			</form>
		</c:when>

		<c:otherwise>
		You do not have permission to edit this post
		</c:otherwise>
	</c:choose>
</body>
</html>