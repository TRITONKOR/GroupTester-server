package com.tritonkor.net.controller;

import com.tritonkor.domain.service.impl.AuthenticationService;
import com.tritonkor.net.request.AuthentiactionRequest;
import com.tritonkor.net.request.UnauthorizeRequest;
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

/**
 * The {@code AuthenticationController} class handles authentication-related HTTP requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return an authentication response
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    /**
     * Authenticates a user.
     *
     * @param request the authentication request
     * @return a user response
     */
    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse> authenticate(@Valid @RequestBody
            AuthentiactionRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Logs out a user.
     *
     * @param request the logout request
     * @return a boolean indicating whether the operation was successful
     */
    @PostMapping("/unauthorize")
    public ResponseEntity<Boolean> leave(@Valid @RequestBody UnauthorizeRequest request) {
        return ResponseEntity.ok(authenticationService.unauthorize(request));
    }
}
