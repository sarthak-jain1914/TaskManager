package com.example.taskmanager.Modles;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModle {
    @Id
    private int id;

    private String name;
    private Date createdAt;
    private Date updatedAt;
}
