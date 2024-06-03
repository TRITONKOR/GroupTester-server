package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.proxy.contract.Answers;
import com.tritonkor.persistence.repository.contract.AnswerRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The {@code AnswersProxy} class serves as a proxy implementation for retrieving answers related to a question.
 * It utilizes the Spring {@code ApplicationContext} to get the {@code AnswerRepository} bean.
 */
@Component
public class AnswersProxy implements Answers {
    private final ApplicationContext applicationContext;

    /**
     * Constructs an {@code AnswersProxy} with the given application context.
     *
     * @param applicationContext the application context to use for retrieving the {@code AnswerRepository} bean
     */
    public AnswersProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Retrieves a list of answers for the specified question ID.
     *
     * @param questionId the unique identifier of the question
     * @return a list of answers related to the specified question ID
     */
    @Override
    public List<Answer> get(UUID questionId) {
        AnswerRepository answerRepository = applicationContext.getBean(AnswerRepository.class);
        Answers answers = qId -> Collections.unmodifiableList(answerRepository.findAllByQuestionId(qId));
        return answers.get(questionId);
    }
}
