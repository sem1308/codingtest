package com.ssafy.codingtest.problem.baekjoon.generator;

import com.ssafy.codingtest.problem.baekjoon.BaekjoonInputOutputGenerator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaekjoonGenerator {
    // 데이터 생성기
    protected BaekjoonInputOutputGenerator generator;

    // 기본 데이터
    protected String inputFileName;
    protected String outputFileName;

    protected Supplier<String> inputFunc;
    protected Function<List<String>, String> outputFunc;

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
