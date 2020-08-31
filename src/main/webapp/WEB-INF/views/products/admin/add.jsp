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
