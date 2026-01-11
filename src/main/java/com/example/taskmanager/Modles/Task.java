package com.example.taskmanager.Modles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@Entity
public class Task extends BaseModle{

    private String name;
    private String description;
    private Date schedule;
    private boolean completed;
    @ManyToOne
    @JsonIgnore
    private User user;
}
