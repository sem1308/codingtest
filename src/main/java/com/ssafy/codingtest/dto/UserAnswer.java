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
    String problemNum;
    String user;
    String code;
}
