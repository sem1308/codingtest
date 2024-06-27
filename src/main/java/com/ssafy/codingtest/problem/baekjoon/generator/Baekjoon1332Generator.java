package com.ssafy.codingtest.problem.baekjoon.generator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class Baekjoon1332Generator extends BaekjoonGenerator {
    // 알고리즘에 필요한 데이터
    Random random = new Random();
    int MAX_N = 50;
    int MAX_V = 1000;

    public static void main(String[] args) {
        Baekjoon1332Generator baekjoon1332Generator = new Baekjoon1332Generator();
        baekjoon1332Generator.size = 30;
        baekjoon1332Generator.generate();
    }

    public Baekjoon1332Generator(){
        super("1332");
    }

    @Override
    public Supplier<String> setInputFunc() {
        return () -> {
            // 문제의 개수 N과 최댓값과 최솟값의 차이 V 생성
            int N = random.nextInt(MAX_N) + 1; // 1 이상 50 이하의 자연수
            int V = random.nextInt(MAX_V) + 1; // 1 이상 1000 이하의 자연수

            // 유진이가 느끼는 흥미도 P 배열 생성
            int[] P = new int[N];
            for (int i = 0; i < N; i++) {
                P[i] = random.nextInt(1001); // 0 이상 1000 이하의 자연수
            }

            // 입력 데이터 출력
            StringBuilder sb = new StringBuilder();
            sb.append(N).append(" ").append(V).append("\n");
            for (int i = 0; i < N; i++) {
                sb.append(P[i]).append(" ");
            }
            sb.append(" ");
            return sb.toString();
        };
    }

    @Override
    public Function<List<String>, String> setOutputFunc() {
        return (lines) -> {
            String[] numbers = lines.get(0).split(" ");
            int N = Integer.parseInt(numbers[0]);
            int V = Integer.parseInt(numbers[1]);

            int[] P = new int[N];

            numbers = lines.get(1).split(" ");
            for (int i = 0; i < N; i++) {
                P[i] = Integer.parseInt(numbers[i]);
            }

            int answer = N;

            // 0번 부터 i번 문제까지 풀었을 때

            // 0 1 => 1번
            // 0 1 2 => 1번
            // 0 1 2 3 => 2번
            // 0 1 2 3 4 => 2번
            // 0 1/ 2 3/ => 2번
            // 0 1 2/ 3 4/ => 2번
            // (minIdx + 1)/2 + (maxIdx - minIdx + 1)/2

            // i가 최소인 경우, j개 최대인 경우

            for (int i = 0; i < N; i++) {
                for (int j = i+1; j < N; j++) {
                    if(Math.abs(P[i] - P[j]) >= V){
                        // 최소, 최대 차이가 V 이상인 경우
                        int cnt = 1 + (i + 1)/2 + (j - i + 1)/2;
                        answer = Math.min(answer, cnt);
                    }
                }
            }

            return String.valueOf(answer);
        };
    }
}
