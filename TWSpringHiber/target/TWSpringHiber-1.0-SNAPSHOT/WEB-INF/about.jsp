<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
<link href="${contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<title>About user operations</title>
</head>
<body>
<h2>${userdetails.login}</h2> <br>
<c:set var="operatins" value='${userdetails.accountOperations}' />
	<c:set var="balance" value='${operatins[0].balance}' />
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
	<table class="tg" border="1" cellpadding="5">
		<caption>Operations</caption>
		<tr>
		    <th>Id</th>
			<th>Date</th>
			<th>Amount</th>
			<th>Balance</th>
			<th>Edit balance</th>
			<th>Delete operation</th>
		</tr>
		<c:if test="${operatins!=null && fn:length(operatins)gt 0}">
	
		<c:forEach var= 'row' items='${operatins}'>
		<tr>
		<td>${row.idAccount}</td>
		<td>${row.date}</td>
		<td>${row.amount}</td>
		<td>${row.balance}</td>
		<td><a href="${contextPath}/admin/about/edit/${row.idAccount}?idUser=${userdetails.id}">
		Edit</a></td>
		<td><a href="${contextPath}/admin/about/delete/${row.idAccount}?idUser=${userdetails.id}">
		Delete</a></td>
		</tr>
		</c:forEach>
		</c:if>	
	</table>
	<br>
	
	<form:form action="${contextPath}/admin/about/edit/?idUser=${userdetails.id}" 
	modelAttribute="account">
	<c:if test="${account.idAccount!=0}">
	<h4>Edit balance</h4>
	<table>
	<tr>
	<td><form:label path="idAccount">Id operation</form:label></td>
	<td><form:input path="idAccount" disabled="true" readonly="true"/> 
	<form:hidden path="idAccount"/>
	<form:hidden path="loginAccount"/> 
	<form:hidden path="date"/> 
	<form:hidden path="amount"/>  
	</td>
	</tr>
	<tr>
	<td><form:label path="balance">Balance</form:label></td>
	<td><form:input path="balance" /> </td>
	</tr>
	<tr>
<td colspan="2">
<input type="submit" value="Edit"/>    
</td>
</tr>
	</table>
	</c:if>
	</form:form>
</body>
</html>