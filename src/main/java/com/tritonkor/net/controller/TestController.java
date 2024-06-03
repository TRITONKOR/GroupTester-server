package com.tritonkor.net.controller;

import com.tritonkor.domain.dto.AnswerStoreDto;
import com.tritonkor.domain.dto.AnswerUpdateDto;
import com.tritonkor.domain.dto.QuestionStoreDto;
import com.tritonkor.domain.dto.QuestionUpdateDto;
import com.tritonkor.domain.dto.TestStoreDto;
import com.tritonkor.domain.service.impl.AnswerService;
import com.tritonkor.domain.service.impl.QuestionService;
import com.tritonkor.domain.service.impl.TestService;
import com.tritonkor.net.request.question.DeleteQuestionRequest;
import com.tritonkor.net.request.question.SaveQuestionRequest;
import com.tritonkor.net.request.test.CreateTestRequest;
import com.tritonkor.net.request.test.DeleteTestRequest;
import com.tritonkor.net.response.TestResponse;
import com.tritonkor.persistence.entity.Answer;
import com.tritonkor.persistence.entity.Question;
import com.tritonkor.persistence.entity.Tag;
import com.tritonkor.persistence.entity.Test;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing test-related endpoints.
 */
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * Retrieves all tests.
     * @return ResponseEntity with a list of TestResponse objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<TestResponse>> getAll() {
        List<TestResponse> testResponses = testService.findAll();
        if (testResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(testResponses);
    }

    /**
     * Retrieves a test by its ID.
     * @param id The ID of the test
     * @return ResponseEntity with the TestResponse object
     */
    @GetMapping("/get-by-id")
    public ResponseEntity<TestResponse> getTest(@RequestParam("id") String id) {
        Test test = testService.findById(UUID.fromString(id));

        return ResponseEntity.ok(new TestResponse(test));
    }

    /**
     * Retrieves tests associated with a specific user.
     * @param userId The ID of the user
     * @return ResponseEntity with a list of TestResponse objects
     */
    @GetMapping("/user-tests")
    public ResponseEntity<List<TestResponse>> getTestByUser(@RequestParam("userId") String userId) {
        List<Test> tests = testService.findAllByUserId(UUID.fromString(userId));

        if (tests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TestResponse> testResponses = new ArrayList<>();
        for (Test test : tests) {
            testResponses.add(new TestResponse(test));
        }

        return ResponseEntity.ok(testResponses);
    }

    /**
     * Saves or updates a question associated with a test.
     * @param request The SaveQuestionRequest object containing question details
     * @return ResponseEntity with the updated TestResponse object
     */
    @PostMapping("/question/save")
    public ResponseEntity<TestResponse> updateQuestion(
            @Valid @RequestBody SaveQuestionRequest request) {
        Test test;
        if (Objects.isNull(request.getId())) {
            QuestionStoreDto questionStoreDto = new QuestionStoreDto(request.getText(),
                    request.getImage(), request.getTestId());

            Question createdQuestion = questionService.create(questionStoreDto);


            for (Answer answer : request.getAnswers()) {
                System.out.println(answer.toString());
                AnswerStoreDto answerStoreDto = new AnswerStoreDto(
                        answer.getText(),
                        createdQuestion.getId(),
                        answer.getIsCorrect()
                );
                answerService.create(answerStoreDto);
            }

            test = testService.findById(request.getTestId());
            return ResponseEntity.ok(new TestResponse(test));
        } else {
            QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(request.getId(),
                    request.getText(), request.getImage(), request.getTestId());

            Question createdQuestion = questionService.update(questionUpdateDto);

            for (Answer answer : request.getAnswers()) {
                AnswerUpdateDto answerUpdateDto = new AnswerUpdateDto(
                        answer.getId(),
                        answer.getText(),
                        createdQuestion.getId(),
                        answer.getIsCorrect()
                );
                answerService.update(answerUpdateDto);
            }

            test = testService.findById(request.getTestId());
            return ResponseEntity.ok(new TestResponse(test));
        }
    }

    /**
     * Deletes a question associated with a test.
     * @param request The DeleteQuestionRequest object containing question ID and user ID
     * @return ResponseEntity with a boolean indicating the success of the operation
     */
    @PostMapping("/question/delete")
    public ResponseEntity<Boolean> deleteQuestion(@Valid @RequestBody DeleteQuestionRequest request) {

        Boolean result = questionService.delete(request.getQuestionId(), request.getUserId());

        return ResponseEntity.ok(result);
    }

    /**
     * Creates a new test.
     * @param request The CreateTestRequest object containing test details
     * @return ResponseEntity with a boolean indicating the success of the operation
     */
    @PostMapping("/create")
    public ResponseEntity<Boolean> createTest(@Valid @RequestBody CreateTestRequest request) {
        TestStoreDto testStoreDto = new TestStoreDto(request.getTestTitle(), request.getUserId(),
                request.getTime());
        Test test = testService.create(testStoreDto);
        List<Tag> tags = request.getTags();

        for(Tag tag : tags) {
            testService.attachTag(test.getId(), tag.getId());
        }

        if (test != null) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    /**
     * Deletes a test.
     * @param request The DeleteTestRequest object containing test ID and user ID
     * @return ResponseEntity with a boolean indicating the success of the operation
     */
    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteTest(@Valid @RequestBody DeleteTestRequest request) {

        Boolean result = testService.delete(request.getTestId(), request.getUserId());

        return ResponseEntity.ok(result);
    }
}
