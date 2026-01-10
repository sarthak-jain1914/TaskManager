package com.example.taskmanager.Modles;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @OneToMany
    @JsonBackReference
    private List<Task> tasks;

}
