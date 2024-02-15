package com.poscodx.movie_management.controller.basiccontroller;

import java.util.Map;

public interface BasicController {
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
