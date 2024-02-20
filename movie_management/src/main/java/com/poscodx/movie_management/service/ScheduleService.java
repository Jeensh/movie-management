package com.poscodx.movie_management.service;

import com.poscodx.movie_management.config.UserGrade;
import com.poscodx.movie_management.model.ReviewDTO;
import com.poscodx.movie_management.model.ScheduleDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.repository.ScheduleRepository;

import java.util.List;

public class ScheduleService {
    private static final ScheduleRepository scheduleRepository = new ScheduleRepository();

    public List<ScheduleDTO> getSchedulesByTheaterId(int theaterId, int size, int pageNumber, UserDTO user){
        if(user.getGrade() == UserGrade.ADMIN){
            return scheduleRepository.findALLByTheaterIdAndRange(theaterId, size, pageNumber);
        }
        else{
            return scheduleRepository.findByTheaterIdAndRange(theaterId, size, pageNumber);
        }
    }

    public void addSchedule(ScheduleDTO schedule){
        scheduleRepository.add(schedule);
    }

    public int getScheduleCountByTheaterId(int movieId, UserDTO user){
        if(user.getGrade() == UserGrade.ADMIN){
            return scheduleRepository.findAllScheduleCountByTheaterId(movieId);
        }
        else {
            return scheduleRepository.findScheduleCountByTheaterId(movieId);
        }
    }

    public void editSchedule(ScheduleDTO schedule){
        scheduleRepository.update(schedule);
    }

    public void deleteSchedule(int scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }
}
