package com.ssafy.codingtest.problem.baekjoon;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BaekjoonInputOutputGenerator {
    public final static String baseDir = "src/main/java";
    public final static String inputDir = "input";
    public final static String outputDir = "output";
    public final static String packagePath = BaekjoonInputOutputGenerator.class.getPackage().getName().replace('.', '/');

    Path problemPath;
    Path inputDirPath;
    Path outputDirPath;

    Path inputFilePath;
    Path outputFilePath;

    /*
        아래와 같은 폴더 생성 후 데이터 생성
        src/main/java/com/ssafy/codingtest/problem/baekjoon/{문제번호}/input
        src/main/java/com/ssafy/codingtest/problem/baekjoon/{문제번호}/output
    */

    public BaekjoonInputOutputGenerator(String problemNum) {
        this.problemPath = getProblemFolderPath(problemNum);
        this.inputDirPath = problemPath.resolve(inputDir);
        this.outputDirPath = problemPath.resolve(outputDir);
    }

    /*
     * inputFileName : 입력 데이터 파일 이름
     * outputFileName : 출력 데이터 파일 이름
     *
     * 입력 데이터 생성 후 출력 데이터 생성
     */
    public void makeData(String inputFileName, String outputFileName, Supplier<String> inputFunc, Function<List<String>, String> outputFunc) {
        this.inputFilePath = inputDirPath.resolve(inputFileName);
        this.outputFilePath = outputDirPath.resolve(outputFileName);

        makeInput(inputFunc);
        makeOutput(outputFunc);
    }

    public static Path getProblemFolderPath(String problemNum){
        return Paths.get(baseDir, packagePath, getProblemFolderName(problemNum));
    }

    public static String getProblemFolderName(String problemNum){
        return "p"+problemNum;
    }

    /*
     * func : 입력 데이터 생성 함수
     *
     * 입력 데이터 생성 후 파일 저장
     */
    public void makeInput(Supplier<String> func) {
        String result = func.get();

        // input.txt 파일에 저장
        save(inputFilePath, result);
    }

    /*
     * func : 출력 데이터 생성 함수
     *        - 입력 데이터 파일을 읽은 String 배열을 매개변수로 받음
     *
     * 출력 데이터 생성 후 파일 저장
     */
    public void makeOutput(Function<List<String>,String> func) {
        // input.txt 파일에서 데이터를 읽고 출력 데이터를 계산하여 output.txt에 저장
        try {
            List<String> lines = Files.readAllLines(inputFilePath, StandardCharsets.UTF_8);
            String result = func.apply(lines);
            // output.txt 파일에 결과 저장
            save(outputFilePath, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * filePath : 파일 저장 경로
     * result : 파일에 넣을 데이터
     *
     * 파일 생성 후 데이터 삽입
     */
    private void save(Path filePath, String result) {
        try {
            // Ensure the directory exists
            Files.createDirectories(filePath.getParent());
            // Write data to the file
            Files.writeString(filePath, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
