<%@page import="dbModels.Account"%>
<%@page import="java.math.BigDecimal"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account</title>
</head>
<body>
	<%
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0");
	%>
	<c:set var="accountData" value='${info}' />
	<c:set var="balance" value='${accountData[0].balance}' />
	Current balance:
	<c:choose>
	<c:when test="${balance!=null}">
	<c:out value="${balance}"></c:out>    
	</c:when>
	<c:otherwise>
	<c:out value="${'0.00'}"></c:out>
	</c:otherwise>
	</c:choose>
	$ <br><br>	
	<table border="1" cellpadding="5">
		<caption>Operations</caption>
		<tr>
			<th>Date</th>
			<th>Amount</th>
			<th>Balance</th>
		</tr>
		<c:if test="${accountData!=null && fn:length(accountData)gt 0}">
	
	<%-- 	<%if(accountData!=null&&accountData.size()>0) {%> --%>
		<c:forEach var= 'row' items='${accountData}'>
		<tr>
		<td>  ${row.date} </td>
		<td>${row.amount}</td>
		<td>${row.balance}</td>
		</tr>
		</c:forEach>
		</c:if>	
	</table>
	<a href="/TWSpringHiber/index?action=putmoney">Put money</a>
	<a href="/TWSpringHiber/index?action=getmoney">Get money</a>
	<br>
	<br>
	<a href="/TWSpringHiber/index?action=logout">Logout</a>
	<br>
	<a href="/TWSpringHiber/index">Back to login</a>
	<br>
</body>
</html>