package com.poscodx.movie_management.config;

import com.poscodx.movie_management.util.DbConnectionUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ServletListenerEx implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new DbConnectionUtil();
    }
}
