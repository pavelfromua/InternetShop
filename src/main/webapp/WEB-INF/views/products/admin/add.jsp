<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 30.08.2020
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
    <jsp:include page="../../style.jsp"/>
</head>
<body>
<jsp:include page="../../header.jsp"/>
<h2 style="color: cadetblue">Add product</h2>
<div class="container" style="width: 30%">
<form class="px-4 py-3" method="post" action="${pageContext.request.contextPath}/products/admin/add">
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${name}" required>
    </div>
    <div class="form-group">
        <label for="price">Price:</label>
        <input type="text" id="price" name="price" maxlength="10">
    </div>
    <div class="form-group" style="color: red">
        ${message}
    </div>
    <div class="form-group">
        <button type="submit" name="Add" class="btn btn-sm btn-primary">Add</button>
    </div>
</form>
</div>
</body>
</html>
