package com.poscodx.movie_management.model;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

@Data
public class TheaterDTO {
    private Integer theaterId;
    private String name;
    private String location;
    private String tel;
    private byte[] thumbnail;
    private String imageAddress;
    private List<ScheduleDTO> scheduleList = new LinkedList<>();

    public TheaterDTO() {}
}
