package com.App.TaskManageWebb.service.impl;

import com.App.TaskManageWebb.dto.TaskRequest;
import com.App.TaskManageWebb.model.Task;
import com.App.TaskManageWebb.model.User;
import com.App.TaskManageWebb.repository.TaskRepository;
import com.App.TaskManageWebb.repository.UserRepository;
import com.App.TaskManageWebb.service.TaskService;
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
    public List<Task> getTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //get the authenticated user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        List<Task> tasks=taskRepository.findTasksByUserId(user.getId());
        return tasks ;
    }

    @Override
    public void updateTask(Long taskId, TaskRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("task not found"));
        //there is another way by making a function in the repo and the varification will be done in the DB
        //Optional<Task> findByIdAndUserId(Long taskId, Long userId);
        //i will try it later
        if(!task.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You are not allowed to update this task");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        taskRepository.save(task);


    }

    @Override
    public void deleteTask(Long taskId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String userName = authentication.getName();
      User user = userRepository.findByUsername(userName)
                                  .orElseThrow(() -> new RuntimeException("user not found"));
      Task task = taskRepository.findByIdAndUserId(taskId,user.getId())
              .orElseThrow(()-> new RuntimeException("You are not allowed to delete this task"));
      taskRepository.delete(task);


    }
}
