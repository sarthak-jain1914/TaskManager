package com.example.taskmanager.Service.UserService;

import com.example.taskmanager.DTOs.AuthResponse;
import com.example.taskmanager.DTOs.UserDtos.UserDto;
import com.example.taskmanager.DTOs.UserDtos.UserSignInDto;
import com.example.taskmanager.DTOs.UserDtos.UserUpdateDto;
import com.example.taskmanager.Exception.AuthorizationFailedException;
import com.example.taskmanager.Modles.User;

import java.util.List;

public interface UserServiceInterface {
    User createUser(UserDto userDto);

    AuthResponse signIn(UserSignInDto userSignInDto);

    User updateUser(Integer id, UserUpdateDto userUpdateDto);

    List<User> deleteUser(Integer id) throws AuthorizationFailedException;
}
