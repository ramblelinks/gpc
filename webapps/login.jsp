<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form:form modelAttribute="loginpage" action="login" method="POST">
<div>
<table>
<tr>
	<td>
	Username: 
	</td>
	<td>
	<form:input path="login_name" />	
	</td>
</tr>
<tr>
	<td>
	Password: 
	</td>
	<td>
	<form:password path="login_password"  />
	</td>
</tr>
</table>
<br>
<input type="submit" value="Login" />
<form:errors path="login_name" cssClass="error" />
</div>
</form:form>
<a href="register">signUp</a>
<!-- a href="test/t">Test</a-->
</body>
</html>