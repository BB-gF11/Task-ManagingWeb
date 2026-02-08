package com.App.TaskManageWebb.service.impl;

import com.App.TaskManageWebb.dto.TaskRequest;
import com.App.TaskManageWebb.model.Task;
import com.App.TaskManageWebb.model.User;
import com.App.TaskManageWebb.repository.TaskRepository;
import com.App.TaskManageWebb.repository.UserRepository;
import com.App.TaskManageWebb.service.TaskService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    @Override
    public void creatTask(TaskRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //get the user so i can set it to the task
        User user = userRepository.findByUsername(username).get();
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getTasks(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //get the authenticated user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        List<Task> tasks=taskRepository.findByUserId(user.getId());
        return tasks ;
    }
}
