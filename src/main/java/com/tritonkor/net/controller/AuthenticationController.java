package com.tritonkor.net.controller;

import com.tritonkor.domain.service.impl.AuthenticationService;
import com.tritonkor.net.request.AuthentiactionRequest;
import com.tritonkor.net.request.RegisterRequest;
import com.tritonkor.net.response.AuthenticationResponse;
import com.tritonkor.net.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse> authenticate(@Valid @RequestBody
            AuthentiactionRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
