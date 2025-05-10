
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
    <script>
        function validateDeleteForm() {
            const serviceId = document.getElementById("serviceId").value;
            const errorDiv = document.getElementById("deleteError");

            if (!serviceId || isNaN(serviceId) || parseInt(serviceId) <= 0) {
                errorDiv.textContent = "Ошибка: Введите корректный ID услуги (положительное число)";
                errorDiv.style.color = "red";
                return false;
            }

            errorDiv.textContent = "";
            return confirm('Вы уверены, что хотите удалить услугу с ID ' + serviceId + '?');
        }
    </script>
</head>
<body>

<a href="menu_admin.jsp">Назад</a>

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
<div class="delete-form">
    <h3>Удаление услуги</h3>
    <form action="controller" method="POST" onsubmit="return validateDeleteForm()">
        <input type="hidden" name="command" value="deleteService">
        <label for="serviceId">ID услуги:</label>
        <input type="number" id="serviceId" name="serviceId" min="1" required>

        <div id="deleteError" style="color: red; margin: 5px 0;"></div>

        <button type="submit" id="deleteBtn">Удалить</button>
    </form>

    <%-- Вывод результатов операции --%>
    <%
        String deleteStatus = request.getParameter("deleteStatus");
        if (deleteStatus != null) {
            if ("success".equals(deleteStatus)) {
    %>
    <p style="color:green;">Услуга успешно удалена!</p>
    <%
    } else if ("error".equals(deleteStatus)) {
    %>
    <p style="color:red;"><%= request.getParameter("errorMessage") %></p>
    <%
            }
        }
    %>
</div>

</body>
</html>