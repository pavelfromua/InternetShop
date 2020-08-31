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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/registration">
    <table border="0">
        <tr>
            <td>Name:</td><td><input type="text" name="name" value="${name}"></td>
        </tr>
        <tr>
            <td>Login:</td><td><input type="text" name="login" value="${login}"></td>
        </tr>
        <tr>
            <td>Password:</td><td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>Confirm assword:</td><td><input type="password" name="cpassword"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><h4 style="color: red">${message}</h4></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><button type="submit" name="reristration">Register</button> </td>
        </tr>
    </table>
</form>

<a href="${pageContext.request.contextPath}/">Go home page</a>
</body>
</html>
