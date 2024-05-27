package com.tritonkor.net.controller;

import com.tritonkor.domain.service.impl.ResultService;
import com.tritonkor.net.response.ResultResponse;
import com.tritonkor.persistence.entity.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @GetMapping("/user-results")
    public ResponseEntity<List<ResultResponse>> getResultsByUser(@RequestParam("userId") String userId) {
        List<Result> results = resultService.findAllByUserId(UUID.fromString(userId));

        if(results.isEmpty()) return ResponseEntity.noContent().build();

        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Result result : results) {
            resultResponses.add(new ResultResponse(result));
        }

        return ResponseEntity.ok(resultResponses);
    }
}
