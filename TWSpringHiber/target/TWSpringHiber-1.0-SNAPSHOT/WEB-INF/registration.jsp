<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>
	<form action="/TWSpringHiber/register" method="POST">
		<p><b>Login:</b><br> <input pattern=".{4,20}" required title="From 4 to 20 characters"
			type="text" name="login"><br></p> <p><b>Password:</b><br>  <input
			pattern=".{4,20}" required title="From 4 to 20 characters" type="password"
			name="password"><br></p> <p><b>Confirm password:</b><br> <input
			pattern=".{4,20}" required title="From 4 to 20 characters" type="password"
			name="confirmpassword"><br></p><img
			src="${contextPath}/resources/capcha/${picturenumber}.gif"
			width="145" height="54"><br> <p><b>Permission code:</b><br> <input
			pattern=".{5,5}" required title="5 characters" type="text"
			name="permissioncode"><br>
			
			<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>  
			<input type="submit" value="Confirm"></p>
	</form>

</body>
</html>