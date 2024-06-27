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
    String problemNum;
    String user;
    List<CaseResult> caseResultList;
    int numAnswer;
    float answerRatio;

    public void setInfo(){
        caseResultList.forEach(caseResult -> {
            if(caseResult.getIsAnswer()) numAnswer++;
        });
        answerRatio = (float) numAnswer / caseResultList.size();
    }
}
