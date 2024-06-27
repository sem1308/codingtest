package com.ssafy.codingtest.problem.baekjoon.generator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class Baekjoon2609Generator extends BaekjoonGenerator {
    // 알고리즘에 필요한 데이터
    Random random = new Random();
    int min = 1;
    int max = 10000;

    public static void main(String[] args) {
        Baekjoon2609Generator baekjoon2609Generator = new Baekjoon2609Generator();
        baekjoon2609Generator.generate();
    }

    public Baekjoon2609Generator(){
        super("2609");
    }

    @Override
    public Supplier<String> setInputFunc() {
        return () -> {
            // 두 정수를 랜덤하게 생성
            int A = random.nextInt(max - min + 1) + min;
            int B = random.nextInt(max - min + 1) + min;
            return A + " " + B;
        };
    }

    @Override
    public Function<List<String>, String> setOutputFunc() {
        return (lines) -> {
            String[] numbers = lines.get(0).split(" ");
            int num1 = Integer.parseInt(numbers[0]);
            int num2 = Integer.parseInt(numbers[1]);

            // 최대공약수 계산
            int gcd = gcd(num1, num2);

            // 최소공배수 계산
            int lcm = lcm(num1, num2, gcd);
            return gcd + "\n" + lcm;
        };
    }

    // 최대공약수 계산 메소드 (유클리드 호제법)
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 최소공배수 계산 메소드
    private int lcm(int a, int b, int gcd) {
        return a * b / gcd;
    }
}
