<%@ page import="java.util.Date,java.util.Calendar"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h1>Welcome to our blog!</h1>
	<br>
	<br>
	<h2>Already have an account?</h2>

	<form action="login" method="POST">
		Nickname:<br> <input type="text" name="nick" placeholder="Please enter your nickname" size="30"><br><br>
		Password:<br> <input type="password" name="password" placeholder="Please enter your password" size="30"><br><br>
		<input type="submit" value="Login"><input type="reset"
			value="Reset">
	</form>
	<br><br>
	<h2>Don't have an account yet</h2>
	<a href="servleti/register"></a>
	<br><br>
		<h2>List of registered authors</h2>
	<a href="servleti/register"></a>
</body>
</html>