package com.eomcs.oop.ex02.domain;

//다른 패키지에서 사용할 수 있도록 public으로 공개한다.
public class Score2 {

  // 다른 패키지에서 이 설계도에 따라 만든 변수에 접근할 수 있도록
  // 접근 범위를 넓힌다.
  public String name; 
  public int kor;
  public int eng;
  public int math;
  public int sum;
  public float aver;

  // new 연산자를 이용하여 인스턴스를 만들 때 자동으로 호출되는 특별한 문법의 메서드
  //  => 생성자(construtor)
  //      - 메서드명은 클래스 이름과 같아야 한다.
  //      - 리턴 타입은 없다.
  //      - 오직 new 명령을 실행할 떄 호출할 수 있다. 나중에 따로 호출할 수 없다.
  public Score2 (String n, int k, int e, int m) {
    this.name = n;
    this.kor = k;
    this.eng = e;
    this.math = m;
    this.compute();
  }
  // 다른 패키지에서 메서드를 사용할 수 있도록 공개한다.
  public void compute() {
    // 인스턴스 메서드를 호출할 때 넘겨준 인스턴스 주소는
    // this 라면 내장 변수 (built-in)에 보관된다.
    this.sum = this.kor + this.eng + this.math;
    this.aver = (float) this.sum / 3;
  }
}
