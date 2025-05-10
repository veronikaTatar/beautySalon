package com.example;

import DB.RegistrationLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationCommand implements Command {
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = null;

        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        boolean result= RegistrationLogic.addUser(name, surname, email, login, pass);

        if(result)  page ="/menu_client.jsp";/* ConfigurationManager.getInstance().getProperty(ConfigurationManager.CLIENT_PAGE_PATH);*/
        else page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);


        System.out.println("Client page path: " + page);
        return page;
    }
}