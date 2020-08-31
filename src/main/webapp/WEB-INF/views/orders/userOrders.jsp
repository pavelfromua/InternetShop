<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 31.08.2020
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User ${name} orders</title>
</head>
<body>
<h1>All ${name}'s orders</h1>

<table border="1">
    <tr>
        <th></th>
        <th>ID</th>
        <th></th>
    </tr>
    <c:forEach var="order" items="${orders}" varStatus="i">
        <tr>
            <td>
                <c:out value="${i.index + 1}"/>
            </td>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/details?id=${order.id}">Details</a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/">Go home page</a>
</body>
</html>
