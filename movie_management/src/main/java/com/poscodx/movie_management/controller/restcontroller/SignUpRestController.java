package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class SignUpRestController implements RestController{

    private final UserService userService = new UserService();
    @Override
    public void process(HttpServletRequest request, Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.getOrDefault("username", null);
        String pw = paramMap.getOrDefault("password", null);
        String nickname = paramMap.getOrDefault("nickname", null);

        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(pw);
        user.setNickname(nickname);
        user.setGrade(UserGrade.NORMAL.getValue());

        userService.signUp(user);
        System.out.println(request.getSession().getAttribute("auth"));
    }
}
