package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Question;
import com.tritonkor.persistence.entity.proxy.contract.QuestionProxy;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.QuestionRepository;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code QuestionProxyImpl} class serves as a proxy implementation for retrieving questions by their ID.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code QuestionRepository} bean.
 */
@Component
public class QuestionProxyImpl implements QuestionProxy {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a {@code QuestionProxyImpl} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code QuestionRepository} bean
     */
    public QuestionProxyImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a question by its unique identifier.
     *
     * @param entityId the unique identifier of the question
     * @return the question associated with the specified ID
     * @throws EntityNotFoundException if the question with the specified ID is not found
     */
    @Override
    public Question get(UUID entityId) {
        QuestionProxy proxy = (questionId) -> applicationContext.getBean(QuestionRepository.class)
                .findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти питання за id"));

        return proxy.get(entityId);
    }
}
