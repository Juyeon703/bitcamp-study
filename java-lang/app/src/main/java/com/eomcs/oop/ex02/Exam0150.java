package com.eomcs.oop.ex02;

//00) 낱개 변수 사용
//01) 성적 데이터를 저장할 사용자 정의 데이터 타입을 만든다.
//02) 리팩토링 : 메서드 추출(extract method), static nested class
//03) 리팩토링 : 메서드 추출(extract method) = 한 개의 메서드는 한 개의 기능을 수행해야 한다.
//04) GRASP(General Responsibility Assignment Software Patterns) 패턴 : 
//  => Information Expert : 데이터를 다룰 때는 그 데이터를 갖고 있는 객체에게 묻는다.
//       리팩토링 : 메서드 이동(Move Method)
//          => 메서드를 관련된 클래스로 이동시킨다 => 코드의 이해가 쉽다.
//05) 인스턴스 메서드 : 인스턴스 주소를 받는 더 쉬운 문법
public class Exam0150 {

  // 여러 메서드에서 공유하려면 클래스 멤버로 만들어야 한다.
  //  특히 스태틱 멤버끼리 공유하려면 같은 스태틱 멤버로 만들어야 한다
  static class Score {
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

  public static void main(String[] args) {

    Score s1 = new Score();
    s1.name = "홍길동";
    s1.kor = 100;
    s1.eng = 90;
    s1.math = 85;

    // 다음은 Score의 값을 다루기 위해 non-instance 메서드를 호출하는 예이다.
    // => non-instance 메서드 = static 메서드 = 클래스 메서드
    //
    //    Score.calculate(score);
    //

    // 클래스 메서드를 사용할 때 마다 매번 인스턴스의 주소를 파라미터로 넘겨줘야 했다.
    // 그러나 인스턴스 메서드를 사용하면 인스턴스 주소를 넘기기가 더 편하다.
    // 메서드 호출 앞에다 둔다.
    // 소스 코드의 목적을 이해하는데 더 직관적이다.
    // 마치 변수 뒤에 연산자를 놓는 i++ 의 예와 비슷하다.
    s1.compute();
    printScore(s1);

    Score s2 = new Score();
    s2.name = "임꺽정";
    s2.kor = 90;
    s2.eng = 80;
    s2.math = 75;
    s2.compute();
    printScore(s2);

    Score s3 = new Score();
    s3.name = "유관순";
    s3.kor = 80;
    s3.eng = 70;
    s3.math = 65;
    s3.compute();
    printScore(s3);
  }

  static void printScore(Score s) {
    System.out.printf("%s: %d, %d, %d, %d, %.1f\n", s.name, s.kor, s.eng, s.math, s.sum, s.aver);
  }

}



