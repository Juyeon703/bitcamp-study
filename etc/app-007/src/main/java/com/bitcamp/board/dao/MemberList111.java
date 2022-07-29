package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member111;

public class MemberList111 {
  private static final int DEFAULT_SIZE = 3;
  private int memberCount;
  private Member111[] members;
  private int no = 0;

  public MemberList111() {
    members = new Member111[DEFAULT_SIZE];
  }

  public Member111[] toArray() {
    Member111[] arr = new Member111[memberCount];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = members[i];
    }
    return arr;
  }

  public Member111 get(int memberNo) {
    for(int i = 0; i < memberCount; i++) {
      if (members[i].no == memberNo) {
        return members[i];
      }
    }
    return null;
  }

  public void add(Member111 member) {
    if (memberCount == members.length) {
      grow();
    }
    member.no = nextNo();
    members[memberCount++] = member;
  }

  public boolean remove(int memberNo) {
    int memberIndex = -1;
    for (int i = 0; i < memberCount; i++) {
      if (members[i].no == memberNo) {
        memberIndex = i;
        break;
      }
    }
    if (memberIndex == -1) {
      return false;
    }
    for (int i = memberIndex + 1; i < memberCount; i++) {
      members[i - 1] = members[i];
    }
    members[--memberCount] = null;
    return true;
  }

  private void grow() {
    int newSize = members.length + (members.length >> 1);
    Member111[] newArray = new Member111[newSize];
    for (int i = 0; i < members.length; i++) {
      newArray[i] = members[i];
    }
    members = newArray;
  }

  private int nextNo() {
    return ++no;
  }
}
