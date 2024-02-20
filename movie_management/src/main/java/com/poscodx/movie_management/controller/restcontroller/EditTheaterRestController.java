package com.poscodx.movie_management.controller.restcontroller;

import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.service.TheaterService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class EditTheaterRestController implements RestController{

    private final TheaterService theaterService = new TheaterService();

    @Override
    public void process(HttpServletRequest req, Map<String, String> paramMap, Map<String, Object> model) {
        int theaterId = Integer.parseInt(paramMap.getOrDefault("theaterId", "-1"));
        String name = paramMap.getOrDefault("name", "");
        String location = paramMap.getOrDefault("location", "1");
        String imageAddress = paramMap.getOrDefault("imageAddress", "");
        String tel = paramMap.getOrDefault("tel", "");

        TheaterDTO theater = new TheaterDTO();
        theater.setName(name);
        theater.setLocation(location);
        theater.setTel(tel);
        theater.setImageAddress(imageAddress);
        theater.setTheaterId(theaterId);
        theaterService.editTheater(theater);
    }
}
