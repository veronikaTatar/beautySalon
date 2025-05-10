<%--
  Created by IntelliJ IDEA.
  User: Afanasiy
  Date: 01.05.2025
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<body>
<h3>Авторизация</h3>
<hr/>
<form name="loginForm" method="POST"
      action="controller">
  <input type="hidden" name="command" value="login" />
  Login:<br/>
  <input type="text" name="login" value=""><br/>
  Password:<br/>
  <input type="password" name="password" value=""> <br/>
  <input type="submit" value="Вход">
</form><hr/>


</body>
</html>
