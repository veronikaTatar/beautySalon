package com.example;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/first")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }


    //HttpServletRequest req: Представляет запрос от клиента (например, браузера). Вы можете использовать его для получения параметров, заголовков и т.д.
    //HttpServletResponse resp: Представляет ответ, который сервлет отправит обратно клиенту.
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");//тип контента ответа: сервлет будет отправлять HTML-контент обратно клиенту.

        // Hello
        PrintWriter out = response.getWriter();// для записи текстового содержимого в ответ.
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//перенаправить на главную
//response.sendRedirect("index.html");

        //отобразим что прислали
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<h1>" + request.getParameter("login") + "<h1>");
        writer.println("<h1>" + request.getParameter("pass") + "<h1>");

        writer.println("<form action=\"second\" method=\"POST\">");
        writer.println("<p>KYPC:</p> <input type=\"text\" name=\"name\"> <br/>");
        writer.println("<p>УСПЕВАЕМОСТЬ:</p> <input type=\"text\" name=\"name\"> <br/>");
        writer.println(" <input type=\"submit\" value=\"ОТПРАВИТЬ\"/>");
        writer.println(" </form></body></html>");
    }

    public void destroy() {
    }
}