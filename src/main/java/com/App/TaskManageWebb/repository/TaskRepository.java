package com.App.TaskManageWebb.repository;

import com.App.TaskManageWebb.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findTasksByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

}
