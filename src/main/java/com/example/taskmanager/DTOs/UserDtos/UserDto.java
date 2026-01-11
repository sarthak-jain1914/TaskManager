package com.example.taskmanager.DTOs.UserDtos;

import com.example.taskmanager.Modles.ENUMS.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String name;
    private String email;
    private String password;
    private Status status;
    private String role;

}
