package com.bitcamp.util;

public class ObjectListTest {
  public static void main(String[] args) {

    ObjectList list = new ObjectList();
    list.add("홍길동");
    list.add("임꺽정");
    list.add("유관순");
    list.add(null);
    list.add("안중근");

    System.out.println(list.get(0));// obj가 실제 가리키는 것은 String 클래스이다.
    System.out.println(list.get(1)); //임꺽정
    System.out.println(list.get(2)); //유관순


    //println()
    // 파라미터 값이 유효한 인스턴스 주소라면 toString()을 호출하여 그 리턴 값을 출력한다.
    // 따라서 굳이 toString()을 명시적으로 호출할 필요가 없다.
    System.out.println(list.get(4)); // 안중근
    System.out.println(list.get(4).toString()); // 안중근

    // 파라미터 값이 null이라면 그래도 null을 출력한다.
    // 3번 인덱스에 null이 들어 있기 때문에 꺼낸 값도 null이다.
    System.out.println(list.get(3)); //println()에 전달하는 값이 null이면 null 출력

    // 100번 인덱스는 유효한 인덱스가 아니기 때문에 get() 메서드가 null을 리턴한다.
    System.out.println("list.get(100)");
    // 생각해 볼 문제!
    // 목록에서 null을 리턴한다면,
    // 예외 상황인가? 아니면 그냥 정상적인 상황인가?
    // 결론?
    // null을 리턴한다고 해서 무조건 예외 상황이 아니다.
    // 의도적으로 null을 저장해놓고 꺼낼 수 있기 때문이다.
    // 따라서 인덱스를 잘못 지정한 경우와 구분해야 한다.
    // 인덱스를 잘못 지정했으면 예외 상황이지만,
    // 그밖에는 정상적인 상황이다.

    // 결론! 알 수 없다.
    // 리턴 값으로는 정상과 예외 상황을 표현할 수 없다.
    // 리턴 값으로 두 상황을 구분할 수 없다.
    // 해결책!
    // 정상적인 경우는 정상적으로 해당 값을 리턴한다.
    // 예외 상황일 경우에는 예외 정보를 던진다. 예외를 발생시킨다.

  }
}
