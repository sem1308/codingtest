package com.ssafy.codingtest.service;

import com.ssafy.codingtest.JavaExecuter;
import com.ssafy.codingtest.dto.CaseResult;
import com.ssafy.codingtest.problem.baekjoon.BaekjoonInputOutputGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class GradingService {
    public List<CaseResult> grading(String problemNum, String code) throws IOException, InterruptedException {
        // 컴파일, 실행을 위한 클래스
        JavaExecuter executer = new JavaExecuter();
        executer.saveAndCompile(code); // 코드 파일 저장 및 컴파일

        // 테스트케이스 폴더 경로
        Path folderPath = BaekjoonInputOutputGenerator.getProblemFolderPath(problemNum);

        // 입력 테스트케이스 폴더 경로
        Path inputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.inputDir);

        // 출력 테스트케이스 폴더 경로
        Path outputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.outputDir);

        // Files.walk()를 사용하여 폴더 내 모든 파일 경로를 가져옴 - 모든 테스트케이스 가져옴
        List<Path> inputPaths = Files.walk(inputDirPath).filter(Files::isRegularFile).toList();
        List<Path> outputPaths = Files.walk(outputDirPath).filter(Files::isRegularFile).toList();

        // 해당 문제의 몇번 테스트 케이스가 맞았는지 저장하는 변수
        List<CaseResult> isAnswerList = new ArrayList<>();

        // 실행 후 결과 저장
        for (int i = 0; i < inputPaths.size(); i++) {
            Path inputPath = inputPaths.get(i);
            Path outputPath = outputPaths.get(i);

            String result = executer.run(inputPath);
            String answer = Files.readString(outputPath);

            boolean isAnswer = result.equals(answer);
            isAnswerList.add(CaseResult.builder()
                .problemNum(problemNum)
                .caseNum(i)
                .isAnswer(isAnswer)
                .build());
        }

        // 코드 파일 및 폴더 삭제
        executer.remove();

        return isAnswerList;
    }
}
