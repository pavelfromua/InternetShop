<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Internet-shop (pet project)</title>
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>
<table>
    <tr>
<c:forEach var="role" items="${roles}" varStatus="i">
    <c:if test="${role.getRoleName().toString().equals('USER')}">
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/products/catalog" style="text-decoration: none">Product catalog</a>
                </td></tr>
            </table>
        </td>
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/orders/userorders" style="text-decoration: none">User orders</a>
                </td></tr>
            </table>
        </td>
    </c:if>
    <c:if test="${role.getRoleName().toString().equals('ADMIN')}">
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/users/all" style="text-decoration: none">Display all users</a>
                </td></tr>
            </table>
        </td>
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/products/admin/catalog" style="text-decoration: none">Admin product catalog</a>
                </td></tr>
            </table>
        </td>
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/orders/admin/orders" style="text-decoration: none">Admin orders list</a>
                </td></tr>
            </table>
        </td>
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/displaydb" style="text-decoration: none">Display all DB tables</a>
                </td></tr>
            </table>
        </td>
    </c:if>
</c:forEach>
        <td>
            <table class="table table-hover">
                <tr><td>
                    <a href="${pageContext.request.contextPath}/logout" style="text-decoration: none">Logout</a>
                </td></tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
