package DB;

import com.example.ConfigurationManager;
import java.sql.*;

public class BookingLogic {
/*
    public static boolean bookService(int userId, int serviceId) throws SQLException {
        String sql = "INSERT INTO `order` (service_IdService, users_idUser, confirmation) VALUES (?, ?, 0)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
*/

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
        Class.forName(driver);
        String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
        return DriverManager.getConnection(url);
    }
    public static boolean bookService(int userId, int serviceId) throws SQLException {
        // Сначала проверяем доступность услуги
        if (!ViewServiceLogic.isServiceAvailable(serviceId)) {
            throw new SQLException("Услуга недоступна для бронирования (либо не существует, либо уже забронирована)");
        }

        String sql = "INSERT INTO `order` (service_idService, users_idUser, confirmation) VALUES (?, ?, 0)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Ошибка подключения к базе данных", e);
        }
    }
}