package com.bitcamp.server.board.handler;

import java.util.List;
import com.bitcamp.client.util.Prompt;
import com.bitcamp.common.board.domain.Member111;
import com.bitcamp.server.board.dao.MemberDao111;
import com.bitcamp.server.handler.AbstractHandler111;

public class MemberHandler111 extends AbstractHandler111 {

  private MemberDao111 memberDao;

  public MemberHandler111(MemberDao111 memberDao) {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});

    this.memberDao = memberDao;
  }

  @Override
  public void service(int menuNo) { 
    try {
      switch (menuNo) {
        case 1: onList(); break;
        case 2: onDetail(); break;
        case 3: onInput(); break;
        case 4: onDelete(); break;
        case 5: onUpdate(); break;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void onList() throws Exception{
    List<Member111> members = memberDao.findAll();

    System.out.println("번호\t이름\t이메일");

    for (Member111 member : members) {
      System.out.printf("%d\t%s\t%s\n", 
          member.no, member.name, member.email);
    }
  }

  private void onDetail() throws Exception{
    int no = Prompt.inputInt("조회할 회원 번호? ");

    Member111 member = memberDao.findByNo(no);

    if (member == null) {
      System.out.println("해당 이메일의 회원이 없습니다!");
      return;
    }

    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", member.createdDate);
  }

  private void onInput() throws Exception {
    Member111 member = new Member111();
    member.name = Prompt.inputString("이름? ");
    member.email = Prompt.inputString("이메일? ");
    member.password = Prompt.inputString("암호? ");

    memberDao.insert(member);
    System.out.println("회원을 등록했습니다.");
  }

  private void onDelete() throws Exception {
    int no = Prompt.inputInt("삭제할 회원 번호? ");

    if (memberDao.delete(no) == 1) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 회원이 없습니다!");
    }
  }

  private void onUpdate() throws Exception {
    int no = Prompt.inputInt("변경할 회원 번호? ");

    Member111 member = memberDao.findByNo(no);

    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    member.name = Prompt.inputString("이름?(" + member.name + ") ");
    member.email = Prompt.inputString("이메일?(" + member.email + ") ");
    member.password = Prompt.inputString("암호?");

    String input = Prompt.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      if (memberDao.update(member) == 1) {
        System.out.println("변경했습니다.");
      } else {
        System.out.println("변경 실패입니다!");
      }
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
