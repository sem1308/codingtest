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

    /**
     * userAnswer :
     * {
     *    "problemNum" : "2609",
     *    "user" : "hshhan0221",
     *    "code" : "import ... "
     * }
     * 위 데이터를 받아 채점한 후 결과 반환
     * 결과 형식은 다음과 같음
     * {
     *    "problemNum" : "2609",
     *    "user" : "hshhan0221",
     *    "caseResultList" : [
     *       {
     *           "problemNum" : "2609",
     *           "caseNum" : 0,
     *           "isAnswer" : true,
     *       },
     *       {
     *           "problemNum" : "2609",
     *           "caseNum" : 1,
     *           "isAnswer" : false,
     *       },
     *    ],
     *    "numAnswer" : 10,
     *    "answerRatio" 1.0
     * }
     */
    @PostMapping
    public ResponseEntity<?> grading(@RequestBody UserAnswer userAnswer) throws IOException, InterruptedException {
        // 유저 데이터를 통해 채점
        List<CaseResult> caseResultList = gradingService.grading(userAnswer.getProblemNum(), userAnswer.getCode());

        // 채점 결과 저장
        UserAnswerResponse response = UserAnswerResponse.builder()
                .user(userAnswer.getUser())
                .problemNum(userAnswer.getProblemNum())
                .caseResultList(caseResultList)
                .build();

        // 정답수, 정답률 등록
        response.setInfo();

        return ResponseEntity.ok().body(response);
    }
}
