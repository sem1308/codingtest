package com.ssafy.codingtest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CaseResult {
    String problemNum; // 문제 번호
    int caseNum; // 테스트 케이스 번호
    Boolean isAnswer; // 정답 여부
}