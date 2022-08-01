package com.bitcamp.board.handler;

import java.util.Date;
import com.bitcamp.board.dao.MemberList111;
import com.bitcamp.board.domain.Member111;
import com.bitcamp.util.Prompt111;

public class MemberHandler111 {

  private MemberList111 memberList = new MemberList111();

  public void execute() {
    while (true) {
      System.out.println("회원: ");
      System.out.println("  1: 목록");
      System.out.println("  2: 상세보기");
      System.out.println("  3: 등록");
      System.out.println("  4: 삭제");
      System.out.println("  5: 변경");
      System.out.println();
      int menuNo = Prompt111.inputInt("메뉴를 선택하세요[1..5](0: 이전) ");
      System.out.println("---------------------------------------------");
      switch (menuNo) {
        case 0: return;
        case 1: onList(); break;
        case 2: onDetail(); break;
        case 3: onInput(); break;
        case 4: onDelete(); break;
        case 5: onUpdate(); break;
        default: System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    }
  }

  private void onList() {
    System.out.println("[회원 목록]");
    System.out.println("번호 이름 이메일");

    Object[] list = memberList.toArray();

    for (Object item : list) {
      Member111 member = (Member111) item;
      System.out.printf("%d\t%s\t%s\n", member.no, member.name, member.email);
    }
  }

  private void onDetail() {
    System.out.println("[회원 상세보기]");
    String email = Prompt111.inputString("조회할 회원 이메일? ");
    Member111 member = memberList.get(email);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }
    System.out.printf("번호: %d\n", member.no);
    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    Date date = new Date(member.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }

  private void onInput() {
    System.out.println("[회원 등록]");
    Member111 member = new Member111();
    member.name = Prompt111.inputString("이름? ");
    member.email = Prompt111.inputString("이메일? ");
    member.password = Prompt111.inputString("암호? ");
    member.createdDate = System.currentTimeMillis();
    memberList.add(member);
    System.out.println("회원을 등록했습니다.");
  }

  private void onDelete() {
    System.out.println("[회원 삭제]");
    String email = Prompt111.inputString("삭제할 회원 이메일? ");

    if (memberList.remove(email)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 회원이 없습니다!");
    }
  }

  private void onUpdate() {
    System.out.println("[회원 변경]");
    String email = Prompt111.inputString("변경할 회원 이메일? ");
    Member111 member = memberList.get(email);
    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }
    String newName = Prompt111.inputString("이름?(" + member.name +")");
    String newEmail = Prompt111.inputString("이메일?(" + member.email +")");
    String input = Prompt111.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      member.name = newName;
      member.email = newEmail;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
