
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DB.ViewServiceLogic" %>
<%@ page import="SalonClass.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="DB.BookingLogic" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head>
    <title>Просмотр услуг</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            display: block;
            margin-bottom: 10px;
        }
        .booking-form {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        #bookBtn {
            padding: 5px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        #bookBtn:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }
    </style>
   <%-- <script>
        function validateForm() {
            var serviceIdInput = document.getElementById("serviceId");
            var bookBtn = document.getElementById("bookBtn");
            // Проверяем, что значение есть и оно больше 0
            bookBtn.disabled = !serviceIdInput.value || parseInt(serviceIdInput.value) <= 0;
        }

        // Вызываем проверку при загрузке страницы
        document.addEventListener("DOMContentLoaded", function() {
            validateForm();
        });
    </script>--%>
    <script>
        function validateBookingForm() {
            const serviceIdInput = document.getElementById("serviceId");
            const bookBtn = document.getElementById("bookBtn");
            const errorDiv = document.getElementById("bookingError");

            // Проверка на пустое значение
            if (!serviceIdInput.value || parseInt(serviceIdInput.value) <= 0) {
                errorDiv.textContent = "Введите корректный ID услуги";
                errorDiv.style.color = "red";
                bookBtn.disabled = true;
                return false;
            }

            // AJAX-проверка доступности услуги
            fetch('checkServiceAvailability?serviceId=' + serviceIdInput.value)
                .then(response => response.json())
                .then(data => {
                    if (data.available) {
                        errorDiv.textContent = "";
                        bookBtn.disabled = false;
                    } else {
                        errorDiv.textContent = "Эта услуга недоступна для бронирования";
                        errorDiv.style.color = "red";
                        bookBtn.disabled = true;
                    }
                })
                .catch(error => {
                    errorDiv.textContent = "Ошибка проверки доступности услуги";
                    errorDiv.style.color = "red";
                    bookBtn.disabled = true;
                });

            return true;
        }

        // Обновленная функция проверки формы
        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById("serviceId").addEventListener("change", validateBookingForm);
        });
    </script>
</head>
<body>

<a href="menu_client.jsp">Назад</a>

<h2>Список услуг</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Тип</th>
        <th>ФИО мастера</th>
        <th>Время</th>
        <th>Цена</th>
    </tr>
    </thead>
    <tbody>
    <%
        ArrayList<Service> services = ViewServiceLogic.getAvailableServices();
        if (services != null) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            for (Service service : services) {
                String timeString = service.getTIME();
                Date date = null;
                try {
                    date = originalFormat.parse(timeString);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                String formattedTime = "";
                if (date != null) {
                    formattedTime = targetFormat.format(date);
                }
    %>
    <tr>
        <td><%= service.getIdService() %></td>
        <td><%= service.getNAME() %></td>
        <td><%= service.getTYPE() %></td>
        <td><%= service.getFIOMASTER() %></td>
        <td><%= formattedTime %></td>
        <td><%= service.getPRICE() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">Ошибка при получении данных из базы данных.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<%--<div class="booking-form">
    <h3>Бронирование услуги</h3>
    <form action="controller" method="POST" onsubmit="return confirm('Вы уверены, что хотите забронировать эту услугу?');" >
        <input type="hidden" name="command" value="bookService">
        <label for="serviceId">ID услуги:</label>
        <input type="number" id="serviceId" name="serviceId" min="1" onchange="validateForm()" required>

        <input type="hidden" name="userId" value="${sessionScope.userId}">

        <button type="submit" id="bookBtn" disabled>Забронировать</button>
    </form>
    <%
        String bookingStatus = request.getParameter("bookingStatus");
        if (bookingStatus != null) {
            if ("success".equals(bookingStatus)) {
    %>
    <p style="color:green;">Услуга успешно забронирована!</p>
    <%
    } else if ("error".equals(bookingStatus)) {
    %>
    <p style="color:red;">Ошибка при бронировании услуги.</p>
    <%
            }
        }
    %>
</div>--%>
<div class="booking-form">
    <h3>Бронирование услуги</h3>
    <form action="controller" method="POST" onsubmit="return confirmBooking();">
        <input type="hidden" name="command" value="bookService">
        <label for="serviceId">ID услуги:</label>
        <input type="number" id="serviceId" name="serviceId" min="1" required
               onchange="validateBookingForm()">

        <div id="bookingError" style="color: red; margin: 5px 0;"></div>

        <input type="hidden" name="userId" value="${sessionScope.userId}">
        <button type="submit" id="bookBtn" disabled>Забронировать</button>
    </form>
</div>

<script>
    function confirmBooking() {
        const serviceId = document.getElementById("serviceId").value;
        return confirm(`Вы уверены, что хотите забронировать услугу с ID ${serviceId}?`);
    }
</script>
</body>
</html>