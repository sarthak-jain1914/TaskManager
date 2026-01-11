package com.example.taskmanager.Service.TaskService;

import com.example.taskmanager.Configuration.JwtAuthenticationFilter;
import com.example.taskmanager.Configuration.JwtUtil;
import com.example.taskmanager.DTOs.TaskDtos.TaskDto;
import com.example.taskmanager.DTOs.TaskDtos.TaskUpdateRequestDto;
import com.example.taskmanager.Exception.SessionExpiryException;
import com.example.taskmanager.Exception.TaskNotFoundException;
import com.example.taskmanager.Exception.UserExistanceException;
import com.example.taskmanager.Modles.Task;
import com.example.taskmanager.Modles.User;
import com.example.taskmanager.Repository.TaskRepo;
import com.example.taskmanager.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskServiceInterface {

    private TaskRepo taskRepo;
    private UserRepo userRepo;
    private JwtUtil jwtUtil;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public TaskService(UserRepo userRepo,TaskRepo taskRepos,JwtUtil jwtUtil,JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.taskRepo = taskRepos;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userRepo = userRepo;
    }


    public List<Task> getAllTask() {
        validateToken(jwtAuthenticationFilter.getToken());
        return taskRepo.findAll();
    }
    public Task getTaskById(int id) throws TaskNotFoundException {
        validateToken(jwtAuthenticationFilter.getToken());
        Optional<User> user = userRepo.findById(jwtUtil.extractUserId(jwtAuthenticationFilter.getToken()));

        List<Task> tasks = user.get().getTasks();

        Task t = null;
        for(int i = 0; i < tasks.size();i++){
            if(tasks.get(i).getId() == id){
                t = tasks.get(i);
            }
        }
        if(t == null){
            throw new TaskNotFoundException("Task not found");
        }
        else{
            return t;
        }
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        validateToken(jwtAuthenticationFilter.getToken());
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setSchedule(taskDto.getSchedule());

        Optional<User> user = userRepo.findById(jwtUtil.extractUserId(jwtAuthenticationFilter.getToken()));
        if(user.isEmpty()){
            throw new UserExistanceException("User not found");
        }
        else{
            task.setUser(user.get());
        }
        return taskRepo.save(task);
    }
/// update task
    @Override
    public Task updateTask(TaskUpdateRequestDto taskUpdateRequestDto,int id) throws TaskNotFoundException {
        validateToken(jwtAuthenticationFilter.getToken());

        Optional<User> user = userRepo.findById(jwtUtil.extractUserId(jwtAuthenticationFilter.getToken()));

        List<Task> tasks = user.get().getTasks();

        Task t = null;
        for(int i = 0; i < tasks.size();i++){
            if(tasks.get(i).getId() == id){
                t = tasks.get(i);
            }
        }
        if(t != null){
            if(taskUpdateRequestDto.getDescription() != null){
                t.setDescription(taskUpdateRequestDto.getDescription());
            }
            if(taskUpdateRequestDto.getSchedule() != null){
                t.setSchedule(taskUpdateRequestDto.getSchedule());
            }
            if(taskUpdateRequestDto.getName() != null){
            t.setName(taskUpdateRequestDto.getName());
            }
        }
        else{
            throw new TaskNotFoundException("Task not found");
        }
        return taskRepo.save(t);

    }

    public void validateToken(String token) {

        boolean validation = jwtUtil.validateToken(token);
        if(!validation){
            throw new SessionExpiryException("Session Expired try to login again");
        }
    }

    public void deleteTask(int id) throws TaskNotFoundException {
        validateToken(jwtAuthenticationFilter.getToken());

        Optional<User> user = userRepo.findById(jwtUtil.extractUserId(jwtAuthenticationFilter.getToken()));

        List<Task> tasks = user.get().getTasks();

        Task t = null;
        for(int i = 0; i < tasks.size();i++){
            if(tasks.get(i).getId() == id){
                t = tasks.get(i);
            }
        }
        if(t != null){
            taskRepo.deleteById(id);
        }
        else{
            throw new TaskNotFoundException("Task does not exist");
        }
    }
}
