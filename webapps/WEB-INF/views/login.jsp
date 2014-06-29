<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ page import="java.io.*,java.util.*" %>

<html>
<head>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-login.js"/>'></script>
<style>
.error {
	color: #ff0000;
	font-size: 8pt;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign-up and find your playing pro partner</title>
</head>
<body>
<%-- <%
    Integer hitsCount =  
      (Integer)application.getAttribute("hitCounter");
    if( hitsCount ==null || hitsCount == 0 ){
       /* First visit */
       out.println("Welcome to my website!");
       hitsCount = 1;
    }else{
       /* return visit */
       out.println("Welcome back to my website!");
       hitsCount += 1;
    }
    application.setAttribute("hitCounter", hitsCount);
%> --%>
<form:form modelAttribute="loginpage" action="login" method="POST">
<%-- <p>Total number of visits: <%= hitsCount%></p>
<p>DB count : ${logincount }</p> --%>
<div>
<table>
<tr>
	<td>
	Username: 
	</td>
	<td>
	<form:input path="login_name" />	
	<form:errors path="login_name" cssClass="error" />
	</td>
</tr>
<tr>
	<td>
	Password: 
	</td>
	<td>
	<form:password path="login_password"  />
	<form:errors path="login_password" cssClass="error" />
	</td>
</tr>
</table>
<br>
<input type="submit" value="Login" /> <input id="reset" type="button" value="Reset Password" />
<form:errors path="login_errors" cssClass="error" />
</div>
</form:form>
<br>
<a href="register">signUp</a>
<!-- a href="test/t">Test</a-->
</body>
</html>