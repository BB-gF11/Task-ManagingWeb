package com.App.TaskManageWebb.controllers;

import com.App.TaskManageWebb.dto.TaskRequest;
import com.App.TaskManageWebb.model.Task;
import com.App.TaskManageWebb.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskService service;

    @PostMapping
    public void creatTask(@RequestBody TaskRequest request){
        service.creatTask(request);
    }

    @GetMapping
    public List<Task> getTasks(Long userId){
        return service.getTask(userId);
    }
}
