<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Registration</title>

<style type="text/css">
.error {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>

<body>
	<h1>
		<c:choose>
			<c:when test="${sheet.id.isEmpty()}">
		New User
		</c:when>
			<c:otherwise>
		Edit User
		</c:otherwise>
		</c:choose>
	</h1>

	<form action="validateRegistration" method="post">

		<div>
			<div>
				<span class="formLabel">First Name</span><input type="text"
					name="firstName" value='<c:out value="${sheet.firstName}"/>'
					size="50">
			</div>
			<c:if test="${sheet.hasError('firstNameError')}">
				<div class="error">
					<c:out value="${sheet.acquireError('firstNameError')}" />
				</div>
			</c:if>
		</div>
		<br>

		<div>
			<div>
				<span class="formLabel">Last Name</span><input type="text"
					name="lastName" value='<c:out value="${sheet.lastName}"/>'
					size="50">
			</div>
			<c:if test="${sheet.hasError('lastNameError')}">
				<div class="error">
					<c:out value="${sheet.acquireError('lastNameError')}" />
				</div>
			</c:if>
		</div>
		<br>

		<div>
			<div>
				<span class="formLabel">Nick Name</span><input type="text"
					name="nickName" value='<c:out value="${sheet.nickName}"/>'
					size="50">
			</div>
			<c:if test="${sheet.hasError('nickNameError')}">
				<div class="error">
					<c:out value="${sheet.acquireError('nickNameError')}" />
				</div>
			</c:if>
		</div>
		<br>

		<div>
			<div>
				<span class="formLabel">EMail</span><input type="text" name="email"
					value='<c:out value="${sheet.email}"/>' size="50">
			</div>
			<c:if test="${sheet.hasError('emailError')}">
				<div class="error">
					<c:out value="${sheet.acquireError('emailError')}" />
				</div>
			</c:if>
		</div>
		<br>

		<div>
			<div>
				<span class="formLabel">Password</span><input type="password"
					name="password" value='<c:out value="${sheet.password}"/>'
					size="50">
			</div>
			<c:if test="${sheet.hasError('passwordError')}">
				<div class="error">
					<c:out value="${sheet.acquireError('passwordError')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Register"> <input type="submit"
				name="method" value="Cancel">
		</div>

	</form>

</body>
</html>
