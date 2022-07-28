/*
 * 키보드 입력을 받는 도구를 구비하고 있다.
 */
package com.bitcamp.board;

public class Prompt {

  static java.util.Scanner keyboardInput = new java.util.Scanner(System.in);

  //메서드를 통해 특정 코드의 복잡함을 감출 수 있다. -> encapsulation 캡슐화
  static int inputInt() {
    /*  사용자로부터 메뉴 번호를 입력 받기
    방법 1 : */
    String str = keyboardInput.nextLine();
    return Integer.parseInt(str);  //"123" => 123, "5" => 5, "ok" => 실행오류

    /*  방법2 :
    int value = keyboardInput.nextInt(); // 저장할 메모리 필요! 익스프레이션이란? 결과를 리턴하는 명령문 변수명은 알아보기 쉽게
    keyboardInput.nextLine(); // 입력한 숫자 뒤에 남아 있는 줄바꿈 코드 제거
    return value; */
  }

  static int inputInt(String title) {
    System.out.print(title);
    String str = keyboardInput.nextLine();
    return Integer.parseInt(str);  
  }

  static String inputString() {
    return keyboardInput.nextLine();
  }

  static String inputString(String title) {
    System.out.print(title);
    return keyboardInput.nextLine();
  }

  static void close() {
    keyboardInput.close();
  }
} //class


