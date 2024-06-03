package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Question;
import com.tritonkor.persistence.entity.proxy.contract.Questions;
import com.tritonkor.persistence.repository.contract.QuestionRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code QuestionsProxy} class serves as a proxy implementation for retrieving questions associated with a test.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code QuestionRepository} bean.
 */
@Component
public class QuestionsProxy implements Questions {
    private final ApplicationContext applicationContext;

    /**
     * Constructs a {@code QuestionsProxy} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code QuestionRepository} bean
     */
    public QuestionsProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a list of questions associated with the specified test ID.
     *
     * @param testId the unique identifier of the test
     * @return a list of questions related to the specified test ID
     */
    @Override
    public List<Question> get(UUID testId) {
        QuestionRepository questionRepository = applicationContext.getBean(QuestionRepository.class);
        Questions questions = tId -> Collections.unmodifiableList(questionRepository.findAllByTestId(tId));
        return questions.get(testId);
    }
}
