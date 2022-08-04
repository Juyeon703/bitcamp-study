package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member;
import com.bitcamp.util.LinkedList;
import com.bitcamp.util.List;

// 회원 목록을 관리하는 역할
//
public class MemberDao {

  // MemberDao는 List 규격에 맞춰 새안한 객체를 사용할 것이다.
  // => ObjectList 클래스는 List 규격에 맞춰 메서드를 정의한 클래스이다.
  // => 따라서 List 레퍼런스 변수에 그 주소를 저장할 수 있다.
  List<Member> list = new LinkedList<>();

  public void insert(Member member) {
    list.add(member);
  }

  // MemberList 에서 MemberDao 로 바꿔는 것에 맞춰
  // 메서드의 이름도 데이터에 초점을 맞춰 변경한다.
  //
  public Member findByEmail(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member member = list.get(i);
      if (member.email.equals(email)) {
        return member;
      }
    }
    return null;
  }

  public boolean delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member member = list.get(i);
      if (member.email.equals(email)) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Member[] findAll() {
    return list.toArray(new Member[0]);
  }
}














