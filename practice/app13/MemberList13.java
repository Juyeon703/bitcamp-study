package com.bitcamp.board;

// 게시글 목록을 관리하는 역할
//
public class MemberList {
  static final int DEFAULT_SIZE = 3; 
  int memberCount; 
  Member111[] members;
  int no = 0;

  //생성자
  MemberList() {
    this.members = new Member111[DEFAULT_SIZE];
  }

  MemberList(int initCapacity) {
    this.members = new Member111[initCapacity];
  }

  //목록에 저장된 인스턴스를 꺼내서 리턴한다.
  Member111[] toArray() {
    Member111[] arr = new Member111[this.memberCount];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = this.members[i];
    }
    return arr;
  }

  //게시글 번호에 해당하는 Board 인스턴스를 찾아 리턴한다.
  Member111 get(int memberNo) {
    for (int i = 0; i < this.memberCount; i++) {
      if (this.members[i].no == memberNo) {
        return this.members[i];
      }
    }
    return null;
  }

  //Board 인스턴스를 배열에 저장한다.
  void add(Member111 member) {
    if (this.memberCount == this.members.length) {
      grow();
    }
    member.no = nextNo();
    this.members[this.memberCount++] = member;
  }


  boolean remove(int memberNo) {
    int memberIndex = -1;
    for (int i = 0; i < this.memberCount; i++) {
      if (this.members[i].no == memberNo) {
        memberIndex = i;
        break;
      }
    }
    if (memberIndex == -1) {
      return false;
    }
    // 삭제할 항목의 다음 항목을 앞으로 당긴다.
    for(int i = memberIndex + 1; i < this.memberCount; i++) {
      this.members[i - 1] = this.members[i];
    }
    // 게시글 개수를 한 개 줄인 후 
    // 맨 뒤의 있던 항목의 주소를 0으로 설정한다.
    this.members[--this.memberCount] = null;

    return true;
  }

  void grow() {
    int newSize = this.members.length + (this.members.length >> 1);
    Member111[] newArray = new Member111[newSize];
    for (int i = 0; i < this.members.length; i++) {
      newArray[i] = this.members[i];
    }
    this.members = newArray;
  }

  int nextNo() {
    return ++no;
  }
}






















