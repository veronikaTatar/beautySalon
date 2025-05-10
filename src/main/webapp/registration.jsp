<%--
  Created by IntelliJ IDEA.
  User: Afanasiy
  Date: 01.05.2025
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация клиента</title>
</head>
<body>
<a href="index.jsp">Назад</a>
<h3>Регистрация</h3>
<hr/>
<form name="registerForm" method="POST"
      action="controller">
    <input type="hidden" name="command" value="registration" />

    Name:<br/>
    <input type="text" name="name" value=""> <br/>
    Surname:<br/>
    <input type="text" name="surname" value=""> <br/>
    Email:<br/>
    <input type="text" name="email" value="" required> <br/>

    Login:<br/>
    <input type="text" name="login" value=""><br/>
    Password:<br/>
    <input type="password" name="password" value=""> <br/>

    <input type="submit" value="Зарегистрироваться">
</form><hr/>
</body>
</html>
