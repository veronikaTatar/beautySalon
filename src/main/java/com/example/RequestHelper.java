package com.example;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

public class RequestHelper {
    private static RequestHelper instance = null;

    HashMap<String, Command> commands =
            new HashMap<String, Command>();

    private RequestHelper() {
//заполнение таблицы командами
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());

        commands.put("viewService", new ViewServiceCommand());

        commands.put("addService", new AddServiceCommand());
        commands.put("deleteService", new DeleteServiceCommand());
        commands.put("bookService", new BookServiceCommand());
        commands.put("deleteBooking", new DeleteBookingCommand());
        commands.put("updateBookingStatus", new UpdateBookingStatusCommand());
    }
    public Command getCommand(HttpServletRequest request) {
//извлечение команды из запроса
        String action = request.getParameter("command");
//получение объекта, соответствующего команде
        Command command = commands.get(action);
        if (command == null) {
//если команды не существует в текущем объекте
            command = new NoCommand();
        }
        return command;
    }
    //создание единственного объекта по шаблону Singleton
    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
