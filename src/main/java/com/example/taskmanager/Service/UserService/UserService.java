package com.example.taskmanager.Service.UserService;

import com.example.taskmanager.Configuration.JwtAuthenticationFilter;
import com.example.taskmanager.Configuration.JwtUtil;
import com.example.taskmanager.DTOs.AuthResponse;
import com.example.taskmanager.DTOs.UserDtos.UserDto;
import com.example.taskmanager.DTOs.UserDtos.UserSignInDto;
import com.example.taskmanager.DTOs.UserDtos.UserUpdateDto;
import com.example.taskmanager.Exception.AuthorizationFailedException;
import com.example.taskmanager.Exception.SessionExpiryException;
import com.example.taskmanager.Exception.UserExistanceException;
import com.example.taskmanager.Modles.ENUMS.Roles;
import com.example.taskmanager.Modles.ENUMS.Status;
import com.example.taskmanager.Modles.Task;
import com.example.taskmanager.Modles.User;
import com.example.taskmanager.Repository.TaskRepo;
import com.example.taskmanager.Repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private  final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TaskRepo taskRepo;

    public UserService(JwtAuthenticationFilter jwtAuthenticationFilter, UserRepo userRepos, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil, TaskRepo taskRepo) {
        this.userRepo = userRepos;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.taskRepo = taskRepo;
    }


    @Override
    public User createUser(UserDto userDto) {

        Optional<User> existingUserByName = userRepo.findByName(userDto.getName());
        Optional<User> existingUserByEmail = userRepo.findByName(userDto.getEmail());

        if(existingUserByName.isPresent()) {
            //user already exist with name
            throw new UserExistanceException("User already exists with name: " + userDto.getName());
        }
        else if(existingUserByEmail.isPresent()) {
            //user already exist with email
            throw new UserExistanceException("User already exists with email: " + userDto.getEmail());
        }
        else{
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            if(userDto.getRole().equals("admin")) {
                user.setRole(Roles.ADMIN);
            }
            else if(userDto.getRole().equals("user")) {
                user.setRole(Roles.USER);
            }
            else if(userDto.getRole().isEmpty()){
                //throw error
                throw new UserExistanceException("User role can not be empty or should be correct: ");
            }
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setStatus(Status.ACTIV);
            return userRepo.save(user);
        }
    }

    @Override
    public AuthResponse signIn(UserSignInDto userSignInDto) {
        Optional<User> user = userRepo.findByName(userSignInDto.getUsername());

        if(user.isPresent() && bCryptPasswordEncoder.matches(userSignInDto.getPassword(), user.get().getPassword())) {
            String token = tokenGeneration(user.get().getId(), user.get().getRole());
            return new AuthResponse(token);
        }
        else{
            throw new UserExistanceException("User not found with name: " + userSignInDto.getUsername());
        }
    }

    @Override
    public User updateUser(Integer id, UserUpdateDto userUpdateDto) {
        String token = jwtAuthenticationFilter.getToken();
        validateToken(token);

        User user = userRepo.findById(id).get();
        if(id == jwtUtil.extractUserId(token)){
            //update details
            if(userUpdateDto.getEmail() != null){
                user.setEmail(userUpdateDto.getEmail());
            }
            if(userUpdateDto.getName() != null){
                user.setName(userUpdateDto.getName());
            }
        }
            return userRepo.save(user);
    }

    @Override
    public List<User> deleteUser(Integer id) throws AuthorizationFailedException {
        String token = jwtAuthenticationFilter.getToken();
        validateToken(token);
        if(userRepo.findById(id).isPresent() && jwtUtil.extractRole(token).equals(Roles.ADMIN)){
            User user = userRepo.findById(id).get();
            List<Task> tasks = user.getTasks();
            for(Task task : tasks){
                taskRepo.deleteById(task.getId());
            }
            userRepo.deleteById(id);
            return userRepo.findAll();
        }
        else{
            throw new AuthorizationFailedException("You are not authorized to delete this user");
        }

    }

    public String  tokenGeneration(int userId, Roles role){
            //generate token
                return jwtUtil.generateToken(userId, role);
    }

    public void validateToken(String token) {

        boolean validation = jwtUtil.validateToken(token);
        if(!validation){
            throw new SessionExpiryException("Session Expired try to login again");
        }
    }

}
