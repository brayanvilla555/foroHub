package com.app.appforo.services.security;

import com.app.appforo.dto.security.LoginRequest;
import com.app.appforo.dto.security.LoginResponse;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest loginRequest);
    void logaut() throws Exception;
}
