package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.entity.proxy.contract.UserProxy;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.UserRepository;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code UserProxyImpl} class serves as a proxy implementation for retrieving users by their ID.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code UserRepository} bean.
 */
@Component
public class UserProxyImpl implements UserProxy {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a {@code UserProxyImpl} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code UserRepository} bean
     */
    public UserProxyImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a user by its unique identifier.
     *
     * @param entityId the unique identifier of the user
     * @return the user associated with the specified ID
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    @Override
    public User get(UUID entityId) {
        UserProxy proxy = (userId) -> applicationContext.getBean(UserRepository.class)
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти користувача за id"));

        return proxy.get(entityId);
    }
}
