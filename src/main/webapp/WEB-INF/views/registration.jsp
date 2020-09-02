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
<form method="post" action="${pageContext.request.contextPath}/registration">
    <table border="0">
        <tr>
            <td>Name:</td><td><input type="text" name="name" value="${name}"></td>
        </tr>
        <tr>
            <td>Login:</td><td><input type="text" name="login" value="${login}"></td>
        </tr>
        <tr>
            <td colspan="2" style="color: red; font-size: small">${messageLogin}</td>
        </tr>
        <tr>
            <td>Password:</td><td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>Confirm password:</td><td><input type="password" name="cpassword"></td>
        </tr>
        <tr>
            <td colspan="2" style="color: red; font-size: small">${messagePassword}</td>
        </tr>
        <tr>
            <td colspan="2" align="right"><button type="submit" name="reristration" class="btn btn-sm btn-primary">Register</button> </td>
        </tr>
    </table>
</form>

</body>
</html>
