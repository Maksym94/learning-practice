<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
     
     <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0L);
	%>
<c:set var="error" value="${Wrong}"/>
<c:set var="totalUsers" value="${totalUsers}"/>
<div align="center" >
		<form action="${contextPath}/login" method="POST">
			Login: <input type="text" name="login"><br> Password: <input
				type="password" name="password"><br> 
				<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
				<input type="submit" value="Confirm">
		</form>
		<br>
		<c:if test="${error!=null && fn:length(error)gt 0  }">
	${error}
		<br><br>
		</c:if>  
		Not yet registered? <a href="/TWSpringHiber/registration">Registration</a><br>
		<br>
		${totalUsers}
		<c:choose>
		<c:when test="${totalUsers==1}">
		${'user'}
		</c:when>
		<c:otherwise>
		${'users'}
		</c:otherwise>
		</c:choose>
		 ${'have already registered'}
	</div>
</body>
</html>