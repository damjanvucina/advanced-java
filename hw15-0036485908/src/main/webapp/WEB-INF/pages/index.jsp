<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h1>Welcome to our blog!</h1>
	<br>
	<h3>
		Welcome,
		<c:choose>
			<c:when test="${empty sessionScope['current.user.id']}">
		Anonymous user
		
		<h2>Already have an account?</h2>

				<form action="login" method="POST">
					Nickname:<br> <input type="text" name="nickName"
						placeholder="Please enter your nickname" size="30"
						value="${invalidNickname}"><br> <br> Password:<br>
					<input type="password" name="password"
						placeholder="Please enter your password" size="30"> <br>
					<c:if test="${not empty sessionScope['invalid.login']}">
    					Invalid nickname or password
					</c:if>

					<br> <input type="submit" value="Login"> <input
						type="reset" value="Reset">
				</form>
				<br>
				<br>
				<h2>Don't have an account yet</h2>
				<a href="register">Register now</a>
				<br>
			</c:when>
			<c:otherwise>
			
			
		${sessionScope["current.user.nick"]}
		<br>
				<h3>
					If you would like to logout, click <a href="logout">here</a>
				</h3>
			</c:otherwise>
		</c:choose>
	</h3>
	<br>

	<br>
	<h2>List of registered authors</h2>
	<c:choose>
		<c:when test="${registeredAuthors.isEmpty()}">
			<p>There are currently no registered users</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="author" items="${registeredAuthors}">
					<li><c:out value="${author.firstName}" /> <c:out
							value="${author.lastName}" /> (<c:out value="${author.email}" />)
						<a href="author/<c:out value="${author.nickName}"/>">View Posts</a></li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
</body>
</html>