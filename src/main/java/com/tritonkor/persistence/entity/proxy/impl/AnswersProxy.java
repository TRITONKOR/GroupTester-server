package com.tritonkor.persistence.entity.proxy.impl;

import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.proxy.contract.Answers;
import com.tritonkor.persistence.repository.contract.AnswerRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AnswersProxy implements Answers {
    private final ApplicationContext applicationContext;

    public AnswersProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public List<Answer> get(UUID questionId) {
        AnswerRepository answerRepository = applicationContext.getBean(AnswerRepository.class);
        Answers answers = qId -> Collections.unmodifiableList(answerRepository.findAllByQuestionId(qId));
        return answers.get(questionId);
    }
}
