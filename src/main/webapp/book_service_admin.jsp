<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DB.ViewBookingAdminLogic" %>
<%@ page import="SalonClass.Booking" %>
<html>
<head>
    <title>Заявки на бронирование</title>
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
            position: sticky;
            top: 0;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .action-panel {
            margin: 20px 0;
            padding: 15px;
            background-color: #f5f5f5;
            border-radius: 5px;
        }
        .action-panel input, .action-panel button {
            margin-right: 10px;
            padding: 5px 10px;
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
<h2>Заявки на бронирование</h2>

<!-- Панель действий -->
<div class="action-panel">
    <form action="controller" method="POST" id="deleteForm">
        <input type="hidden" name="command" value="deleteBooking">
        <label for="bookingId">ID заявки:</label>
        <input type="number" id="bookingId" name="bookingId" min="1" required>
        <button type="submit" id="deleteBtn" disabled>Удалить</button>
    </form>

    <form action="controller" method="POST" id="updateForm" style="margin-top: 10px;">
        <input type="hidden" name="command" value="updateBookingStatus">
        <input type="hidden" id="updateBookingId" name="bookingId" value="">
        <button type="submit" id="updateBtn" disabled>Изменить статус</button>
    </form>
</div>

<!-- Таблица заявок -->
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название услуги</th>
        <th>Тип услуги</th>
        <th>Мастер</th>
        <th>Клиент</th>
        <th>Email клиента</th>
        <th>Статус</th>
    </tr>
    </thead>
    <tbody>
    <%
        ArrayList<Booking> bookings = ViewBookingAdminLogic.getBookings();
        if (bookings != null && !bookings.isEmpty()) {
            for (Booking booking : bookings) {
    %>
    <tr onclick="selectBooking(<%= booking.getOrderId() %>)">
        <td><%= booking.getOrderId() %></td>
        <td><%= booking.getServiceName() %></td>
        <td><%= booking.getServiceType() %></td>
        <td><%= booking.getMasterName() %></td>
        <td><%= booking.getClientName() %></td>
        <td><%= booking.getClientEmail() %></td>
        <td><%= booking.getConfirmationStatus() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="7">Нет данных о заявках</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<%-- Сообщения об ошибках/успехе --%>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>

<a href="menu_admin.jsp">Назад</a>

<script>
    function selectBooking(bookingId) {
        // Устанавливаем ID в обе формы
        document.getElementById('bookingId').value = bookingId;
        document.getElementById('updateBookingId').value = bookingId;

        // Активируем кнопки
        document.getElementById('deleteBtn').disabled = false;
        document.getElementById('updateBtn').disabled = false;
    }

    // Валидация при изменении значения
    document.getElementById('bookingId').addEventListener('input', function() {
        var bookingId = this.value;
        var isValid = !isNaN(bookingId) && bookingId.trim() !== "" && parseInt(bookingId) > 0;

        document.getElementById('deleteBtn').disabled = !isValid;
        document.getElementById('updateBookingId').value = isValid ? bookingId : "";
        document.getElementById('updateBtn').disabled = !isValid;
    });
</script>
</body>
</html>