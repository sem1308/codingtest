package com.ssafy.codingtest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class JavaExecuter {
    String baseDir = "temp";
    String folderName;
    String fileName = "Main.java";
    // 폴더 경로 설정
    Path folderPath;
    Path filePath;

    public void saveAndCompile(String code){
        save(code);
        compile();
    }

    public void save(String code){
        // UUID 기반 폴더 이름 생성
        folderName = UUID.randomUUID().toString();
        // 폴더 경로 설정
        folderPath = Paths.get(baseDir, folderName);
        filePath = folderPath.resolve(fileName);
        // 폴더 생성 및 Java 소스 코드 파일로 저장
        try {
            Files.createDirectories(folderPath);
            Files.writeString(filePath, code, StandardCharsets.UTF_8);
            System.out.println("Java source code has been written to " + filePath.toString());
        } catch (IOException e) {
            System.err.println("An error occurred while writing the Java source code: " + e.getMessage());
        }
    };

    public void compile(){
        try {
            Process compileProcess = new ProcessBuilder("javac","-encoding","UTF-8", filePath.toString()).inheritIO().start();
            compileProcess.waitFor();
            if (compileProcess.exitValue() == 0) {
                System.out.println("Java source code compiled successfully.");
            } else {
                System.err.println("Compilation failed.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred during compilation: " + e.getMessage());
        }
    };

    public String run(Path inputFilePath) throws IOException, InterruptedException {
        Process runProcess = new ProcessBuilder("java", "-cp", folderPath.toString(), "Main").start();

        // 프로세스에 입력 스트림 연결
        PrintWriter processInput = new PrintWriter(new OutputStreamWriter(runProcess.getOutputStream()));

        // 파일에서 입력을 읽어 프로세스에 전달
        BufferedReader inputFileReader = new BufferedReader(new InputStreamReader(Files.newInputStream(inputFilePath)));
        String line;
        while ((line = inputFileReader.readLine()) != null) {
            processInput.println(line);
        }
        processInput.flush();

        // 프로세스의 stdout 출력
        StringBuilder sb = new StringBuilder();

        BufferedReader processOutput = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
        String outputLine;
        while ((outputLine = processOutput.readLine()) != null) {
            sb.append(outputLine).append("\n");
        }

        runProcess.waitFor();

        return sb.toString().trim();
    }

    public void remove() {
        try {
            Files.walk(folderPath)
                .sorted((a, b) -> -a.compareTo(b)) // 하위 폴더부터 삭제하기 위해 역순 정렬
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}