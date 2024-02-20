package com.poscodx.movie_management.controller.controlleradapter;

import com.poscodx.movie_management.controller.basiccontroller.BasicController;
import com.poscodx.movie_management.controller.frontcontroller.ModelView;
import com.poscodx.movie_management.controller.restcontroller.RestController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BasicControllerAdapter implements ControllerAdapter{
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof BasicController);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object controller)
            throws ServletException, IOException {

        BasicController basicController = (BasicController) controller;

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = basicController.process(request.getSession(), paramMap, model);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining((paramName) -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
