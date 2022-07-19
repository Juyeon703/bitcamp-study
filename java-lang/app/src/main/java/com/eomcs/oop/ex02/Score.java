package com.eomcs.oop.ex02;

//다른 패키지에서 사용할 수 있도록 public으로 공개한다.
public class Score {
  String name; // 변수 또는 필드
  int kor;
  int eng;
  int math;
  int sum;
  float aver;

  // static method ==> instance method
  void compute() {
    // 인스턴스 메서드를 호출할 때 넘겨준 인스턴스 주소는
    // this 라면 내장 변수 (built-in)에 보관된다.
    this.sum = this.kor + this.eng + this.math;
    this.aver = (float) this.sum / 3;
  }
}
