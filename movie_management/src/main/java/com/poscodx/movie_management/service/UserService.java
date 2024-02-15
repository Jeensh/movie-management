package com.poscodx.movie_management.service;

import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.repository.UserRepository;

public class UserService {
    private static final UserRepository userRepository = new UserRepository();

    public UserDTO signIn(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void signUp(UserDTO newUser) {
        userRepository.add(newUser);
    }
}
