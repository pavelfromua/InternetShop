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
    <title>All products</title>
    <jsp:include page="../../style.jsp"/>
</head>
<body>
<jsp:include page="../../header.jsp"/>
<h2 style="color: cadetblue">All products page</h2>

<table border="1">
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <c:forEach var="product" items="${catalog}" varStatus="i">
        <tr>
            <td>
                <c:out value="${i.index + 1}"/>
            </td>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/products/delete?id=${product.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/products/admin/add">Add new product</a>
</body>
</html>
