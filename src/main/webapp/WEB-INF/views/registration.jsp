<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 29.08.2020
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>

<div class="container" style="width: 30%">
<form class="px-4 py-3" method="post" action="${pageContext.request.contextPath}/registration">
    <div class="form-group">
        <label for="name">Name:</label>
        <input class="form-control" type="text" id="name" name="name" value="${name}">
    </div>
    <div class="form-group">
        <label for="login">Login:</label>
        <input class="form-control" type="text" id="login" name="login" value="${login}">
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input class="form-control" type="password" id="password" name="password">
    </div>
    <div class="form-group">
        <label for="cpassword">Confirm password:</label>
        <input class="form-control" type="password" id="cpassword" name="cpassword">
    </div>
    <div class="form-group" style="color: red; font-size: small">
        ${message}
    </div>
    <button type="submit" name="registration" class="btn btn-sm btn-primary">Register</button>
</form>
</div>

</body>
</html>
