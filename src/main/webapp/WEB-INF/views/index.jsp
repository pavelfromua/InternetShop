<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet-shop (pet project)</title>
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>
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
