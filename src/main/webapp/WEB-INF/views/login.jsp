<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="./style.jsp"/>
</head>
<body>
<jsp:include page="./header.jsp"/>
<h2 style="color: cadetblue">Login page</h2>
<div class="container" style="width: 30%">
<form class="px-4 py-3" method="post" action="${pageContext.request.contextPath}/login">
    <div class="form-group">
        <label for="login">Login:</label>
        <input class="form-control" type="text" id = "login" name="login" value="${login}">
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input class="form-control" type="password" id="password" name="password">
    </div>
    <div class="form-group" style="color: red">
        ${message}
    </div>
    <button type="submit" name="autentification" class="btn btn-sm btn-primary">Login</button>
    <div class="form-group" align="center">
        <a href="${pageContext.request.contextPath}/registration">Register</a>
    </div>
</form>
</div>
</body>
</html>
