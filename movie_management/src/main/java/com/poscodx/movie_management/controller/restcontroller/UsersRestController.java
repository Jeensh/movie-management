package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.ReviewService;
import com.poscodx.movie_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class UsersRestController implements RestController{

    private final UserService userService = new UserService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int pageNumber = Integer.parseInt(paramMap.getOrDefault("pageNumber", "-1"));
        if(pageNumber > 0){
            List<UserDTO> list = userService.findUserByRange(pageNumber, 10);
            model.put("users", list);
        }
        if(pageNumber == 0){
            int total = userService.getTotalCount();
            model.put("total", total);
        }
    }
}
