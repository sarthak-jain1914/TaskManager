package com.example.taskmanager.Service.TaskService;

import com.example.taskmanager.DTOs.TaskDtos.TaskDto;
import com.example.taskmanager.DTOs.TaskDtos.TaskUpdateRequestDto;
import com.example.taskmanager.Exception.TaskNotFoundException;
import com.example.taskmanager.Modles.Task;

import java.util.List;

public interface TaskServiceInterface {
    List<Task> getAllTask();
    Task getTaskById(int id) throws TaskNotFoundException;
    Task createTask(TaskDto taskDto);
    Task updateTask(TaskUpdateRequestDto taskUpdateRequestDto, int id) throws TaskNotFoundException;
    void deleteTask(int id) throws TaskNotFoundException;
}
