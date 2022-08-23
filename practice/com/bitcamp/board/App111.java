package com.bitcamp.board;

import java.util.Stack;
import com.bitcamp.board.handler.BoardHandler111;
import com.bitcamp.board.handler.MemberHandler111;
import com.bitcamp.handler.Handler;
import com.bitcamp.util.Prompt111;

public class App111 {

  public static Stack<String> breadcrumbMenu = new Stack<>();

  public static void main(String[] args) {
    welcome();
    Handler[] handlers = new Handler[] { 
        new BoardHandler111(),
        new BoardHandler111(),
        new BoardHandler111(),
        new BoardHandler111(),
        new BoardHandler111(),
        new MemberHandler111()
    };
    breadcrumbMenu.push("메인");
    String[] menus = {"게시판", "독서록", "방명록", "공지사항", "일기장", "회원"};
    loop: while (true) {
      System.out.printf("%s: \n", breadcrumbMenu);
      printMenus(menus);
      System.out.println();
      try {
        int mainMenuNo = Prompt111.inputInt("메뉴를 선택하세요[1..6](0: 종료) ");
        if (mainMenuNo < 0 || mainMenuNo > menus.length) {
          System.out.println("메뉴 번호가 옳지 않습니다!");
          continue;
        } else if (mainMenuNo == 0) {
          break loop;
        }
        breadcrumbMenu.push(menus[mainMenuNo - 1]);
        handlers[mainMenuNo - 1].execute();
        breadcrumbMenu.pop();
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

  static void printMenus(String[] menus) {
    for (int i = 0; i < menus.length; i++) {
      System.out.printf("  %d: %s\n", i + 1, menus[i]);
    }
  }
}
