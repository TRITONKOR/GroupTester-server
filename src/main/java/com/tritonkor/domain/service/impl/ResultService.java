package com.tritonkor.domain.service.impl;

import com.tritonkor.domain.dto.ResultStoreDto;
import com.tritonkor.domain.dto.ResultUpdateDto;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.ResultContext;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.ResultRepository;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

    private final ResultContext resultContext;
    private final ResultRepository resultRepository;
    //private final AuthorizeService authorizeService;
    private final Validator validator;

    public ResultService(PersistenceContext persistenceContext, AuthorizeService authorizeService,
            Validator validator) {
        this.resultContext = persistenceContext.results;
        this.resultRepository = persistenceContext.results.repository;
        //this.authorizeService = authorizeService;
        this.validator = validator;
    }

    public Result findById(UUID id) {
        return resultContext.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти питання"));
    }

    public List<Result> findAll() {
        return resultRepository.findAll();
    }


    public List<Result> findAllByTestId(UUID testId) {
        return resultContext.repository.findAllByTestId(testId);
    }

    public List<Result> findAllByUserId(UUID userId) {
        return resultContext.repository.findAllByOwnerId(userId);
    }

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
                .mark(resultStoreDto.mark())
                .createdAt(null).build();

        resultContext.registerNew(result);
        resultContext.commit();
        return resultContext.getEntity();
    }

    public Result update(ResultUpdateDto resultUpdateDto) {
        var violations = validator.validate(resultUpdateDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("оновленні результата", violations);
        }

        Result result = Result.builder()
                .id(resultUpdateDto.id())
                .ownerId(resultUpdateDto.ownerId())
                .owner(null)
                .testId(resultUpdateDto.testId())
                .test(null)
                .mark(resultUpdateDto.mark())
                .createdAt(null).build();

        resultContext.registerModified(result);
        resultContext.commit();
        return resultContext.getEntity();
    }

    public boolean delete(UUID id) {
        Result result = findById(id);

        return resultContext.repository.delete(result.getId());
    }
}
