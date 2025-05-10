package DB;

import com.example.ConfigurationManager;
import java.sql.*;

public class UpdateBookingStatusLogic {
    public static boolean updateStatus(int bookingId) {
        String sql = "UPDATE `order` SET confirmation = CASE WHEN confirmation = 1 THEN 0 ELSE 1 END WHERE idOrder = ?";

        try (Connection connection = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при изменении статуса: " + e.getMessage());
            return false;
        }
    }
}
