<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DB.ViewBookingAdminLogic" %>
<%@ page import="SalonClass.Booking" %>

<html>
<head>
    <title>Мои заявки</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .alert {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .alert-success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .alert-danger {
            background-color: #f2dede;
            color: #a94442;
        }
    </style>
</head>
<body>
<h2>Мои заявки на услуги</h2>

<%
    // Получаем idUser из сессии
    Integer userId = (Integer) session.getAttribute("userId");
    ArrayList<Booking> bookings = ViewBookingAdminLogic.getBookingsByUser(userId);
%>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название услуги</th>
        <th>Тип услуги</th>
        <th>Мастер</th>
        <th>Дата и время</th>
        <th>Статус</th>
    </tr>
    </thead>
    <tbody>
    <% if (bookings != null && !bookings.isEmpty()) {
        for (Booking booking : bookings) { %>
    <tr>
        <td><%= booking.getOrderId() %></td>
        <td><%= booking.getServiceName() %></td>
        <td><%= booking.getServiceType() %></td>
        <td><%= booking.getMasterName() %></td>
        <td><%= booking.getTime() %></td>
        <td><%= booking.getConfirmationStatus() %></td>
    </tr>
    <% }
    } else { %>
    <tr>
        <td colspan="6">У вас нет активных заявок</td>
    </tr>
    <% } %>
    </tbody>
</table>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>

<a href="menu_client.jsp">Назад</a>
</body>
</html>
