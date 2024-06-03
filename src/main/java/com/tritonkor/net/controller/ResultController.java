package com.tritonkor.net.controller;

import com.tritonkor.domain.dto.ResultStoreDto;
import com.tritonkor.domain.service.impl.GroupService;
import com.tritonkor.domain.service.impl.ResultService;
import com.tritonkor.domain.service.impl.UserService;
import com.tritonkor.net.request.result.SaveResultRequest;
import com.tritonkor.net.response.ResultResponse;
import com.tritonkor.persistence.entity.Group;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.entity.User;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
 * The {@code ResultController} class handles result-related HTTP requests.
 */
@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final GroupService groupService;
    private final UserService userService;

    /**
     * Retrieves results for a student.
     *
     * @param userId the ID of the student
     * @return a list of result responses
     */
    @GetMapping("/student-results")
    public ResponseEntity<List<ResultResponse>> getResultsByStudent(
            @RequestParam("userId") String userId) {
        List<Result> results = resultService.findAllByUserId(UUID.fromString(userId));

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Result result : results) {
            resultResponses.add(new ResultResponse(result));
        }

        return ResponseEntity.ok(resultResponses);
    }

    /**
     * Retrieves results for a teacher.
     *
     * @param userId the ID of the teacher
     * @return a list of result responses
     */
    @GetMapping("/teacher-results")
    public ResponseEntity<List<ResultResponse>> getResultsByTeacher(
            @RequestParam("userId") String userId) {
        List<Result> results = resultService.findAllByTeacherId(UUID.fromString(userId));

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Result result : results) {
            resultResponses.add(new ResultResponse(result));
        }

        return ResponseEntity.ok(resultResponses);
    }

    /**
     * Saves a result.
     *
     * @param request the request to save a result
     * @return a result response
     */
    @PostMapping("/save")
    public ResponseEntity<ResultResponse> saveResult(
            @Valid @RequestBody SaveResultRequest request) {
        ResultStoreDto resultStoreDto = new ResultStoreDto(request.getTestId(), request.getUserId(),
                request.getGroupCode(), request.getMark());

        Result result = resultService.create(resultStoreDto);

        Group group = groupService.findByCode(request.getGroupCode());
        User user = userService.findById(request.getUserId());
        if (group.getResults().containsKey(user)) {
            group.getResults().put(user, result.getMark());
        }

        return ResponseEntity.ok(new ResultResponse(result));
    }
}
