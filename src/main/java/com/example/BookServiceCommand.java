package com.example;

import DB.BookingLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookServiceCommand implements Command {

    private static final String PARAM_SERVICE_ID = "serviceId";
    private static final String PARAM_USER_ID = "userId";
    private static final String BOOKING_STATUS = "bookingStatus";

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String serviceIdStr = request.getParameter(PARAM_SERVICE_ID);
        String userIdStr = request.getParameter(PARAM_USER_ID);
        System.out.println("Получены параметры - serviceId: '" + serviceIdStr + "', userId: '" + userIdStr + "'");

        if (serviceIdStr == null || userIdStr == null) {
            request.setAttribute("errorMessage", "Не указаны ID услуги или пользователя");
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }

        try {
            int serviceId = Integer.parseInt(serviceIdStr);
            int userId = Integer.parseInt(userIdStr);

            boolean result = BookingLogic.bookService(userId, serviceId);

            if (result) {
                // Перенаправляем с параметром успешного бронирования
                return ConfigurationManager.getInstance().getProperty(ConfigurationManager.BOOK_PAGE_PATH);
            } else {
                request.setAttribute("errorMessage", "Ошибка при бронировании услуги");
                return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный формат ID");
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Системная ошибка: " + e.getMessage());
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
    }
}