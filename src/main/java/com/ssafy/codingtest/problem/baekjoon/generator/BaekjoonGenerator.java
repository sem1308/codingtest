package com.ssafy.codingtest.problem.baekjoon.generator;

import com.ssafy.codingtest.problem.baekjoon.BaekjoonInputOutputGenerator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaekjoonGenerator {
    // 입.출력 데이터 생성기
    protected BaekjoonInputOutputGenerator generator;

    // 기본 데이터
    protected String inputFileName; // 입력 데이터 파일 이름
    protected String outputFileName; // 출력 데이터 파일 이름


    protected Supplier<String> inputFunc; // 입력 데이터 생성 함수
    protected Function<List<String>, String> outputFunc; // 출력 데이터 생성 함수

    // 테스트 케이스 개수
    protected int size = 10;

    public BaekjoonGenerator(String problemNum) {
        generator = new BaekjoonInputOutputGenerator(problemNum);
        inputFileName = "input_" + problemNum + "_";
        outputFileName = "output_" + problemNum + "_";
        inputFunc = setInputFunc();
        outputFunc = setOutputFunc();
    }

    public abstract Supplier<String> setInputFunc();
    public abstract Function<List<String>, String> setOutputFunc();

    public void generate() {
        for (int i = 1; i <= size; i++) {
            generator.makeData(inputFileName+i,outputFileName+i, inputFunc, outputFunc);
        }
    }
}