

package DB;

import com.example.ConfigurationManager;
import SalonClass.Service;
import java.sql.*;
import java.util.ArrayList;
/*
public class ViewServiceLogic {
    public static ArrayList<Service> getAvailableServices() {
        ArrayList<Service> serviceList = new ArrayList<>();

        // SQL-запрос для получения услуг, которых нет в таблице order
        String query = "SELECT s.idService, s.NAME, s.TYPE, s.MASTER, s.TIME, s.PRICE " +
                "FROM service s " +
                "LEFT JOIN `order` o ON s.idService = o.service_IdService " +
                "WHERE o.service_IdService IS NULL";

        try (Connection cn = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Service service = new Service(
                        rs.getInt("idService"),
                        rs.getString("NAME"),
                        rs.getString("TYPE"),
                        rs.getString("MASTER"),
                        rs.getString("TIME"),
                        rs.getString("PRICE")
                );
                serviceList.add(service);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении доступных услуг: " + e.getMessage());
            return null;
        }
        return serviceList;
    }
}*/

public class ViewServiceLogic {
    public static ArrayList<Service> getAvailableServices() {
        ArrayList<Service> serviceList = new ArrayList<>();


        String query = "SELECT s.idService, s.NAME, s.TYPE, s.MASTER, s.TIME, s.PRICE " +
                "FROM service s " +
                "WHERE s.idService NOT IN (SELECT service_idService FROM `order`)";
        try (Connection cn = getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Service service = new Service(
                        rs.getInt("idService"),
                        rs.getString("NAME"),
                        rs.getString("TYPE"),
                        rs.getString("MASTER"),
                        rs.getString("TIME"),
                        rs.getString("PRICE")
                );
                serviceList.add(service);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при получении доступных услуг: " + e.getMessage());
            return null;
        }
        return serviceList;
    }

    public static boolean isServiceAvailable(int serviceId) {
        String query = "SELECT 1 FROM service s " +
                "WHERE s.idService = ? AND " +
                "NOT EXISTS (SELECT 1 FROM `order` o WHERE o.service_idService = s.idService)";

        try (Connection cn = getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {

            st.setInt(1, serviceId);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при проверке доступности услуги: " + e.getMessage());
            return false;
        }
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
        Class.forName(driver);
        String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
        return DriverManager.getConnection(url);
    }
}