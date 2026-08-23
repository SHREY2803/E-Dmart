package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {

        String dbUrl =
                System.getenv("DB_URL");

        String dbUser =
                System.getenv("DB_USER");

        String dbPassword =
                System.getenv("DB_PASSWORD");


        /*
         * Local / existing configuration
         */
        if (dbUrl != null &&
                !dbUrl.isBlank()) {

            URL = dbUrl;
            USER = dbUser;
            PASSWORD = dbPassword;

        }

        /*
         * Railway configuration
         */
        else {

            String host =
                    System.getenv("MYSQLHOST");

            String port =
                    System.getenv("MYSQLPORT");

            String database =
                    System.getenv("MYSQLDATABASE");

            USER =
                    System.getenv("MYSQLUSER");

            PASSWORD =
                    System.getenv("MYSQLPASSWORD");


            URL =
                    "jdbc:mysql://"
                            + host
                            + ":"
                            + port
                            + "/"
                            + database
                            + "?useSSL=false"
                            + "&allowPublicKeyRetrieval=true"
                            + "&serverTimezone=UTC";
        }
    }


    // This method gives DB connection
    public static Connection getConnection() {

        try {

            // Load MySQL JDBC Driver
            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );


            if (URL == null ||
                    USER == null ||
                    PASSWORD == null) {

                throw new RuntimeException(
                        "Database configuration is missing."
                );
            }


            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );


        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}