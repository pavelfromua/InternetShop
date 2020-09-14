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
    <jsp:include page="../style.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>
<h2 style="color: cadetblue">All products page</h2>
<table>
    <tr>
        <td>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${catalog}" varStatus="i">
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
                        <td>
                            <a href="${pageContext.request.contextPath}/cart/add?id=${product.id}">Add cart</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <c:if test="${message != ''}">
                ${message}
            </c:if>
        </td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/cart">Go shopping cart</a>
        </td>
    </tr>
</table>
</body>
</html>
