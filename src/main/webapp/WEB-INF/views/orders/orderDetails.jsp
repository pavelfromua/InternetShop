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
    <title>Order ${orderId}</title>
</head>
<body>
<h1>Order ${orderId}</h1>

<table border="1">
    <tr>
        <th></th>
        <th>Product</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}" varStatus="i">
        <tr>
            <td>
                <c:out value="${i.index}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/">Go home page</a>

</body>
</html>
