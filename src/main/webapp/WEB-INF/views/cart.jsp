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
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>
<table>
    <tr>
        <td>
            <h2 style="color: cadetblue">Shopping cart</h2>
        </td>
    </tr>
    <tr>
        <td align="right">
            <c:if test="${products.size() > 0}">
                <a href="${pageContext.request.contextPath}/orders/add">Complete order</a>
            </c:if>
        </td>
    </tr>
    <tr>
        <td>
            <table border="1">
                <tr>
                    <th>№</th><th>Name</th><th>Price</th><th>Action</th>
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
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/products/catalog">Go to the catalog</a>
        </td>
    </tr>
</table>
</body>
</html>
