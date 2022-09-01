package com.bitcamp.client.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Stack;
import com.bitcamp.client.board.handler.BoardHandler111;
import com.bitcamp.client.board.handler.MemberHandler111;
import com.bitcamp.client.handler.Handler111;
import com.bitcamp.client.util.Prompt111;

public class ClientApp111 {

  public static Stack<String> breadcrumbMenu = new Stack<>();

  public static void main(String[] args) {
    System.out.println("[게시글 관리 클라이언트]");
    try(
        Socket socket = new Socket("127.0.0.1", 8888);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      System.out.println("연결 되었음!");

      welcome();
      Handler111[] handlers = new Handler111[] { 
          new BoardHandler111("board", in, out),
          new BoardHandler111("reading", in, out),
          new BoardHandler111("visit", in, out),
          new BoardHandler111("notice", in, out),
          new BoardHandler111("daily", in, out),
          new MemberHandler111("member", in, out)
      };
      breadcrumbMenu.push("메인");
      String[] menus = {"게시판", "독서록", "방명록", "공지사항", "일기장", "회원"};
      loop: while (true) {
        printTitle();
        printMenus(menus);
        System.out.println();
        try {
          int mainMenuNo = Prompt111.inputInt("메뉴를 선택하세요[1..6](0: 종료) ");
          if (mainMenuNo < 0 || mainMenuNo > menus.length) {
            System.out.println("메뉴 번호가 옳지 않습니다!");
            continue;
          } else if (mainMenuNo == 0) {
            out.writeUTF("exit");
            break loop;
          }
          breadcrumbMenu.push(menus[mainMenuNo - 1]);
          handlers[mainMenuNo - 1].execute();
          breadcrumbMenu.pop();
        } catch(Exception ex) {
          System.out.println("입력 값이 옳지 않습니다.");
        }
      } // while
      Prompt111.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
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
