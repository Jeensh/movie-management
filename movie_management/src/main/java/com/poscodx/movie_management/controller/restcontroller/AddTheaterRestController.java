package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.MovieDTO;
import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.service.MovieService;
import com.poscodx.movie_management.service.TheaterService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class AddTheaterRestController implements RestController{

    private final TheaterService theaterService = new TheaterService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        String name = paramMap.getOrDefault("name", "");
        String location = paramMap.getOrDefault("location", "1");
        String imageAddress = paramMap.getOrDefault("imageAddress", "");
        String tel = paramMap.getOrDefault("tel", "");

        TheaterDTO theater = new TheaterDTO();
        theater.setName(name);
        theater.setLocation(location);
        theater.setTel(tel);
        theater.setImageAddress(imageAddress);
        theaterService.addTheater(theater);
    }
}
