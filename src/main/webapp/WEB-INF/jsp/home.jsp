<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

<h1>Title: ${title}</h1>
<h2>Message: ${message}</h2>
<h3><a href="/admin/users">Admin</a></h3>
<h3><a href="/user/user">User</a></h3>
<a href="<c:url value="/perform_logout" />">Logout</a>

</body>
</html>
