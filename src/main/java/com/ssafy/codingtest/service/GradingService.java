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
        JavaExecuter executer = new JavaExecuter();
        executer.saveAndCompile(code);

        // 탐색할 폴더 경로
        Path folderPath = BaekjoonInputOutputGenerator.getProblemFolderPath(problemNum);

        Path inputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.inputDir);
        Path outputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.outputDir);

        // Files.walk()를 사용하여 폴더 내 모든 파일과 폴더의 경로를 가져옴
        List<Path> inputPaths = Files.walk(inputDirPath).filter(Files::isRegularFile).toList();
        List<Path> outputPaths = Files.walk(outputDirPath).filter(Files::isRegularFile).toList();

        List<CaseResult> isAnswerList = new ArrayList<>();

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

        executer.remove();

        return isAnswerList;
    }
}
