package util;

import java.sql.Connection;

public class DbTest {
    public static void main() {
        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("DATABASE CONNECTED SUCCESSFULLY!");
        } else {
            System.out.println("DATABASE CONNECTION FAILED!");
        }
    }
}
