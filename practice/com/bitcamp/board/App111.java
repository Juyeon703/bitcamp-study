package com.bitcamp.board;

import com.bitcamp.board.handler.BoardHandler111;
import com.bitcamp.board.handler.MemberHandler111;
import com.bitcamp.util.Prompt111;

public class App111 {

  public static void main(String[] args) {
    welcome();

    BoardHandler111 boardHandler = new BoardHandler111("게시판");
    BoardHandler111 readingHandler = new BoardHandler111("독서록");
    BoardHandler111 visitHandler = new BoardHandler111("방명록");
    BoardHandler111 noticeHandler = new BoardHandler111("공지사항");
    BoardHandler111 diaryHandler = new BoardHandler111("일기장");
    MemberHandler111 memberHandler = new MemberHandler111();

    loop: while (true) {
      System.out.println("메뉴:");
      System.out.println("  1: 게시판");
      System.out.println("  2: 독서록");
      System.out.println("  3: 방명록");
      System.out.println("  4: 공지사항");
      System.out.println("  5: 일기장");
      System.out.println("  6: 회원");
      System.out.println();

      try {
        int mainMenuNo = Prompt111.inputInt("메뉴를 선택하세요[1..6](0: 종료) ");
        switch (mainMenuNo) {
          case 0: break loop;
          case 1: boardHandler.execute(); break;
          case 2: readingHandler.execute(); break;
          case 3: visitHandler.execute(); break;
          case 4: noticeHandler.execute(); break;
          case 5: diaryHandler.execute(); break;
          case 6: memberHandler.execute(); break;
          default: System.out.println("메뉴 번호가 옳지 않습니다!");
        }
      } catch(Exception ex) {
        System.out.println("입력 값이 옳지 않습니다.");
      }
    } // while

    System.out.println("안녕히 가세요!");
    Prompt111.close();
  } // main

  static void welcome() {
    System.out.println("[게시판 애플리케이션]");
    System.out.println();
    System.out.println("환영합니다!");
    System.out.println();
  }
}
