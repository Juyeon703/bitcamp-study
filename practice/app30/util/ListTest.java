package com.bitcamp.util;

import com.bitcamp.board.domain.Board;

public class ListTest {
  public static void main(String[] args) {
    ObjectList<String> list = new ObjectList<>(); //뒤 <>는 String 생략 가능
    list.add("홍길동");
    list.add("임꺽정");
    list.add(null);
    //    list.add(new Board());// 컴파일 오류

    String s1 = list.get(0); //형변환 할 필요가 없다.
    System.out.println(s1);

    ObjectList<Board> list2 = new ObjectList<>();
    // list2.add("Hello"); // 컴파일 오류
    list2.add(new Board());
    // list2.add(new Member()); //컴파일 오류
    Board b = list2.get(0);

    System.out.println("--------------------------------");
    LinkedList<String> list3 = new LinkedList<>();
    list3.add("홍길동");
    list3.add("임꺽정");
    list3.add("유관순");
    list3.add("안중근");

    String[] names = new String[list3.size()];
    list3.toArray(names);
    // => String[] names = list3.toArray(new String[0]); -> 단점:쓸데없는 가비지 생성

    for (String name : names) {
      System.out.println(name);
    }
  }
}
