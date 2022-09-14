package com.bitcamp.client.board;

import java.util.ArrayList;
import java.util.Stack;
import com.bitcamp.client.board.handler.BoardHandler111;
import com.bitcamp.client.board.handler.MemberHandler111;
import com.bitcamp.client.handler.Handler111;
import com.bitcamp.client.util.Prompt111;

public class ClientApp111 {

  public static Stack<String> breadcrumbMenu = new Stack<>();

  public static void main(String[] args) {
    System.out.println("[게시글 관리 클라이언트]");

    welcome();
    ArrayList<Handler111> handlers = new ArrayList<>();
    handlers.add(new BoardHandler111());
    handlers.add(new MemberHandler111());

    breadcrumbMenu.push("메인");
    String[] menus = {"게시판", "회원"};
    loop: while (true) {
      printTitle();
      printMenus(menus);
      System.out.println();
      try {
        int mainMenuNo = Prompt111.inputInt(String.format(
            "메뉴를 선택하세요[1..%d](0: 종료) ", handlers.size()));
        if (mainMenuNo < 0 || mainMenuNo > menus.length) {
          System.out.println("메뉴 번호가 옳지 않습니다!");
          continue;
        } else if (mainMenuNo == 0) {
          break loop;
        }
        breadcrumbMenu.push(menus[mainMenuNo - 1]);
        handlers.get(mainMenuNo - 1).execute();
        breadcrumbMenu.pop();
      } catch(Exception ex) {
        System.out.println("입력 값이 옳지 않습니다.");
      }
    } // while
    Prompt111.close();
    System.out.println("종료!");
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

  protected static void printTitle() {
    StringBuilder builder = new StringBuilder();
    for (String title : breadcrumbMenu) {
      if (!builder.isEmpty()) {
        builder.append(" > ");
      }
      builder.append(title);
    }
    System.out.printf("%s:\n", builder.toString());
  }
}
