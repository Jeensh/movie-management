package com.poscodx.movie_management.model;

import com.poscodx.movie_management.config.UserGrade;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {
    private Integer userId;
    private String userName;
    private String password;
    private String nickname;
    private UserGrade grade;
    public void setGrade(int i) {
        if(i == 1)
            this.grade = UserGrade.NORMAL;
        else if(i == 2)
            this.grade = UserGrade.EXPERT;
        else if(i == 3)
            this.grade = UserGrade.ADMIN;
    }
}
