package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IniciarConexao {
    private static String url = "jdbc:postgresql://127.0.0.1:5432/BancoSQL";
    private static String username = "postgres";
    private static String password = "1234";

    public static Connection startConnection() {
            try {
                return DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return null;
    }
}
