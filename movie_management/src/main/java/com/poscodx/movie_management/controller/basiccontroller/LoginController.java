package com.poscodx.movie_management.controller.basiccontroller;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

public class LoginController implements BasicController {
    @Override
    public String process(HttpSession session, Map<String, String> paramMap, Map<String, Object> model) {
        session.invalidate();
        return "user/login";
    }
}
