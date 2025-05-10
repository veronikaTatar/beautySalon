package DB;

import SalonClass.Booking;
import com.example.ConfigurationManager;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ViewBookingAdminLogic {

        public static ArrayList<Booking> getBookings() {
            ArrayList<Booking> bookingList = new ArrayList<>();

            // Форматтер для преобразования времени из БД
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            String query = "SELECT o.idOrder, s.NAME as serviceName, s.TYPE as serviceType, " +
                    "s.MASTER as masterName, u.NAME as clientName, u.SURNAME as clientSurname, " +
                    "u.EMAIL as clientEmail, o.confirmation, s.TIME as serviceTime " + // Добавляем время услуги
                    "FROM `order` o " +
                    "JOIN service s ON o.service_IdService = s.idService " +
                    "JOIN users u ON o.users_idUser = u.idUser";

            try (Connection cn = DriverManager.getConnection(
                    ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
                 PreparedStatement st = cn.prepareStatement(query);
                 ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    // Получаем и форматируем время
                    Timestamp timestamp = rs.getTimestamp("serviceTime");
                    String formattedTime = timestamp != null ?
                            timestamp.toLocalDateTime().format(displayFormatter) : "Не указано";

                    // Создаем объект Booking с использованием обновленного конструктора
                    Booking booking = new Booking(
                            rs.getInt("idOrder"),
                            rs.getString("serviceName"),
                            rs.getString("serviceType"),
                            rs.getString("masterName"),
                            rs.getString("clientName") + " " + rs.getString("clientSurname"),
                            rs.getString("clientEmail"),
                            rs.getInt("confirmation") == 1 ? "Подтверждено" : "Ожидание",
                            formattedTime  // Добавляем отформатированное время
                    );
                    bookingList.add(booking);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при получении списка заявок: " + e.getMessage());
                return null;
            }
            return bookingList;
        }
    //для конструктора без time
/*    public static ArrayList<Booking> getBookings() {
        ArrayList<Booking> bookingList = new ArrayList<>();

        String query = "SELECT o.idOrder, s.NAME as serviceName, s.TYPE as serviceType, " +
                "s.MASTER as masterName, u.NAME as clientName, u.SURNAME as clientSurname, " +
                "u.EMAIL as clientEmail, o.confirmation " +
                "FROM `order` o " +
                "JOIN service s ON o.service_IdService = s.idService " +
                "JOIN users u ON o.users_idUser = u.idUser";

        try (Connection cn = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("idOrder"),
                        rs.getString("serviceName"),
                        rs.getString("serviceType"),
                        rs.getString("masterName"),
                        rs.getString("clientName") + " " + rs.getString("clientSurname"),
                        rs.getString("clientEmail"),
                        rs.getInt("confirmation") == 1 ? "Подтверждено" : "Ожидание"
                );
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return bookingList;
    }*/

    public static ArrayList<Booking> getBookingsByUser(Integer userId) {
        ArrayList<Booking> bookingList = new ArrayList<>();

        String query = "SELECT o.idOrder, s.NAME as serviceName, s.TYPE as serviceType, " +
                "s.MASTER as masterName, u.NAME as clientName, u.SURNAME as clientSurname, " +
                "u.EMAIL as clientEmail, o.confirmation, s.TIME as serviceTime " + // Добавляем поле времени
                "FROM `order` o " +
                "JOIN service s ON o.service_IdService = s.idService " +
                "JOIN users u ON o.users_idUser = u.idUser " +
                (userId != null ? "WHERE o.users_idUser = ?" : "");

        try (Connection cn = DriverManager.getConnection(
                ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL));
             PreparedStatement st = cn.prepareStatement(query)) {

            if (userId != null) {
                st.setInt(1, userId);
            }

            try (ResultSet rs = st.executeQuery()) {
                DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

                while (rs.next()) {
                    // Получаем время из базы и форматируем для отображения
                    Timestamp timestamp = rs.getTimestamp("serviceTime");
                    String formattedTime = timestamp != null ?
                            timestamp.toLocalDateTime().format(displayFormatter) : "Не указано";

                    Booking booking = new Booking(
                            rs.getInt("idOrder"),
                            rs.getString("serviceName"),
                            rs.getString("serviceType"),
                            rs.getString("masterName"),
                            rs.getString("clientName") + " " + rs.getString("clientSurname"),
                            rs.getString("clientEmail"),
                            rs.getInt("confirmation") == 1 ? "Подтверждено" : "Ожидание",
                            formattedTime  // Добавляем отформатированное время
                    );
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении заявок: " + e.getMessage());
            return null;
        }
        return bookingList;
    }
}