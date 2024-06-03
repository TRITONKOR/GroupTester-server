package com.tritonkor.net.controller;

import com.tritonkor.net.response.UserResponse;
import com.tritonkor.domain.service.impl.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user-related endpoints.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users.
     * @return ResponseEntity with a list of UserResponse objects
     */
    @RequestMapping("/all")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> userDataResponse = userService.findAll();
        if(userDataResponse.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(userDataResponse);
    }
}
