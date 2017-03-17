<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Admin</title>
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
</head>
<body>
<h2>Admin page</h2> <br>
<c:set var="UserList" value="${users}" ></c:set>
<table class="tg" border="1" cellpadding="5">
		<caption>Existed users</caption>
		<tr>
		    <th>Id</th>
			<th>Name</th>
			<th>Password</th>
			<th>Roles</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<c:if test="${UserList!=null && fn:length(UserList)gt 0}">
		<c:forEach var="row" items="${UserList}">
		<tr>
		<td>${row.id}</td>
		<td><a href="${contextPath}/admin/about/${row.id}" target="_blank">${row.login}</a></td>
		<td>${row.password}</td>
		<td>${row.roles.iterator().next().roleName}</td>
		<td><a href="${contextPath}/admin/edit/${row.id}">Edit</a></td>
		<td><a href="${contextPath}/admin/delete/${row.id}">Delete</a></td>
		</tr>
		</c:forEach>
		</c:if>
		</table>

<form:form action="${contextPath}/admin/edit/" modelAttribute="user"> <!-- ${user.id} -->
<c:if test="${user.login!=null}">
<h4>Edit user</h4> <br>
<table>
<tr>
<td> <form:label  path="id">Id</form:label>
</td>
<td><form:input path="id" readonly="true" disabled="true" /> <form:hidden path="id"/></td>
</tr>
<tr>
<td> <form:label path="login">Username</form:label>
</td>
<td><form:input path="login"/> </td>
</tr>
<tr>
<td><form:label path="password">Password</form:label></td>
<td><form:input path="password"/></td>

</tr>
<tr>
<td colspan="2">
<input type="submit" value="Edit user"/>    
</td>
</tr>
</table>
</c:if>
</form:form>

</body>
</html>