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
fieldset { border-bottom: 0;
border-right: 0;
border-left: 0;
 }
</style>
<script type='text/javascript' src='<c:url value="/resources/js/golfcompanion-profile.js"/>'></script>
<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
</head>
<title>Player Profile</title>
<body>
<form:form  id="ppform" modelAttribute="playerprofile"  method="POST" action="/golfcompanion/profile/fetch">
	<!-- form:errors path="*" cssClass="errorblock" element="div" /-->
	<div>
	<fieldset>
<Table>
<tr>
<td>*First Name:</td>
<td><form:input path="first_name" disabled="true"/>
</td>
</tr>
<tr>
<td>Middle Name:</td>
<td><form:input path="middle_name" disabled="true" /></td>
</tr>
<tr>
<td>*Last Name:</td>
<td><form:input path="last_name" disabled="true"/></td>
</tr>
</Table>
</fieldset>
</div>
<div>
<fieldset id="editsection" >
<Table>
<tr>
<td>Address 1:</td>
<td><form:input path="street_address1" id="street_address1"/></td>
<td><form:errors path="street_address1" cssClass="error" /></td>
</tr>
<tr>
<td>Address 2:</td>
<td><form:input path="street_address2" id="street_address2"/></td>
<td><form:errors path="street_address2" cssClass="error" /></td>
</tr>
<tr>
<td>City:</td>
<td><form:input path="city" id="city"/></td>
</tr>
<tr>
<td>*State:</td>
<td>
	<form:select path="state_id" id="state_id">
              <form:option value="0" label="--Please Select"/>
              <form:options items="${state}" itemValue="stateId" itemLabel="stateName"/>
          </form:select>
</td>
</tr >
 <tr>
<td>*Zipcode:</td>
<td><form:input path="zip_code" id="zip_code"/>
<form:input path="latitude" type="hidden"/>		        
<form:input path="longitude" type="hidden"/>
</td>
<td><form:errors path="zip_code" cssClass="error" /></td>
</tr>
<tr>
<td>Phone:</td>
<td><form:input path="phone_id" id="phone_id"/></td>
 <%-- <td>
<form:select path="phoneType" value="${editprofile.phoneType}">
<form:option value="M">Mobile</form:option>
<form:option value="H">Home</form:option>
<form:option value="B">Buisness</form:option>
</form:select>
</td> --%>
 <td><form:errors path="phone_id" cssClass="error" /></td>
</tr>
<%-- <tr>
<td>Country:</td>
<td><form:input path="country_id" /></td>
</tr>
 --%>
 <%-- <tr>
<td>*Gender:</td>
<td>
<form:select path="gender">
<form:option value="M">Male</form:option>
<form:option value="F">Female</form:option>
</form:select>
</td>
<td><form:errors path="gender" cssClass="error" /></td>
</tr>
 --%>
 <tr>
<td>*Date Of Birth: (mm/dd/yyyy)</td>
<td><form:input path="date_of_birth" id="date_of_birth"/></td>
<td><form:errors path="date_of_birth" cssClass="error" /></td>
</tr>
<tr>
<td>*EmailAddress:</td>
<td><form:input path="email_address" id="email_address"/></td>
<td><form:errors path="email_address" cssClass="error" /></td>
</tr>
<!-- <tr> -->
<!-- <td>Golfer Image:</td> -->
<%-- <td><form:input path="photo" type="file"/></td> --%>
<!-- </tr> -->
<%-- <tr>
<td>
<form:checkbox path="email_notifications" id="email_notifications" value='${playerprofile.email_notifications }' checked="true"/>Sign-up for email notifications
<a id="question" href="#" onclick="QuestionDialog();return false;"><img alt="How this helps." src="<c:url value="/resources/images/question.jpg" />" width="32" height="32" border="0"></a>
</td>
</tr> --%>
 </Table>
 </fieldset>
 </div>
<br>
<input type="button" value="Edit" id="editprofile">
<input type="submit" value="Save" id="saveprofile">
</form:form>
</body>
</html>