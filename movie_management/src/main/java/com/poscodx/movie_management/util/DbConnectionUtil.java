package com.poscodx.movie_management.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.poscodx.movie_management.config.DbConfig.*;

public class DbConnectionUtil {

    public Connection getConnection() {
        Connection connetion = null;

        try {
            Class.forName(DRIVER);
            connetion = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connetion;
    }
}
