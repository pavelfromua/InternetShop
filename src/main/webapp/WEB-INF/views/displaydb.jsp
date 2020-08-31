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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>
<h1>DB tables</h1>
<c:forEach items="${tables}" var="entry">
    <h1>${entry.key}</h1>
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

<a href="${pageContext.request.contextPath}/">Go home page</a>
</body>
</html>
