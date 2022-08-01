package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member111;
import com.bitcamp.util.ObjectList;

public class MemberList111 extends ObjectList {

  public Member111 get(String email) {
    for(int i = 0; i < size(); i++) {
      Member111 member = (Member111) get(i);
      if (member.email.equals(email)) {
        return member;
      }
    }
    return null;
  }

  public boolean remove(String email) {
    for (int i = 0; i < size(); i++) {
      Member111 member = (Member111) get(i);
      if (member.email.equals(email)) {
        return remove(i);
      }
    }
    return false;
  }
}
