/*
package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import com.example.ConfigurationManager;

public class LoginLogic {
    public static String checkLogin(String login, String password) {
        // проверка логина и пароля
        try {
            String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
            Class.forName(driver);
            Connection cn = null;
            try {
                String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                cn = DriverManager.getConnection(url);
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement(
                            "SELECT role FROM USERS WHERE LOGIN = ? AND PASSWORD = ?"); // Запрос только роли
                    st.setString(1, login);
                    st.setString(2, password);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();

                        if (rs.next()) {
                            return rs.getString("role"); // Возвращаем роль
                        } else {
                            return null; // Пользователь не найден
                        }
                    } finally {
                        if (rs != null)
                            rs.close();
                    }
                } finally {
                    if (st != null)
                        st.close();
                }
            } finally {
                if (cn != null)
                    cn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Ошибка SQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Ошибка класса
        }
    }
}*/

package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import com.example.ConfigurationManager;

public class LoginLogic {
    //если с сессией
    public static Object[] checkLogin(String login, String password) {
        try {
            String driver = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_DRIVER_NAME);
            Class.forName(driver);
            Connection cn = null;
            try {
                String url = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DATABASE_URL);
                cn = DriverManager.getConnection(url);
                PreparedStatement st = null;
                try {
                    // Запрос теперь возвращает и роль, и ID
                    st = cn.prepareStatement(
                            "SELECT idUser, role FROM USERS WHERE LOGIN = ? AND PASSWORD = ?");
                    st.setString(1, login);
                    st.setString(2, password);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();

                        if (rs.next()) {
                            return new Object[]{rs.getString("role"), rs.getInt("idUser")};
                        } else {
                            return null;
                        }
                    } finally {
                        if (rs != null)
                            rs.close();
                    }
                } finally {
                    if (st != null)
                        st.close();
                }
            } finally {
                if (cn != null)
                    cn.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}