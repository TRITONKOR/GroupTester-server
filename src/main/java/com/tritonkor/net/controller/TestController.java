package com.tritonkor.net.controller;

import com.tritonkor.domain.dto.AnswerStoreDto;
import com.tritonkor.domain.dto.AnswerUpdateDto;
import com.tritonkor.domain.dto.QuestionStoreDto;
import com.tritonkor.domain.dto.QuestionUpdateDto;
import com.tritonkor.domain.service.impl.AnswerService;
import com.tritonkor.domain.service.impl.QuestionService;
import com.tritonkor.domain.service.impl.TestService;
import com.tritonkor.net.request.queston.SaveQuestionRequest;
import com.tritonkor.net.response.GroupResponse;
import com.tritonkor.net.response.TestResponse;
import com.tritonkor.persistence.entity.Answer;
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

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/all")
    public ResponseEntity<List<TestResponse>> getAll() {
        List<TestResponse> testResponses = testService.findAll();
        if (testResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(testResponses);
    }

    @GetMapping()
    public ResponseEntity<TestResponse> getTest(@RequestParam("id") String id) {
        Test test = testService.findById(UUID.fromString(id));

        return ResponseEntity.ok(new TestResponse(test));
    }

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

    @PostMapping("/update-question")
    public ResponseEntity<TestResponse> updateQuestion(
            @Valid @RequestBody SaveQuestionRequest request) {
        if (Objects.isNull(request.getId())) {
            QuestionStoreDto questionStoreDto = new QuestionStoreDto(request.getText(),
                    request.getTestId());
            questionService.create(questionStoreDto);

            for (Answer answer : request.getAnswers()) {
                AnswerStoreDto answerStoreDto = new AnswerStoreDto(
                        answer.getText(),
                        answer.getQuestionId(),
                        // Assuming getId() returns the ID of the newly created question
                        answer.getCorrect()
                );
                answerService.create(answerStoreDto);
            }
        } else {
            QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(request.getId(),
                    request.getText(), request.getTestId());
            questionService.update(questionUpdateDto);

            for (Answer answer : request.getAnswers()) {
                AnswerUpdateDto answerUpdateDto = new AnswerUpdateDto(answer.getId(),
                        answer.getText(),
                        answer.getQuestionId(),
                        answer.getCorrect()
                );
                answerService.update(answerUpdateDto);
            }
        }
        return null;
    }
}
