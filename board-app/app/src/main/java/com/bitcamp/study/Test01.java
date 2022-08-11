package com.bitcamp.study;

import com.bitcamp.board.domain.Board;
import com.google.gson.Gson;

public class Test01 {
  public static void main(String[] args) {
    Board b = new Board();
    b.no = 100;
    b.title = "제목입니다.";
    b.writer = "홍길동";
    b.password = "1111";
    b.viewCount = 11;
    b.createdDate = System.currentTimeMillis();

    // Object --> JSON 문자열
    Gson gson = new Gson();
    String json = gson.toJson(b);
    System.out.println(json);

    // json을 사용해서 Board.class 설계도에 따라 만들어라
    Board b2 = gson.fromJson(json, Board.class); // 여기서 class는 파일확장자가 아니라 클래스의 정보가 저장된 레퍼런스(변수)임
    System.out.println(b2);

    System.out.println(b == b2); //false
  }
}
