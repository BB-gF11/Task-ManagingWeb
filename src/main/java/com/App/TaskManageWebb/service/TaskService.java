package com.App.TaskManageWebb.service;

import com.App.TaskManageWebb.dto.TaskRequest;
import com.App.TaskManageWebb.model.Task;

import java.util.List;

public interface TaskService {
    void creatTask(TaskRequest request);

    List<Task> getTasks(Long userId);
}
