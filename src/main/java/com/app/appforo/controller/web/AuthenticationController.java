package com.app.appforo.controller.web;

import com.app.appforo.dto.security.LoginRequest;
import com.app.appforo.dto.security.LoginResponse;
import com.app.appforo.services.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PreAuthorize("permitAll")
    @PostMapping("/logout")
    public void logaut() throws Exception {
        authenticationService.logaut();
    }
}
