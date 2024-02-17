package com.poscodx.movie_management.controller.basiccontroller;

import java.util.Map;

public class SignUpController implements BasicController {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "user/signup";
    }
}
