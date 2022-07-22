/*
 * 게시판 관리 애플리케이션
 * 비트캠프 - 20220704
 */
package com.bitcamp.board;

public class App {

  public static void main(String[] args) {
    welcome();

    BoardHandler boardHandler = new BoardHandler("게시판");
    BoardHandler readingHandler = new BoardHandler("독서록");
    BoardHandler visitHandler = new BoardHandler("방명록");
    BoardHandler noticeHandler = new BoardHandler("공지사항");
    BoardHandler diaryHandler = new BoardHandler("일기장");
    MemberHandler memberHandler = new MemberHandler("회원");

    loop : while (true) {
      // 메인 메뉴 출력
      System.out.println("메뉴:");
      System.out.println("  1: 게시판");
      System.out.println("  2: 독서록");
      System.out.println("  3: 방명록");
      System.out.println("  4: 공지사항");
      System.out.println("  5: 일기장");
      System.out.println("  6: 회원");
      int mainMenuNo = Prompt.inputInt("메뉴를 선택하세요[1..6](0: 종료) ");
      switch (mainMenuNo) {
        case 0 : break loop;
        case 1 : boardHandler.execute(); break;
        case 2 : readingHandler.execute(); break;
        case 3 : visitHandler.execute(); break;
        case 4 : noticeHandler.execute(); break;
        case 5 : diaryHandler.execute(); break;
        case 6 : memberHandler.execute(); break;
        default : System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    } // while 
    System.out.println("안녕히 가세요!");
    Prompt.close();
  } //main


  static void welcome() {
    System.out.println("[게시판 애플리케이션]");
    System.out.println();
    System.out.println("환영합니다!");
    System.out.println();
  }

} //class


