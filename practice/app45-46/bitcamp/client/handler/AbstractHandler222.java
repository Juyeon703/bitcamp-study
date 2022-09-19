package com.bitcamp.client.handler;

import com.bitcamp.client.board.ClientApp111;
import com.bitcamp.client.util.Prompt111;

public abstract class AbstractHandler222 implements Handler111 {

  private String[] menus;

  public AbstractHandler111(String[] menus) {
    this.menus = menus;
  }

  protected void printMenus() {
    for (int i = 0; i < menus.length; i++) {
      System.out.printf("  %d: %s\n", i + 1, menus[i]);
    }
  }

  protected static void printTitle() {
    StringBuilder builder = new StringBuilder();
    for (String title : ClientApp111.breadcrumbMenu) {
      if (!builder.isEmpty()) {
        builder.append(" > ");
      }
      builder.append(title);
    }
    System.out.printf("%s:\n", builder.toString());
  }

  @Override
  public void execute() {
    while (true) {
      printTitle();
      printMenus();
      System.out.println();
      try {
        int menuNo = Prompt111.inputInt(String.format(
            "메뉴를 선택하세요[1..%d](0: 이전) ", menus.length));
        if (menuNo < 0 || menuNo > menus.length) {
          System.out.println("메뉴 번호가 옳지 않습니다!");
          continue;
        } else if (menuNo == 0) {
          return;
        }
        ClientApp111.breadcrumbMenu.push(menus[menuNo - 1]);
        System.out.println("---------------------------------------------");
        printTitle();
        service(menuNo);
        ClientApp111.breadcrumbMenu.pop();
      } catch (Exception ex) {
        System.out.printf("예외 발생: %s\n", ex.getMessage());
      }
    }
  }

  public abstract void service(int menuNo);
}
