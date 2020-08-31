<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello world! Time is ${time}</h1>

<ul>
    <li><a href="${pageContext.request.contextPath}/injectdata">Generate mock data</a></li>
    <li><a href="${pageContext.request.contextPath}/users/all">Display all users</a></li>
    <li><a href="${pageContext.request.contextPath}/registration">Register a new user</a></li>
    <li><a href="${pageContext.request.contextPath}/products/catalog">Product catalog</a></li>
    <li><a href="${pageContext.request.contextPath}/products/admin/catalog">Admin product catalog</a></li>
    <li></li>
    <li><a href="${pageContext.request.contextPath}/displaydb">Display all DB tables</a></li>
    <li></li>
    <li><a href="${pageContext.request.contextPath}/">Go home page</a></li>
</ul>

</body>
</html>
