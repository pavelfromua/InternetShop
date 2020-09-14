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
    <jsp:include page="../style.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>
<h2 style="color: cadetblue">Order ${orderId}</h2>

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Product</th>
        <th scope="col">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}" varStatus="i">
        <tr>
            <th scope="row">
                <c:out value="${i.index + 1}"/>
            </th>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
