package com.example.taskmanager.Controller;

import com.example.taskmanager.DTOs.TaskDtos.TaskDto;
import com.example.taskmanager.DTOs.TaskDtos.TaskUpdateRequestDto;
import com.example.taskmanager.Exception.TaskNotFoundException;
import com.example.taskmanager.Modles.Task;
import com.example.taskmanager.Service.TaskService.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/All-Task")
    public List<Task> getAllTasks() {
        return taskService.getAllTask();
    }

    @GetMapping("/Task/{id}")
    public Task getTaskById(@PathVariable Integer id) throws TaskNotFoundException {
        return taskService.getTaskById(id);
    }

    @PostMapping ("/create-task")
    public Task createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PatchMapping("/update-task/{id}")
    public Task updateTask(@RequestBody TaskUpdateRequestDto taskUpdateRequestDto,@PathVariable Integer id) throws TaskNotFoundException {
        return taskService.updateTask(taskUpdateRequestDto,id);
    }

    @DeleteMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable Integer id) throws TaskNotFoundException {
        taskService.deleteTask(id);
        return "successfully deleted";
    }

}
