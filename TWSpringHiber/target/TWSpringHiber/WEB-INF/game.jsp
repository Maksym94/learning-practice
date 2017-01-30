<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Table games</title>
</head>
<body>
<c:set var="gameList" value="${playingComands}"></c:set>
<table border="1" cellpadding="5">
		<caption>Tournament commands</caption>
		<tr>
			<th>Command 1</th>
			<th>Command 2</th>
			<th>Score command 1</th>
			<th>Score command 2</th>
			<th>Finished</th>
		</tr>
		<c:if test="${gameList!=null &&fn:length(gameList)gt 0 }">
		<c:forEach var="command" items="${ playingComands}">
		<tr>
		<td>${command.command1.commandName }</td>
		<td>${command.command2.commandName }</td>
		<td>${command.score1 }</td>
		<td>${command.score2 }</td>
		<td><c:choose>
		<c:when test="${command.finished}">${'yes'}</c:when>
		<c:otherwise>${'no'}</c:otherwise>
		</c:choose></td>
		</tr>
		</c:forEach>
		</c:if>
		</table>
</body>
</html>