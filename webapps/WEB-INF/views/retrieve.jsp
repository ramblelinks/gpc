<!DOCTYPE html>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-retrieve.js"/>'></script>
<style>
.error {
	color: #ff0000;
	font-size: 8pt;
}
</style>
<title>Reset password</title>
</head>
<body>
<form:form modelAttribute="loginpage" id="retrievepage" name="retrievepage">
Need to implement reset password in this page
<div>
<div id="findaccount">
	<fieldset>
	<legend>Find your Account</legend>
		Email, username
		<form:input path="username" type="text" id="username" name="username" placeholder="User Name:" />
		<br>
		<input id="find" type="button" value="Find" > 
		<span id="finderr"></span>		
	</fieldset>
</div>
<div id="resetpassword">
	<fieldset>
	<legend>Reset password</legend>
		Password: <form:password path="passwd" id="passwd" name="passwd" placeholder="Password:" />
		<span id="passwd_err"></span>	
		<br>
		Confirm Password:<form:password path="confirmpasswd" id="confirmpasswd" name="confirmpasswd" placeholder="Confirm Password" />
		<span id="confirmpasswd_err"></span>
		<br>		
		<input id="reset" type="button" value="Submit" > 			
	</fieldset>
</div>
<input id="cancel" type="button" value="Cancel" >
<span id="message"></span>	
</div>
</form:form>
</body>
</html>