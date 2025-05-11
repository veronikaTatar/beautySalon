<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Салон красоты Image</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #4b0082;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #6a0dad;
        }
        a {
            display: inline-block;
            margin: 10px;
            padding: 10px 20px;
            background-color: #8a2be2;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #9370db;
        }
        .container {
            max-width: 800px;
            margin: auto;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1><%= "Добро пожаловать в косметический салон Image!" %></h1>
    <br/>
    <a href="login.jsp">Авторизация</a>
    <br>
    <a href="registration.jsp">Регистрация</a>
</div>
</body>
</html>