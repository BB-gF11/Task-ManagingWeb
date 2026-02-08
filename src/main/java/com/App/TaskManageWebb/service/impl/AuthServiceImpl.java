package com.App.TaskManageWebb.service.impl;

import com.App.TaskManageWebb.config.JwtTokenProvider;
import com.App.TaskManageWebb.dto.LoginRequest;
import com.App.TaskManageWebb.dto.SignupRequest;
import com.App.TaskManageWebb.model.User;
import com.App.TaskManageWebb.repository.UserRepository;
import com.App.TaskManageWebb.response.ApiResponse;
import com.App.TaskManageWebb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
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
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;

    }
}