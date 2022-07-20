package com.eomcs.oop.ex02;

//# 관련된 기능(메서드)을 묶어 분류하기 - 클래스로 분류
//01) 분류전
//02) 클래스로 묶어 분류하기
//03) 클래스 변수 도입
//04) 클래스 변수의 한계 확인
//05) 인스턴스 변수 도입
//06) 인스턴스 메서드 활용
//07) 패키지 멤버 클래스로 분리
//08) 클래스를 역할에 따라 패키지로 분류하기
public class Exam0210Test {

  public static void main(String[] args) {

    Calculator c1 = new Calculator();

    // 메서드를 호출하여 작업을 수행하고,
    // 리턴 결과는 로컬 변수에 저장한다.
    c1.plus(2);
    c1.plus(3);
    c1.minus(1);
    c1.multiple(7);
    c1.divide(1);

    System.out.printf("result = %d\n", c1.result);
  }

  static class Calculator {
    int result = 0;

    void plus(int value) {
      this.result += value;
    }

    void minus(int value) {
      this.result -= value;
    }

    void multiple(int value) {
      this.result *= value;
    }

    void divide(int value) {
      this.result /= value;
    }

  }

}


