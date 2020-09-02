<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
    <jsp:include page="../style.jsp"/>
</head>
<body>
<jsp:include page="../header.jsp"/>
<h2 style="color: cadetblue">All users page</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th></th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/users/delete?id=${user.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
