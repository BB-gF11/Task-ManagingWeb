package com.App.TaskManageWebb.controllers;

import com.App.TaskManageWebb.dto.JwtAuthResponse;
import com.App.TaskManageWebb.dto.LoginRequest;
import com.App.TaskManageWebb.dto.SignupRequest;
import com.App.TaskManageWebb.response.ApiResponse;
import com.App.TaskManageWebb.service.AuthService;
import com.App.TaskManageWebb.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request){
        ApiResponse response = authService.signup(request);
       return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request){
        String token = authService.login(request);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
