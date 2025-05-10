package com.example;

import DB.ViewServiceLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

    @WebServlet("/checkServiceAvailability")
    public class CheckServiceAvailabilityServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            boolean isAvailable = ViewServiceLogic.isServiceAvailable(serviceId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"available\": " + isAvailable + "}");
        }
    }

