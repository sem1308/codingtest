package com.ssafy.codingtest.controller;

import com.ssafy.codingtest.dto.CaseResult;
import com.ssafy.codingtest.dto.UserAnswer;
import com.ssafy.codingtest.dto.UserAnswerResponse;
import com.ssafy.codingtest.service.GradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradingController {

    private final GradingService gradingService;

    @PostMapping
    public ResponseEntity<?> grading(@RequestBody UserAnswer userAnswer) throws IOException, InterruptedException {
        System.out.println(userAnswer);
        List<CaseResult> caseResultList = gradingService.grading(userAnswer.getProblemNum(), userAnswer.getCode());

        UserAnswerResponse response = UserAnswerResponse.builder()
            .user(userAnswer.getUser())
            .problemNum(userAnswer.getProblemNum())
            .caseResultList(caseResultList)
            .build();

        response.setInfo();

        return ResponseEntity.ok().body(response);
    }
}
