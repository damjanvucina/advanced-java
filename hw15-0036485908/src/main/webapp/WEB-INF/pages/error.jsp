<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h1>Error occurred</h1>
	<br>
	<h2>Error description:</h2>
	<br>
	<p>${errorMessage}</p>
	<br>
</body>
</html>