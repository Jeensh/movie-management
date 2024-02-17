package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class AuthRestController implements RestController{

    private final UserService userService = new UserService();
    @Override
    public void process(HttpServletRequest request, Map<String, String> paramMap, Map<String, Object> model) {
        String id = paramMap.getOrDefault("username", "");
        String pw = paramMap.getOrDefault("password", "");

        UserDTO user = userService.signIn(id, pw);
        if(user != null){
            user.setPassword(null);

            model.put("auth", true);
            model.put("userData", user);
            request.getSession().setAttribute("auth", user);
        }
        else{
            model.put("auth", false);
        }
    }
}
