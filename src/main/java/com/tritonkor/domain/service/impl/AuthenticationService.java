package com.tritonkor.domain.service.impl;

import com.password4j.Password;
import com.tritonkor.domain.exception.AuthenticationException;
import com.tritonkor.domain.exception.RegisterUserException;
import com.tritonkor.domain.exception.UserAlreadyAuthenticatedException;
import com.tritonkor.net.request.AuthentiactionRequest;
import com.tritonkor.net.request.RegisterRequest;
import com.tritonkor.net.request.UnauthorizeRequest;
import com.tritonkor.net.response.AuthenticationResponse;
import com.tritonkor.net.response.UserResponse;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.UserContext;
import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.exception.EntityUpdateException;
import com.tritonkor.persistence.repository.contract.UserRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user authentication.
 */
@Service
public class AuthenticationService {
    private final UserContext userContext;
    private final UserRepository userRepository;

    private List<User> authorizedUsers = new ArrayList<>();

    public AuthenticationService(PersistenceContext persistenceContext) {
        this.userContext = persistenceContext.users;
        this.userRepository = userContext.repository;
    }

    /**
     * Registers a new user.
     *
     * @param request The request containing user registration information.
     * @return An authentication response indicating whether the registration was successful.
     * @throws RegisterUserException if an error occurs during user registration.
     */
    public AuthenticationResponse register(@Valid RegisterRequest request) {
        User user = User.builder()
                .id(null)
                .username(request.getUsername())
                .email(request.getEmail())
                .password(Password.hash(request.getPassword()).withBcrypt().getResult())
                .avatar(request.getAvatar())
                .birthday(request.getBirthday())
                .role(request.getRole())
                .build();
        Boolean result = false;
        User savedUser = null;
        try {
            userContext.registerNew(user);
            userContext.commit();
            savedUser = userContext.getEntity();
            result = true;
        } catch (EntityUpdateException e) {
            System.out.println(e.getMessage());
            throw new RegisterUserException();
        }


        return AuthenticationResponse.builder()
                .id(savedUser.getId().toString())
                .result(result)
                .build();
    }

    /**
     * Authenticates a user.
     *
     * @param request The request containing user authentication information.
     * @return The user response if authentication is successful, otherwise null.
     */
    public UserResponse authenticate(@Valid AuthentiactionRequest request) {
        try {
            User foundedUser = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(AuthenticationException::new);

            if (authorizedUsers.contains(foundedUser)) {
                return null;
            }
            if (!Password.check(request.getPassword(), foundedUser.getPassword()).withBcrypt()) {
                return null;
            }

            authorizedUsers.add(foundedUser);

            return new UserResponse(foundedUser);
        } catch (AuthenticationException e) {
            return null;
        }
    }

    /**
     * Unauthorizes a user.
     *
     * @param request The request containing user unauthorization information.
     * @return True if the unauthorization was successful, otherwise false.
     */
    public Boolean unauthorize(@Valid UnauthorizeRequest request) {
        User foundedUser = userRepository.findById(request.getUserId())
                .orElseThrow(AuthenticationException::new);
        if (authorizedUsers.contains(foundedUser)) {
            authorizedUsers.remove(foundedUser);
            return true;
        }
        return false;
    }
}
