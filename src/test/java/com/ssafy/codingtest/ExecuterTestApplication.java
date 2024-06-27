package com.ssafy.codingtest;

import com.ssafy.codingtest.problem.baekjoon.BaekjoonInputOutputGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExecuterTestApplication {
    public static void main(String[] args) throws IOException {
        String code = """
        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.io.IOException;
        import java.util.StringTokenizer;

        public class Main {

            public static void main(String[] args) throws IOException {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                StringTokenizer st = new StringTokenizer(br.readLine(), " ");

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                int d = gcd(a, b);

                System.out.println(d);
                System.out.println(a * b / d);

            }

            // 최대공약수 반복문 방식
            public static int gcd(int a, int b) {

                while (b != 0) {
                    int r = a % b; // 나머지를 구해준다.

                    // GCD(a, b) = GCD(b, r)이므로 변환한다.
                    a = b;
                    b = r;
                }
                return a;
            }
        }
        """;

        JavaExecuter executer = new JavaExecuter();
        executer.saveAndCompile(code);

        // 탐색할 폴더 경로
        String problemNum = "2609";
        Path folderPath = BaekjoonInputOutputGenerator.getProblemFolderPath(problemNum);

        Path inputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.inputDir);
        Path outputDirPath = folderPath.resolve(BaekjoonInputOutputGenerator.outputDir);

        // Files.walk()를 사용하여 폴더 내 모든 파일과 폴더의 경로를 가져옴
        List<Path> inputPaths = Files.walk(inputDirPath).filter(Files::isRegularFile).toList();
        List<Path> outputPaths = Files.walk(outputDirPath).filter(Files::isRegularFile).toList();

        for (int i = 0; i < inputPaths.size(); i++) {
            Path inputPath = inputPaths.get(i);
            Path outputPath = outputPaths.get(i);
            try {
                String result = executer.run(inputPath);
                System.out.println(inputPath);
                System.out.println(result);
                String answer = Files.readString(outputPath);
                System.out.println(result.equals(answer) ? "O" : "X");

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executer.remove();
    }
}
