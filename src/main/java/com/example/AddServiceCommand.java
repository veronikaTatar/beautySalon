
package com.example;

import DB.AddServiceLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AddServiceCommand implements Command {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_MASTER = "fioMaster"; // Соответствует форме
    private static final String PARAM_TIME = "time";
    private static final String PARAM_PRICE = "price";


    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter(PARAM_NAME);
        String type = request.getParameter(PARAM_TYPE);
        String master = request.getParameter(PARAM_MASTER);
        String time = request.getParameter(PARAM_TIME);
        String priceStr = request.getParameter(PARAM_PRICE);


        System.out.println(name+" "+type+" "+master+" "+time+" "+priceStr );
        if (name == null || name.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                master == null || master.trim().isEmpty() ||
                time == null || time.trim().isEmpty() ||
                priceStr == null || priceStr.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Все поля должны быть заполнены");
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }

        try {

            double price = Double.parseDouble(priceStr);
            boolean result = AddServiceLogic.addService(name, type, master, time, price);

            if(result) {
                request.setAttribute("successMessage", "Услуга успешно добавлена!");
                return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_PAGE_PATH);
            } else {
                request.setAttribute("errorMessage", "Ошибка при добавлении услуги");
                return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }

    }
}