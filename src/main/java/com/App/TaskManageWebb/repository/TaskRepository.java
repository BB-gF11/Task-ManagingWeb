package com.App.TaskManageWebb.repository;

import com.App.TaskManageWebb.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
