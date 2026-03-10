package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public Connection getConnection() throws Exception {

        String url = "jdbc:postgresql://localhost:5432/garage";
        String user = "postgres";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }
}