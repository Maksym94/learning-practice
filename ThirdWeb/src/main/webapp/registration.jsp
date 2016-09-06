<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<form action="/ThirdWeb/register" method="POST">
		<p><b>Login:</b><br> <input pattern=".{4,20}" required title="From 4 to 20 characters"
			type="text" name="login"><br></p> <p><b>Password:</b><br>  <input
			pattern=".{4,20}" required title="From 4 to 20 characters" type="password"
			name="password"><br></p> <p><b>Confirm password:</b><br> <input
			pattern=".{4,20}" required title="From 4 to 20 characters" type="password"
			name="confirmpassword"><br></p><img
			src="/ThirdWeb/capcha/${picturenumber}.gif"
			width="145" height="54"><br> <p><b>Permission code:</b><br> <input
			pattern=".{5,5}" required title="5 characters" type="text"
			name="permissioncode"><br>  <input type="submit"
			value="Confirm"></p>
	</form>

</body>
</html>