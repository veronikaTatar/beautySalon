
package DB;

import com.example.ConfigurationManager;
import java.sql.*;

public class DeleteBookingLogic {

    // Удаление заявки по ID
    public static boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM `order` WHERE idOrder = ?";

        try (Connection connection = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении заявки: " + e.getMessage());
            return false;
        }
    }

    // Удаление услуги по ID услуги, связанной с заявкой
    public static boolean deleteServiceByOrderId(int orderId) {
        // Сначала получаем service_idService из таблицы order
        String getServiceIdSql = "SELECT service_idService FROM `order` WHERE idOrder = ?";
        String deleteServiceSql = "DELETE FROM service WHERE idService = ?";

        try (Connection connection = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement getServiceStmt = connection.prepareStatement(getServiceIdSql);
             PreparedStatement deleteServiceStmt = connection.prepareStatement(deleteServiceSql)) {

            // Получаем ID связанной услуги
            getServiceStmt.setInt(1, orderId);
            ResultSet rs = getServiceStmt.executeQuery();

            if (rs.next()) {
                int serviceId = rs.getInt("service_idService");

                // Удаляем услугу
                deleteServiceStmt.setInt(1, serviceId);
                return deleteServiceStmt.executeUpdate() > 0;
            }
            return false; // Если запись не найдена

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении связанной услуги: " + e.getMessage());
            return false;
        }
    }
}
