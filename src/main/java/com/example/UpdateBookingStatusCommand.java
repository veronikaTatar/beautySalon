package com.example;

import DB.UpdateBookingStatusLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateBookingStatusCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        System.out.println("Received bookingId: " + bookingIdStr); // Логирование для отладки

        if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "ID заявки не указан");
            return "/book_service_admin.jsp";
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr.trim());
            if (bookingId <= 0) {
                throw new NumberFormatException();
            }

            boolean result = UpdateBookingStatusLogic.updateStatus(bookingId);

            if (result) {
                request.setAttribute("successMessage", "Статус заявки изменен");
            } else {
                request.setAttribute("errorMessage", "Ошибка при изменении статуса");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный формат ID заявки. Используйте положительное число.");
        }

        return "/book_service_admin.jsp";
    }
}