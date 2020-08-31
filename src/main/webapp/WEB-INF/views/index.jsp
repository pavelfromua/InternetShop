<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet-shop (pet project)</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<a href="${pageContext.request.contextPath}/"><h1>Internet-shop (pet project)</h1></a>
<h3>Tomcat, servlets, db</h3>
<table border="1">
    <tr>
        <th><h2>Customer</h2></th>
        <th><h2>Admin</h2></th>
        <th><h2>Service</h2></th>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/registration">Register a new user</a></td>
        <td><a href="${pageContext.request.contextPath}/users/all">Display all users</a></td>
        <td><a href="${pageContext.request.contextPath}/injectdata">Generate mock data</a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/products/catalog">Product catalog</a></td>
        <td><a href="${pageContext.request.contextPath}/products/admin/catalog">Admin product catalog</a></td>
        <td><a href="${pageContext.request.contextPath}/displaydb">Display all DB tables</a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/orders/userorders">User orders</a></td>
        <td><a href="${pageContext.request.contextPath}/orders/admin/orders">Admin orders list</a></td>
        <td></td>
    </tr>
</table>
</body>
</html>
