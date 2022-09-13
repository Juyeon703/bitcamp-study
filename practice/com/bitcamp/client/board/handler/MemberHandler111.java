package com.bitcamp.client.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;
import com.bitcamp.client.board.dao.MemberDaoProxy111;
import com.bitcamp.client.handler.AbstractHandler111;
import com.bitcamp.client.util.Prompt111;
import com.bitcamp.server.board.domain.Member111;

public class MemberHandler111 extends AbstractHandler111 {

  private MemberDaoProxy111 memberDao;

  public MemberHandler111(String dataName, DataInputStream in, DataOutputStream out) {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});

    memberDao = new MemberDaoProxy111(dataName, in, out);
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
    Member111[] members = memberDao.findAll();

    if (members == null) {
      System.out.println("목록을 가져오는데 실패했습니다!");
      return;
    }

    System.out.println("이메일 이름");

    for (Member111 member : members) {
      System.out.printf("%s\t%s\n", member.email, member.name);
    }
  }

  private void onDetail() throws Exception{
    String email = Prompt111.inputString("조회할 회원 이메일? ");

    Member111 member = memberDao.findByEmail(email);

    if (member == null) {
      System.out.println("해당 이메일의 회원이 없습니다!");
      return;
    }

    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.email);
    Date date = new Date(member.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }

  private void onInput() throws Exception {
    Member111 member = new Member111();
    member.name = Prompt111.inputString("이름? ");
    member.email = Prompt111.inputString("이메일? ");
    member.password = Prompt111.inputString("암호? ");
    member.createdDate = System.currentTimeMillis();

    if (memberDao.insert(member)) {
      System.out.println("회원을 등록했습니다.");
    } else {
      System.out.println("회원 등록에 실패했습니다!");
    }
  }

  private void onDelete() throws Exception {
    String email = Prompt111.inputString("삭제할 회원 이메일? ");

    if (memberDao.delete(email)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 이메일의 회원이 없습니다!");
    }
  }

  private void onUpdate() throws Exception {
    String email = Prompt111.inputString("변경할 회원 이메일? ");

    Member111 member = memberDao.findByEmail(email);

    if (member == null) {
      System.out.println("해당 이메일의 회원이 없습니다!");
      return;
    }

    member.name = Prompt111.inputString("이름?(" + member.name +")");

    String input = Prompt111.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {

      if (memberDao.update(member)) {
        System.out.println("변경했습니다.");
      } else {
        System.out.println("변경 실패입니다!");
      }

    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
