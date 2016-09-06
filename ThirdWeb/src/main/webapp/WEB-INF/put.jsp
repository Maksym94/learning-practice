<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Put money</title>
</head>
<body>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0");
%>
	Enter your amount for put:
	<br>
	<form action="/ThirdWeb/putact" method="POST">
		<input type="number" min="1" step="0.01" name="currentput" /><br>
		<input type="submit" value="Confirm">
	</form>
	<a href="/ThirdWeb/index.jsp?action=approved">Back to account</a>
	<br>
	
</body>
</html>