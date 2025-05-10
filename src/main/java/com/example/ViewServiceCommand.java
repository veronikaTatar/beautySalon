package com.example;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ViewServiceCommand implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
        // Здесь нужно реализовать логику команды
        // Например, получить данные, выполнить какие-то действия
        // и вернуть имя страницы, на которую нужно перенаправить запрос
        return "view_service.jsp"; // Пример: перенаправляем на view_service.jsp
    }


}