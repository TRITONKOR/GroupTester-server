package com.tritonkor.domain.service.impl;

import com.tritonkor.domain.dto.AnswerStoreDto;
import com.tritonkor.domain.dto.AnswerUpdateDto;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.AnswerContext;
import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.AnswerRepository;
import jakarta.validation.Validator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service class for managing operations related to answers.
 */
@Service
public class AnswerService {

    private final AnswerContext answerContext;
    private final AnswerRepository answerRepository;
    private final Validator validator;

    public AnswerService(PersistenceContext persistenceContext,
            Validator validator) {
        this.answerContext = persistenceContext.answers;
        this.answerRepository = persistenceContext.answers.repository;
        this.validator = validator;
    }

    /**
     * Retrieves an answer by its ID.
     *
     * @param id The ID of the answer to retrieve.
     * @return The retrieved answer.
     * @throws EntityNotFoundException if the answer with the given ID does not exist.
     */
    public Answer findById(UUID id) {
        return answerContext.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти відповідь"));
    }

    /**
     * Retrieves all answers.
     *
     * @return A list of all answers.
     */
    public List<Answer> findAll() {
        return answerRepository.findAll();
    }

    /**
     * Retrieves all answers associated with a question.
     *
     * @param questionId The ID of the question.
     * @return A list of answers associated with the specified question.
     */
    public List<Answer> findAllByQuestionId(UUID questionId) {
        return answerContext.repository.findAllByQuestionId(questionId);
    }

    /**
     * Counts the total number of answers.
     *
     * @return The total number of answers.
     */
    public long count() {
        return answerRepository.count();
    }

    /**
     * Creates a new answer.
     *
     * @param answerStoreDto The data for storing the new answer.
     * @return The created answer.
     * @throws ValidationException if the provided data is not valid.
     */
    public Answer create(AnswerStoreDto answerStoreDto) {
        System.out.println(answerStoreDto);
        var violations = validator.validate(answerStoreDto);
        if (!violations.isEmpty()) {
            System.out.println(violations);
            throw ValidationException.create("збереженні відповіді", violations);
        }

        Answer answer = Answer.builder()
                .id(null)
                .questionId(answerStoreDto.questionId())
                .question(null)
                .text(answerStoreDto.text())
                .isCorrect(answerStoreDto.isCorrect())
                .build();

        answerContext.registerNew(answer);
        answerContext.commit();
        return answerContext.getEntity();
    }

    /**
     * Updates an existing answer.
     *
     * @param answerUpdateDto The data for updating the answer.
     * @return The updated answer.
     * @throws ValidationException if the provided data is not valid.
     */
    public Answer update(AnswerUpdateDto answerUpdateDto) {
        var violations = validator.validate(answerUpdateDto);
        if (!violations.isEmpty()) {
            System.out.println(violations);
            throw ValidationException.create("оновленні відповіді", violations);
        }

        Answer answer = Answer.builder()
                .id(answerUpdateDto.id())
                .questionId(answerUpdateDto.questionId())
                .question(null).text(answerUpdateDto.text())
                .isCorrect(answerUpdateDto.isCorrect())
                .build();

        answerContext.registerModified(answer);
        answerContext.commit();
        return answerContext.getEntity();
    }

    /**
     * Deletes an answer by its ID.
     *
     * @param id The ID of the answer to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean delete(UUID id) {
        Answer answer = findById(id);

        return answerContext.repository.delete(answer.getId());
    }
}
