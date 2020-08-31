<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 31.08.2020
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<h1>Shopping cart</h1>
<table border="1">
    <tr>
        <th>Name</th><th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}" varStatus="i">
        <tr>
            <td><c:out value="${i.index + 1}"/></td>
            <td><c:out value="${product.name}"/></td>
            <td><c:out value="${product.price}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/cart/delete?id=${product.id}&row=${i.index}">Delete</a>
            </td>
        </tr>
    </c:forEach>

</table>

<a href="${pageContext.request.contextPath}/products/catalog">Go to the catalog</a>
<a href="${pageContext.request.contextPath}/orders/add">Complete order</a>
</body>
</html>
