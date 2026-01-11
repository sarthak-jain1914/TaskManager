package com.example.taskmanager.Controller;

import com.example.taskmanager.DTOs.AuthResponse;
import com.example.taskmanager.DTOs.UserDtos.UserDto;
import com.example.taskmanager.DTOs.UserDtos.UserSignInDto;
import com.example.taskmanager.DTOs.UserDtos.UserUpdateDto;
import com.example.taskmanager.Exception.AuthorizationFailedException;
import com.example.taskmanager.Modles.User;
import com.example.taskmanager.Service.UserService.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> SignIn(@RequestBody UserSignInDto userSignInDto) {

        AuthResponse response = userService.signIn(userSignInDto);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id,userUpdateDto);
    }

    @DeleteMapping("/delete-user/{id}")
    public List<User> deleteUser(@PathVariable Integer id) throws AuthorizationFailedException {
        return userService.deleteUser(id);
    }
}
