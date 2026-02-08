package com.App.TaskManageWebb.controllers;

import com.App.TaskManageWebb.dto.TaskRequest;
import com.App.TaskManageWebb.model.Task;
import com.App.TaskManageWebb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    public void creatTask(@RequestBody TaskRequest request){
        service.creatTask(request);
    }

    @GetMapping
    public List<Task> getTasks(){
        return service.getTasks();
    }
    @PutMapping("/{taskId}")
    public void updateTask(@PathVariable Long taskId,@RequestBody TaskRequest request){
        service.updateTask(taskId,request);
    }
    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        service.deleteTask(taskId);
    }
}
