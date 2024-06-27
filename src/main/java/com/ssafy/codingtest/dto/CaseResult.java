package com.ssafy.codingtest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CaseResult {
    String problemNum;
    int caseNum;
    Boolean isAnswer;
}