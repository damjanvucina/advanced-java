<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body bgcolor="${pickedBgCol}">
	<h1>Vote for your favorite band</h1>

	<ol>
		<c:forEach var="option" items="${pollOptions}">
			<li><a href="glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>

		</c:forEach>
	</ol>
</body>
</html>