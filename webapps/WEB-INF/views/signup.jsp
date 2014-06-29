<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
<head>
<style>
.error {
	color: #ff0000;
	font-size: 8pt;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
td {font-size:8pt;}
input {font-size: 8pt;}
select {font-size: 8pt;}
.ui-dialog {
font-size: 8pt; 
}
.ui-dialog-titlebar {
  background-color: #F9A7AE;
  background-image: none;
  color: #000;
}
</style>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-signup.js"/>'></script>
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
</head>
<title>Player Registration</title>
<body>
<form:form  modelAttribute="registerplayer" action="register" method="post">
	<!-- form:errors path="*" cssClass="errorblock" element="div" /-->
<h4>Player Registration Form</h4>
<br>
<Table>
<tr>
<td>*First Name:</td>
<td><form:input path="fname" />
</td>
<td><form:errors path="fname" cssClass="error" /></td>
</tr>
<tr>
<td>Middle Name:</td>
<td><form:input path="mname"  /></td>
<td><form:errors path="mname" cssClass="error" /></td>
</tr>
<tr>
<td>*Last Name:</td>
<td><form:input path="lname" /></td>
<td><form:errors path="lname" cssClass="error" /></td>
</tr>
<tr>
<td>Address 1:</td>
<td><form:input path="addressOne" /></td>
<td><form:errors path="addressOne" cssClass="error" /></td>
</tr>
<tr>
<td>Address 2:</td>
<td><form:input path="addressTwo" /></td>
<td><form:errors path="addressTwo" cssClass="error" /></td>
</tr>
<tr>
<td>City:</td>
<td><form:input path="city" /></td>
</tr>
<tr>
<td>*State:</td>
<td>
	<form:select path="stateId">
              <form:option value="0" label="--Please Select"/>
              <form:options items="${state}" itemValue="stateId" itemLabel="stateName"/>
          </form:select>
</td>
</tr >
<tr>
<td>*Zipcode:</td>
<td><form:input path="zipcode" />
<form:input path="latitude" type="hidden"/>		        
<form:input path="longitude" type="hidden"/>
</td>
<td><form:errors path="zipcode" cssClass="error" /></td>
</tr>
<tr>
<td>Phone:</td>
<td><form:input path="phoneId" /></td>
<td>
<form:select path="phoneType" >
<form:option value="M">Mobile</form:option>
<form:option value="H">Home</form:option>
<form:option value="B">Buisness</form:option>
</form:select>
</td>
<td><form:errors path="phoneId" cssClass="error" /></td>
</tr>
<tr>
<td>Country:</td>
<td><form:input path="country" /></td>
</tr>
<tr>
<td>*Gender:</td>
<td>
<form:select path="gender">
<form:option value="M">Male</form:option>
<form:option value="F">Female</form:option>
</form:select>
</td>
<td><form:errors path="gender" cssClass="error" /></td>
</tr>
<tr>
<td>*Date Of Birth: (mm/dd/yyyy)</td>
<td><form:input path="dob" /></td>
<td><form:errors path="dob" cssClass="error" /></td>
</tr>
<tr>
<td>Occupation:</td>
<td><form:input path="occupation" /></td>
</tr>
<tr>
<td>*EmailAddress:</td>
<td><form:input path="emailaddress" /></td>
<td><form:errors path="emailaddress" cssClass="error" /></td>
</tr>
<tr>
<td>*Password:</td>
<td><form:input path="passwd" type="password"/></td>
<td><form:errors path="passwd" cssClass="error" /></td>
</tr>
<tr>
<td>*Confirm Password:</td>
<td><form:input path="confirmpasswd" type="password"/></td>
<td><form:errors path="confirmpasswd" cssClass="error" /></td>
</tr>
<!-- <tr> -->
<!-- <td>Golfer Image:</td> -->
<%-- <td><form:input path="photo" type="file"/></td> --%>
<!-- </tr> -->
<tr>
<td>
<form:checkbox path="emailnotification" value="Y" checked="true"/>Sign-up for email notifications
<a id="question" href="#" onclick="QuestionDialog();return false;"><img alt="How this helps." src="<c:url value="/resources/images/question.jpg" />" width="32" height="32" border="0"></a>
</td>
</tr>
</Table>
<br>
<input type="submit" value="Register">
<div id="dialog" title="Email Notifications">  <p>Signing up for email notifications will only be used to send you updates regarding invitaions from you and your golf companions.</p></div>
</form:form>
</body>
</html>