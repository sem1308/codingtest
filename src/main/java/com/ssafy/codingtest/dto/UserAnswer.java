package com.ssafy.codingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class UserAnswer {
    String problemNum; // 문제 번호
    String user; // 유저 아이디 or 이름
    String code; // 유저 코드
}
