<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<h1>Available Polls</h1>

	<ol>
		<c:forEach var="poll" items="${pollList}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>