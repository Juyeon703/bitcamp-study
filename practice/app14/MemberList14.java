package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member111;

// 회원 목록을 관리하는 역할
//
public class MemberList14 extends ObjectList14{

  private int no = 0;

  private int nextNo() {
    return ++no;
  }

  @Override
  public void add(Object obj) {
    Member111 member = (Member111) obj; //원래 타입으로 형변환
    member.no = nextNo();
    super.add(obj); //obj대신 member넣어도 상관없음, 여기서 super 대신 this넣으면 재귀호출이 됨 무한반복
  }

  @Override
  public Member111 get(int memberNo) {
    for (int i = 0; i < this.length; i++) {
      Member111 member = (Member111) this.list[i];
      if (member.no == memberNo) {
        return member;
      }
    }
    return null;
  }

  @Override
  public boolean remove(int memberNo) {
    int index = -1;
    for (int i = 0; i < this.length; i++) {
      Member111 member = (Member111) this.list[i];
      if (member.no == memberNo) {
        index = i;
        break;
      }
    }
    return super.remove(index);
  }
}














