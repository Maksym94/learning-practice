

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Input with JSP</title>
</head>
<body>

	<%
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0L);
	%>
<c:set var="isLogIn" value="${isLogIn}"/>
<c:set var="login" value="${currentLogin}"/>
<c:choose>
<c:when test="${isLogIn && login!=null }">
<div align="center">

		<p>
			Login as, ${login} </p>
		<a href="/ThirdWeb/index.jsp?action=logout">Logout</a><br> <a
			href="/ThirdWeb/index.jsp?action=approved">Account information</a><br>
		<br> <img src="/ThirdWeb/Images/Loginbank.jpg" width="458"
			height="283">

	</div>
</c:when>
<c:otherwise>
<c:redirect url="/ThirdWeb/login.jsp" />
</c:otherwise>
</c:choose>


</body>
</html>