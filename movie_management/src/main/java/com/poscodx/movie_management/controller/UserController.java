package com.poscodx.movie_management.controller;

import com.poscodx.movie_management.repository.UserRepository;
import com.poscodx.movie_management.util.DbConnectionUtil;

public class UserController {
    private static final UserRepository userRepository = new UserRepository(DbConnectionUtil.getDataSource());


}
