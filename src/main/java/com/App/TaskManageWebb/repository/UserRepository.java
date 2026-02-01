package com.App.TaskManageWebb.repository;

import com.App.TaskManageWebb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public boolean existsByEmail(String email);
    public User findByEmail(String email);
    Optional<User> findByUsername(String username);
}
