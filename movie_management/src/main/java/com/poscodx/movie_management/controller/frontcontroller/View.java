package com.poscodx.movie_management.controller.frontcontroller;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class View {

    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(Map<String, Object> model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        modelToRequestAttribute(model, request);
        if ((boolean) model.getOrDefault("rest", false)) {
            Gson gson = new Gson();
            String jsonData = gson.toJson(model);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonData);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
            dispatcher.forward(request, response);
        }
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach(request::setAttribute);
    }
}
