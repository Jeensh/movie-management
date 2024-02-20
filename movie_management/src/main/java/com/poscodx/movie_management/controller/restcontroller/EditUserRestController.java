package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class EditUserRestController implements RestController {

    private final UserService userService = new UserService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int userId = Integer.parseInt(paramMap.getOrDefault("userId", "-1"));
        int grade = Integer.parseInt(paramMap.get("grade"));
        String password = paramMap.get("password");
        String nickname = paramMap.get("nickname");

        UserDTO user = new UserDTO();

        UserDTO origin = userService.findById(userId);

        // password 비워져 있을 시 원래 패스워드로 등록
        if (password == null || password.isEmpty()) {
            password = origin.getPassword();
        }
        // username은 변경불가
        String username = origin.getUserName();
        // 관리자 등급변경이나 관리자로 등급변경은 불가
        if(grade != 3 && origin.getGrade() != UserGrade.ADMIN){
            user.setUserId(userId);
            user.setUserName(username);
            user.setPassword(password);
            user.setNickname(nickname);
            user.setGrade(grade);
            userService.editUser(user);
        }
    }
}
