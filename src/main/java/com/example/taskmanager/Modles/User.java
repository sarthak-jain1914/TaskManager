package com.example.taskmanager.Modles;

import com.example.taskmanager.Modles.ENUMS.Roles;
import com.example.taskmanager.Modles.ENUMS.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModle {
    private String email;
    private String password;
    private Status status;
    private Roles role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Task> tasks;

}
