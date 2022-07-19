package com.eomcs.oop.ex02.test;


//07) 클래스를 역할에 따라 패키지로 분류
//   => 클래스가 많을 경우 유지보수하기 쉽도록 적절한 패키지로 분산 배치한다.
//   => 데이터 타입의 역할을 하는 클래스의 경우 
//    보통 domain, vo(value object), dto(data transfer object) 라는 이름을 가진 패키지에 분류한다
//   => 패키지가 다르면 modifier 옵션에 따라 접근 범위가 달라진다.
//    멤버의 접근 범위 설정
//    => public :  모두 공개
//    => protected :  서브 클래스와 같은 패키지의 멤버는 접근 가능
//    => (default) : 같은 패키지의 멤버는 접근 가능
//    => private : 접근 불가! 그 멤버가 속한 클래스의 내부에서만 접근 가능
//08) 생성자 도입 : 인스턴스를 생성할 때 값을 초기화시키는 특별한 메서드


public class ExamTest {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    void compute() {
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
