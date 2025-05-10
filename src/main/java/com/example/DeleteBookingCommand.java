package com.example;

import DB.DeleteBookingLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBookingCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");

        try {
            int bookingId = Integer.parseInt(bookingIdStr);
            boolean result = DeleteBookingLogic.deleteBooking(bookingId);

            if (result) {
                request.setAttribute("successMessage", "Заявка успешно удалена");
            } else {
                request.setAttribute("errorMessage", "Ошибка при удалении заявки");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный формат ID заявки");
        }

        return "/book_service_admin.jsp";
    }
}