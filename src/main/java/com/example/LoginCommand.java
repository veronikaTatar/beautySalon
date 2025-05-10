/*
package com.example;

import DB.LoginLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        String role = LoginLogic.checkLogin(login, pass); // Получаем роль из LoginLogic

        if (role != null) {
            request.setAttribute("user", login);

            if (role.equals("admin")) {
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_PAGE_PATH);
            } else {
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CLIENT_PAGE_PATH);
            }
        } else {
            request.setAttribute("errorMessage",
                    MessageManager.getInstance().getProperty(MessageManager.LOGIN_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}*/

package com.example;

import DB.LoginLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    //если с сессией
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        // Получаем не только роль, но и ID пользователя
        Object[] authResult = LoginLogic.checkLogin(login, pass);

        if (authResult != null) {
            String role = (String) authResult[0];
            int userId = (int) authResult[1];

            // Сохраняем в сессию
            HttpSession session = request.getSession();
            session.setAttribute("user", login);
            session.setAttribute("userId", userId);
            session.setAttribute("role", role);

            if (role.equals("admin")) {
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMIN_PAGE_PATH);
            } else {
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CLIENT_PAGE_PATH);
            }
        } else {
            request.setAttribute("errorMessage",
                    MessageManager.getInstance().getProperty(MessageManager.LOGIN_ERROR_MESSAGE));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}
