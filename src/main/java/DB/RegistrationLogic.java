package DB;

import com.example.ConfigurationManager;

import java.sql.*;

public class RegistrationLogic {

    private static final String DEFAULT_ROLE = "client";

    public static boolean addUser(String name, String surname, String email, String login, String password) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean result = false;

        try {
            // 1. Загрузка драйвера
            String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
            Class.forName(driver);

            // 2. Установка соединения
            String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
            connection = DriverManager.getConnection(url);

            // 3. Подготовка SQL-запроса
            String sql = "INSERT INTO users (LOGIN, PASSWORD, ROLE, NAME, SURNAME, EMAIL) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, DEFAULT_ROLE);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, surname);
            preparedStatement.setString(6, email);

            // 4. Выполнение запроса
            int rowsAffected = preparedStatement.executeUpdate();
            result = rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            System.err.println("Ошибка: Не найден JDBC драйвер: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("Ошибка SQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // 5. Закрытие ресурсов
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии PreparedStatement: " + e.getMessage());
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии Connection: " + e.getMessage());
            }
        }

        return result;
    }
}