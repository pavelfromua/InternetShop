<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 30.08.2020
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All DB</title>
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>

<h2 style="color: cadetblue">DB tables</h2>

<c:forEach items="${tables}" var="entry">
    <h2 style="color: gray">${entry.key}</h2>
    <table border="1">
        <tr>
            <c:if test="${entry.key.equals('Products')}">
                <th>id</th>
                <th>name</th>
                <th>price</th>
            </c:if>
            <c:if test="${entry.key.equals('Users')}">
                <th>id</th>
                <th>name</th>
                <th>login</th>
                <th>password</th>
            </c:if>
            <c:if test="${entry.key.equals('ShoppingCarts')}">
                <th>id</th>
                <th>userId</th>
                <th>products</th>
            </c:if>
            <c:if test="${entry.key.equals('Orders')}">
                <th>id</th>
                <th>userId</th>
                <th>products</th>
            </c:if>
        </tr>
        <c:forEach items="${entry.value}" var="item" varStatus="loop">
            <tr>
                <c:if test="${entry.key.equals('Products')}">
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                </c:if>
                <c:if test="${entry.key.equals('Users')}">
                    <td>${item.id}</td>
                    <td>${item.name}</td>
                    <td>${item.login}</td>
                    <td>${item.password}</td>
                </c:if>
                <c:if test="${entry.key.equals('ShoppingCarts')}">
                    <td>${item.id}</td>
                    <td>${item.userId}</td>
                    <td>${item.products}</td>
                </c:if>
                <c:if test="${entry.key.equals('Orders')}">
                    <td>${item.id}</td>
                    <td>${item.userId}</td>
                    <td>${item.products}</td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <br>
</c:forEach>
</body>
</html>
