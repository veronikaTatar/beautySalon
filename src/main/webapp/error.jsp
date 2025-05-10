<%--
  Created by IntelliJ IDEA.
  User: Afanasiy
  Date: 01.05.2025
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head><title>Error</title></head>
<body>
<h3>Error</h3>
<hr />
<jsp:expression>
    (request.getAttribute("errorMessage") != null)
    ? (String) request.getAttribute("errorMessage")
    : "unknown error"</jsp:expression>
<hr />
<a href="controller">Return to login page</a>
</body></html>

