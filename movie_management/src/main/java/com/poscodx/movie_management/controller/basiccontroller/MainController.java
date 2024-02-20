package com.poscodx.movie_management.controller.basiccontroller;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.UserDTO;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public class MainController implements BasicController {
    @Override
    public String process(HttpSession session, Map<String, String> paramMap, Map<String, Object> model) {
        UserDTO user = (UserDTO) session.getAttribute("auth");
        if(user.getGrade() == UserGrade.NORMAL || user.getGrade() == UserGrade.EXPERT){
            return "main/main";
        }
        else if (user.getGrade() == UserGrade.ADMIN){
            return "main/adminmain";
        }
        return "user/login";
    }
}
