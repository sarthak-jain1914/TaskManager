package com.example.taskmanager.DTOs.TaskDtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskUpdateRequestDto {

    private String name;
    private String description;
    private Date schedule;
    private boolean completed;
}
