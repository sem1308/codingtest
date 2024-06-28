package com.ssafy.codingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class UserAnswerResponse {
    String problemNum; // 문제 번호
    String user; // 유저 아이디 or 이름
    List<CaseResult> caseResultList; // 테스트케이스 정답 결과 목록
    int numAnswer; // 정답수
    float answerRatio; // 정답률

    // 정답수, 정답률 등록
    public void setInfo(){
        numAnswer = 0;
        caseResultList.forEach(caseResult -> {
            if(caseResult.getIsAnswer()) numAnswer++;
        });
        answerRatio = (float) numAnswer / caseResultList.size();
    }
}
