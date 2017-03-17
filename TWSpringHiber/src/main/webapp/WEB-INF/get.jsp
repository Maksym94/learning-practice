<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get money</title>
<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0");
%>
	Enter your amount for get:
	<br>
	<form action="/TWSpringHiber/getact" method="POST">
		<input type="number" min="0.01" step="0.01" name="currentget" /><br>
		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		<input type="submit" value="Confirm">
	</form>
	<a href="/TWSpringHiber/index?action=approved">Back to account</a>
	<br>
	
</body>
</html>