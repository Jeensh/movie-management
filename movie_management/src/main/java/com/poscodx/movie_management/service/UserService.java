package com.poscodx.movie_management.service;

import com.poscodx.movie_management.model.TheaterDTO;
import com.poscodx.movie_management.model.UserDTO;
import com.poscodx.movie_management.repository.UserRepository;

import java.util.List;

public class UserService {
    private static final UserRepository userRepository = new UserRepository();

    public UserDTO signIn(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void signUp(UserDTO newUser) {
        userRepository.add(newUser);
    }

    public UserDTO findById(int userId){
        return userRepository.findById(userId);
    }

    public List<UserDTO> findUserByRange(int pageNumber, int size){
        List<UserDTO> list = userRepository.findByRange(size, pageNumber);
        return list;
    }

    public void editUser(UserDTO user){
        userRepository.update(user);
    }

    public int getTotalCount(){
        return userRepository.findUserCount();
    }
}
