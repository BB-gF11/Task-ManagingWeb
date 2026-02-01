package com.App.TaskManageWebb.service.impl;

import com.App.TaskManageWebb.dto.LoginRequest;
import com.App.TaskManageWebb.dto.SignupRequest;
import com.App.TaskManageWebb.exception.InvalidPasswordException;
import com.App.TaskManageWebb.exception.UserNotFoundException;
import com.App.TaskManageWebb.model.User;
import com.App.TaskManageWebb.repository.UserRepository;
import com.App.TaskManageWebb.response.ApiResponse;
import com.App.TaskManageWebb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public ApiResponse signup(SignupRequest req) {
        String email = req.getEmail();
        if (userRepository.existsByEmail(email)) {
            return new ApiResponse("email already exists"); // make a new exception
        }
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        userRepository.save(user);
        return new ApiResponse("sign up completed");

    }

    @Override
    public ApiResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new UserNotFoundException("user doesn't exist");
        }
        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("wrong password");
        }
        return new ApiResponse("welcome"+ user.getName());

    }
}