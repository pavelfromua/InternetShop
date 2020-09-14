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
    <table class="table table-striped">
        <tr>
            <c:if test="${entry.key.equals('Products')}">
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">price</th>
            </c:if>
            <c:if test="${entry.key.equals('Users')}">
                <th scope="col">id</th>
                <th scope="col">name</th>
                <th scope="col">login</th>
                <th scope="col">password</th>
            </c:if>
            <c:if test="${entry.key.equals('ShoppingCarts')}">
                <th scope="col">id</th>
                <th scope="col">userId</th>
                <th scope="col">products</th>
            </c:if>
            <c:if test="${entry.key.equals('Orders')}">
                <th scope="col">id</th>
                <th scope="col">userId</th>
                <th scope="col">products</th>
            </c:if>
        </tr>
        <c:forEach items="${entry.value}" var="item" varStatus="loop">
            <tr>
                <c:if test="${entry.key.equals('Products')}">
                    <th scope="row">${item.id}</th>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                </c:if>
                <c:if test="${entry.key.equals('Users')}">
                    <th scope="row">${item.id}</th>
                    <td>${item.name}</td>
                    <td>${item.login}</td>
                    <td>${item.password}</td>
                </c:if>
                <c:if test="${entry.key.equals('ShoppingCarts')}">
                    <th scope="row">${item.id}</th>
                    <td>${item.userId}</td>
                    <td>${item.products}</td>
                </c:if>
                <c:if test="${entry.key.equals('Orders')}">
                    <th scope="row">${item.id}</th>
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
