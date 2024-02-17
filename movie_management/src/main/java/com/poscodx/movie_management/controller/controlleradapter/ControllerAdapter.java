package com.poscodx.movie_management.controller.controlleradapter;

import com.poscodx.movie_management.controller.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerAdapter {
    boolean supports(Object controller);
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object controller)
        throws ServletException, IOException;
}
