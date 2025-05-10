package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import DB.DeleteServiceLogic;

public class DeleteServiceCommand implements Command {
    private static final String PARAM_SERVICE_ID = "serviceId";

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String serviceIdStr = request.getParameter(PARAM_SERVICE_ID);

        if (serviceIdStr == null || serviceIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "ID услуги не указан");
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }

        try {
            int serviceId = Integer.parseInt(serviceIdStr);

            if (serviceId <= 0) {
                request.setAttribute("errorMessage", "Неверный ID услуги");
                return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            }

            // Используем безопасное удаление с проверкой связей
            boolean result = DeleteServiceLogic.deleteServiceSafely(serviceId);

            if (result) {
                request.setAttribute("successMessage", "Услуга успешно удалена!");
            } else {
                request.setAttribute("errorMessage",
                        "Не удалось удалить услугу. Нет такого id.");
            }

            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_PAGE_PATH);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный формат ID услуги");
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
    }
}