package DB;

import com.example.ConfigurationManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddServiceLogic {
    // Два форматера для разных форматов даты
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter DB_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean addService(String name, String type, String fioMaster, String time, double price) {
        // Валидация входных параметров
        if (name == null || name.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                fioMaster == null || fioMaster.trim().isEmpty() ||
                time == null || time.trim().isEmpty()) {
            System.err.println("Ошибка: Обязательные параметры не могут быть пустыми");
            return false;
        }

        String sql = "INSERT INTO service (NAME, TYPE, MASTER, TIME, PRICE) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Установка параметров
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, fioMaster);
            preparedStatement.setTimestamp(4, parseTimestamp(time));
            preparedStatement.setDouble(5, price);

            // Выполнение запроса
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка SQL при добавлении услуги: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
        Class.forName(driver);
        String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
        return DriverManager.getConnection(url);
    }

    private static Timestamp parseTimestamp(String timeString) throws DateTimeParseException {
        try {
            System.out.println("Полученное время: " + timeString);
            // Парсим из формата datetime-local (yyyy-MM-ddTHH:mm)
            LocalDateTime dateTime = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return Timestamp.valueOf(dateTime);
        } catch (DateTimeParseException e) {
            System.err.println("Неверный формат времени: " + timeString +
                    ". Ожидается формат: yyyy-MM-ddTHH:mm");
            throw e;
        }
    }
}