package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member111;
import com.bitcamp.util.LinkedList111;
import com.bitcamp.util.List111;

public class MemberDao111 {

  List111 list = new LinkedList111();

  public void insert(Member111 member) {
    list.add(member);
  }

  public Member111 findByEmail(String email) {
    for(int i = 0; i < list.size(); i++) {
      Member111 member = (Member111) list.get(i);
      if (member.email.equals(email)) { 
        return member;
      }
    }
    return null;
  }

  public boolean delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member111 member = (Member111) list.get(i);
      if (member.email.equals(email)) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Member111[] findAll() {
    Object[] arr = list.toArray();
    Member111[] members = new Member111[arr.length];
    for (int i = 0; i < arr.length; i++) {
      members[i] = (Member111) arr[i];
    }
    return members;
  }
}
