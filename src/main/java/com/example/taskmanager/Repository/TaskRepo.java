package com.example.taskmanager.Repository;

import com.example.taskmanager.Modles.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findAll();
    Optional<Task> findById(int id);
}
