package com.example.taskmanager.Repository;

import com.example.taskmanager.Modles.User;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByName(String username);

    Optional<User> findByNameAndPassword(String username, @Nullable String encode);



    Optional<User> findByPassword(String password);
}
