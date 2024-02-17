package com.poscodx.movie_management.controller.restcontroller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface RestController {
    void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model);
}
