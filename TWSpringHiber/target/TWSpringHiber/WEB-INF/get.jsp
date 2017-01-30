<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get money</title>
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
		<input type="submit" value="Confirm">
	</form>
	<a href="/TWSpringHiber/index?action=approved">Back to account</a>
	<br>
	
</body>
</html>