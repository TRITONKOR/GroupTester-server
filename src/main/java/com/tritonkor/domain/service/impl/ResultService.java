package com.tritonkor.domain.service.impl;

import com.tritonkor.domain.dto.ResultStoreDto;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.ResultContext;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.ResultRepository;
import jakarta.validation.Validator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service class for managing results.
 */
@Service
public class ResultService {

    private final ResultContext resultContext;
    private final ResultRepository resultRepository;
    private final Validator validator;

    /**
     * Constructs a new ResultService with the given persistence context and validator.
     *
     * @param persistenceContext The persistence context.
     * @param validator          The validator.
     */
    public ResultService(PersistenceContext persistenceContext,
            Validator validator) {
        this.resultContext = persistenceContext.results;
        this.resultRepository = persistenceContext.results.repository;
        this.validator = validator;
    }

    /**
     * Finds a result by its ID.
     *
     * @param id The ID of the result.
     * @return The result found.
     * @throws EntityNotFoundException if the result with the given ID is not found.
     */
    public Result findById(UUID id) {
        return resultContext.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти питання"));
    }

    /**
     * Finds all results.
     *
     * @return The list of all results.
     */
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    /**
     * Finds all results by test ID.
     *
     * @param testId The ID of the test.
     * @return The list of results belonging to the specified test.
     */
    public List<Result> findAllByTestId(UUID testId) {
        return resultContext.repository.findAllByTestId(testId);
    }

    /**
     * Finds all results by group code.
     *
     * @param groupCode The code of the group.
     * @return The list of results belonging to the specified group.
     */
    public List<Result> findAllByGroupCode(String groupCode) {
        return resultContext.repository.findAllByGroupCode(groupCode);
    }

    /**
     * Finds all results by user ID.
     *
     * @param userId The ID of the user.
     * @return The list of results belonging to the specified user.
     */
    public List<Result> findAllByUserId(UUID userId) {
        return resultContext.repository.findAllByOwnerId(userId);
    }

    /**
     * Finds all results by teacher ID.
     *
     * @param teacherId The ID of the teacher.
     * @return The list of results belonging to the specified teacher.
     */
    public List<Result> findAllByTeacherId(UUID teacherId) {
        return resultContext.repository.findAllByTeacherId(teacherId);
    }

    /**
     * Counts the number of results.
     *
     * @return The number of results.
     */
    public long count() {
        return resultRepository.count();
    }

    public Result create(ResultStoreDto resultStoreDto) {
        var violations = validator.validate(resultStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("збереженні результата", violations);
        }

        Result result = Result.builder()
                .id(null)
                .ownerId(resultStoreDto.ownerId())
                .owner(null)
                .testId(resultStoreDto.testId())
                .test(null)
                .groupCode(resultStoreDto.groupCode())
                .mark(resultStoreDto.mark())
                .createdAt(null).build();

        resultContext.registerNew(result);
        resultContext.commit();
        return resultContext.getEntity();
    }

    public boolean delete(UUID id) {
        Result result = findById(id);

        return resultContext.repository.delete(result.getId());
    }
}
