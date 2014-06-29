<!DOCTYPE HTML>
<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
    <head>
        <title>Home Page</title>         
    </head>
    <body>
    <form:form method="POST" modelAttribute="faqdate" action="faq">
    <!-- need to populate this page with Q&A  -->
    
    <form:input path="dob"/>
    <form:checkbox path="prefs"/>
    <input type="submit" value="Save" >
    </form:form>
    </body>
</html>
