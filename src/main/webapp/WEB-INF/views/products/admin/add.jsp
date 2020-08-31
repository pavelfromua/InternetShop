<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 30.08.2020
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<h1>Add product</h1>
<form method="post" action="${pageContext.request.contextPath}/products/admin/add">
    <table>
        <tr>
            <td>Name:</td><td><input type="text" name="name" value="${name}" required></td>
        </tr>
        <tr>
            <td>Price:</td><td><input type="text" name="price" maxlength="10"></td>
        </tr>
        <tr>
            <td colspan="2" align="left" style="color: red">${message}</td>
        </tr>
        <tr>
            <td colspan="2" align="right"><button type="submit" name="Add">Add</button></td>
        </tr>
    </table>
</form>

<a href="${pageContext.request.contextPath}/">Go home page</a>
</body>
</html>
