

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Input with JSP</title>
<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>

	<%
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0L);
	%>
<c:set var="login" value="${currentUser.login}"/>
<div align="center">

		<p>
			Login as, ${login} </p>
    <c:url value="/logout" var="logoutUrl" />

	<form action="${logoutUrl}" method="post" id="logoutForm">
	  <input type="hidden"
		name="${_csrf.parameterName}"
		value="${_csrf.token}" />
		 <input type="submit"
               value="Log out" />
	</form>
		<br> <a
			href="${contextPath}/index?action=approved">Account information</a><br>
		<br> <img src="resources/Images/Loginbank.jpg" width="458"
			height="283">

	</div>

</body>
</html>