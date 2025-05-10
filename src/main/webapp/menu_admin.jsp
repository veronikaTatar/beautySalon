<%--
  Created by IntelliJ IDEA.
  User: Afanasiy
  Date: 01.05.2025
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Меню администратора</title>
</head>
<body>
<!-- Блок для сообщений -->
<div class="messages">
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
                ${successMessage}
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
                ${errorMessage}
        </div>
    </c:if>
</div>
<h3>Меню администратора</h3>
<a href="add_service.jsp">Добавить услугу</a>
<br>
<a href="delete_service.jsp">Удалить услугу</a>
<br>
<a href="book_service_admin.jsp">Изучить заявки</a>
<br>
<a href="index.jsp">Назад</a>


</body>
</html>
