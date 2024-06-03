package com.tritonkor.domain.service.impl;

import com.password4j.Password;
import com.tritonkor.domain.dto.UserStoreDto;
import com.tritonkor.domain.dto.UserUpdateDto;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.net.response.UserResponse;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.UserContext;
import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.entity.User.Role;
import com.tritonkor.persistence.entity.filter.UserFilterDto;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.UserRepository;
import jakarta.validation.Validator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * This class provides services for managing users within the application.
 */
@Service
public class UserService {

    private final UserContext userContext;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final Validator validator;
    private Path defaultAvatar;

    /**
     * Constructs a UserService with the specified dependencies.
     *
     * @param persistenceContext The persistence context providing access to user data.
     * @param fileService        The service for handling file operations.
     * @param validator          The validator for validating user data.
     * @throws IOException if an I/O error occurs while setting up the default avatar path.
     */
    public UserService(PersistenceContext persistenceContext,
            FileService fileService, Validator validator) throws IOException {
        this.userContext = persistenceContext.users;
        this.userRepository = persistenceContext.users.repository;
        this.fileService = fileService;
        this.validator = validator;
        // вказуємо дефолтний аватар
        defaultAvatar = fileService.getPathFromResource("default-avatar.png");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user entity.
     * @throws EntityNotFoundException if no user is found with the specified ID.
     */
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user entity.
     * @throws EntityNotFoundException if no user is found with the specified username.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The user entity.
     * @throws EntityNotFoundException if no user is found with the specified email.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача"));
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(new UserResponse(user));
        }

        return userResponses;
    }

    public List<User> findAll(int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            UserFilterDto userFilterDto) {
        return userRepository.findAll(
                offset,
                limit,
                sortColumn,
                ascending,
                userFilterDto);
    }

    public long count() {
        return userRepository.count();
    }

    public User create(UserStoreDto userStoreDto) throws IOException {
        var violations = validator.validate(userStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("збереженні користувача", violations);
        }

        User user = User.builder()
                .id(null)
                .username(userStoreDto.username())
                .email(userStoreDto.email())
                .password(Password.hash(userStoreDto.password()).withBcrypt().getResult())
                .avatar(fileService.getBytes(userStoreDto.avatar()))
                .birthday(userStoreDto.birthday())
                .role(Objects.nonNull(userStoreDto.role()) ? userStoreDto.role() : Role.STUDENT)
                .build();

        userContext.registerNew(user);
        userContext.commit();
        return userContext.getEntity();
    }

    public User update(UserUpdateDto userUpdateDto) throws IOException {
        var violations = validator.validate(userUpdateDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("оновленні користувача", violations);
        }

        User oldUser = findById(userUpdateDto.id());

        User user = User.builder()
                .id(userUpdateDto.id())
                .username(userUpdateDto.username())
                .email(userUpdateDto.email())
                .password(Objects.nonNull(userUpdateDto.password()) ?
                        Password.hash(userUpdateDto.password()).withBcrypt().getResult() : null)
                .avatar(!userUpdateDto.avatar().equals(defaultAvatar) ?
                        fileService.getBytes(userUpdateDto.avatar())
                        : fileService.getBytes(defaultAvatar))
                .birthday(userUpdateDto.birthday())
                .role(userUpdateDto.role())
                .build();

        userContext.registerModified(user);
        userContext.commit();
        return userContext.getEntity();
    }
}
