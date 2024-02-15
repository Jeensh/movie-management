package com.poscodx.movie_management.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.poscodx.movie_management.config.DbConfig.*;

public class DbConnectionUtil {

    private static DataSource dataSource;
    public DbConnectionUtil(){
        if(dataSource == null){
        // HikariCP 설정
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        // HikariCP 데이터 소스 생성
        dataSource = new HikariDataSource(config);
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
