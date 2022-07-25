/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board;

import java.util.Date;

public class MemberHandler {

  String title;

  //게시글 목록을 관리할 객체 준비
  MemberList memberList = new MemberList();  
 
  // 제목을 입력 받는 생성자
  MemberHandler(String title) {
    this.title = title;
  }


  void execute() {

    while (true) {
      System.out.printf("%s: \n", this.title);
      System.out.println("  1: 목록");
      System.out.println("  2: 상세보기");
      System.out.println("  3: 등록");
      System.out.println("  4: 삭제");
      System.out.println("  5: 변경");
      System.out.println();
      int menuNo = Prompt.inputInt("메뉴를 선택하세요[1..5](0: 이전) ");
      displayHeadLine();

      switch (menuNo) {
        case 0 : return;
        case 1 : this.onList(); break;
        case 2 : this.onDetail(); break;
        case 3 : this.onInput(); break;
        case 4 : this.onDelete(); break;
        case 5 : this.onUpdate(); break;
        default : System.out.println("메뉴 번호가 옳지 않습니다!");
      }
      displayBlankLine();
    } //게시판 while
  }

  static void displayHeadLine() {
    System.out.println("------------------------------------------------");
  }

  static void displayBlankLine() {
    System.out.println();
  }

  void onList() {
    System.out.printf("[%s 목록]\n", this.title);
    System.out.println("번호  이름  이메일");


    Member[] list = this.memberList.toArray();

    for (Member member : list) {
      System.out.printf("%d\t%s\t%s\n", // 포멧에 따라 출력
          member.no, member.name, member.email);
    }
  }


  void onDetail() {
    System.out.printf("[%s 상세보기]\n", this.title);
    System.out.println();

    int memberNo = Prompt.inputInt("조회할 회원 번호? "); 

    Member member = this.memberList.get(memberNo);


    if(member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      System.out.println();
      return;
    }

    System.out.printf("번호 : %d\n", member.no);
    System.out.printf("이름 : %s\n", member.name);
    System.out.printf("이메일 : %s\n", member.email);
    Date date = new Date(member.createdDate);
    System.out.printf("등록일 : %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }


  void onInput() {
    System.out.printf("[%s 등록]\n", this.title);
    System.out.println();

    Member member = new Member();
    member.name = Prompt.inputString("이름? ");
    member.email = Prompt.inputString("이메일? ");
    member.password = Prompt.inputString("암호? ");
    member.createdDate = System.currentTimeMillis();

    this.memberList.add(member);

    System.out.println("회원을 등록했습니다.");
  }

  void onDelete() {
    System.out.printf("[%s 삭제]\n", this.title);
    System.out.println();

    int memberNo = Prompt.inputInt("삭제할 회원 번호? "); 

    if (memberList.remove(memberNo)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 회원이 없습니다!");
    }
  }

  void onUpdate() {
    System.out.printf("[%s 변경]\n", this.title);
    System.out.println();

    int memberNo = Prompt.inputInt("변경할 회원 번호? "); 

    Member member = this.memberList.get(memberNo);

    if(member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    String newName = Prompt.inputString("이름?(" + member.name +") ");
    String newEmail = Prompt.inputString(String.format("이메일?(%s) ", member.email));

    String input = Prompt.inputString("변경하시겠습니까?(y/n)");
    if (input.equals("y")) {
      member.name = newName;
      member.email = newEmail;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }


} //class


