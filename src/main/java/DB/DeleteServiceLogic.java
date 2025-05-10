package DB;

import com.example.ConfigurationManager;
import java.sql.*;

public class DeleteServiceLogic {

    // Проверка, есть ли связанные заявки
    private static boolean hasRelatedOrders(int serviceId) throws SQLException {
        String sql = "SELECT 1 FROM `order` WHERE service_idService = ? LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Если есть результаты - значит есть связанные заявки
            }
        }
    }

    // Удаление услуги только если нет связанных заявок
    public static boolean deleteServiceSafely(int serviceId) {
        try {
            // Проверяем наличие связанных заявок
            if (hasRelatedOrders(serviceId)) {
                System.err.println("Нельзя удалить услугу с ID " + serviceId +
                        " - есть связанные заявки");
                return false;
            }

            // Если связанных заявок нет - удаляем
            String sql = "DELETE FROM service WHERE idService = ?";

            try (Connection connection = getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1, serviceId);
                int affectedRows = stmt.executeUpdate();

                return affectedRows > 0;
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при безопасном удалении услуги: " + e.getMessage());
            return false;
        }
    }

    // Стандартное удаление (без проверки связей)
    public static boolean deleteService(int serviceId) {
        String sql = "DELETE FROM service WHERE idService = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении услуги: " + e.getMessage());
            return false;
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
        return DriverManager.getConnection(url);
    }
}