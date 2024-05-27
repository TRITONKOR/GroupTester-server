package com.tritonkor.domain.service.impl;

import com.tritonkor.domain.dto.TestStoreDto;
import com.tritonkor.domain.dto.TestUpdateDto;
import com.tritonkor.domain.exception.AccessDeniedException;
import com.tritonkor.domain.exception.ValidationException;
import com.tritonkor.net.response.TestResponse;
import com.tritonkor.net.response.UserResponse;
import com.tritonkor.persistence.context.factory.PersistenceContext;
import com.tritonkor.persistence.context.impl.TestContext;
import com.tritonkor.persistence.entity.Test;
import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.entity.filter.TestFilterDto;
import com.tritonkor.persistence.exception.EntityNotFoundException;
import com.tritonkor.persistence.repository.contract.TestRepository;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Service class providing operations related to tests.
 */
@Service
public class TestService {

    private final TestContext testContext;
    private final TestRepository testRepository;
    private final AuthorizeService authorizeService;
    private final Validator validator;

    public TestService(PersistenceContext persistenceContext, AuthorizeService authorizeService,
            Validator validator) {
        this.testContext = persistenceContext.tests;
        this.testRepository = persistenceContext.tests.repository;
        this.authorizeService = authorizeService;
        this.validator = validator;
    }

    /**
     * Finds a test by its ID.
     *
     * @param id the ID of the test
     * @return the test found
     * @throws EntityNotFoundException if the test with the specified ID is not found
     */
    public Test findById(UUID id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти тест"));
    }

    /**
     * Finds a test by its title.
     *
     * @param title the title of the test
     * @return the test found
     * @throws EntityNotFoundException if the test with the specified title is not found
     */
    public Test findByTitle(String title) {
        return testContext.repository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Не вдалось знайти тест"));
    }

    /**
     * Retrieves all tests.
     *
     * @return a set of all tests
     */
    public List<TestResponse> findAll() {
        List<Test> tests = testRepository.findAll();
        List<TestResponse> testResponses = new ArrayList<>();
        for (Test test : tests) {
            testResponses.add(new TestResponse(test));
        }

        return testResponses;
    }

    /**
     * Retrieves all tests based on provided parameters.
     *
     * @param offset        the offset for pagination
     * @param limit         the limit for pagination
     * @param sortColumn    the column to sort by
     * @param ascending     the sort order (true for ascending, false for descending)
     * @param testFilterDto the filter criteria
     * @return a set of filtered tests
     */
    public List<Test> findAll(int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            TestFilterDto testFilterDto) {
        return testRepository.findAll(
                offset,
                limit,
                sortColumn,
                ascending,
                testFilterDto);
    }

    /**
     * Retrieves all tests owned by a specific user.
     *
     * @param userId the ID of the user
     * @return a set of tests owned by the user
     */
    public List<Test> findAllByUserId(UUID userId) {
        return testRepository.findAllByUserId(userId);
    }

    /**
     * Retrieves all tests owned by a specific user based on provided parameters.
     *
     * @param userId        the ID of the user
     * @param offset        the offset for pagination
     * @param limit         the limit for pagination
     * @param sortColumn    the column to sort by
     * @param ascending     the sort order (true for ascending, false for descending)
     * @param testFilterDto the filter criteria
     * @return a set of filtered tests owned by the user
     */
    public List<Test> findAllByUserId(UUID userId,
            int offset,
            int limit,
            String sortColumn,
            boolean ascending,
            TestFilterDto testFilterDto) {
        return testRepository.findAllByUserId(
                userId,
                offset,
                limit,
                sortColumn,
                ascending,
                testFilterDto);
    }

    /**
     * Counts the total number of tests.
     *
     * @return the total number of tests
     */
    public long count() {
        return testRepository.count();
    }

    /**
     * Creates a new test.
     *
     * @param testStoreDto the DTO containing information for creating the test
     * @return the created test
     * @throws ValidationException if the validation of the input fails
     */
    public Test create(TestStoreDto testStoreDto) {
        var violations = validator.validate(testStoreDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("збереженні тесту", violations);
        }

        Test comment = Test.builder()
                .id(null)
                .title(testStoreDto.title())
                .ownerId(testStoreDto.ownerId())
                .owner(null)
                .questions(null)
                .tags(null)
                .createdAt(null)
                .build();

        testContext.registerNew(comment);
        testContext.commit();
        return testContext.getEntity();
    }

    /**
     * Updates an existing test.
     *
     * @param testUpdateDto the DTO containing information for updating the test
     * @return the updated test
     * @throws EntityNotFoundException if the test to be updated is not found
     * @throws ValidationException     if the validation of the input fails
     */
    public Test update(TestUpdateDto testUpdateDto) {
        var violations = validator.validate(testUpdateDto);
        if (!violations.isEmpty()) {
            throw ValidationException.create("оновленні тесту", violations);
        }

        Test oldTest = findById(testUpdateDto.id());

        Test test = Test.builder()
                .id(testUpdateDto.id())
                .title(testUpdateDto.title())
                .ownerId(testUpdateDto.ownerId())
                .owner(null)
                .questions(null)
                .tags(null)
                .createdAt(null)
                .build();

        testContext.registerModified(test);
        testContext.commit();
        return testContext.getEntity();
    }


    public boolean delete(UUID id, UUID userId) {
        Test test = findById(id);
        if (!authorizeService.canDelete(test, userId)) {
            throw AccessDeniedException.notAuthorUser("видаляти тести");
        }

        return testContext.repository.delete(test.getId());
    }
}
