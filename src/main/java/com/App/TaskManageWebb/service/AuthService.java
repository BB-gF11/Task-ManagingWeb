package com.App.TaskManageWebb.service;

import com.App.TaskManageWebb.dto.LoginRequest;
import com.App.TaskManageWebb.dto.SignupRequest;
import com.App.TaskManageWebb.response.ApiResponse;

public interface AuthService {
    public ApiResponse signup(SignupRequest req);

    String login(LoginRequest request);
}
