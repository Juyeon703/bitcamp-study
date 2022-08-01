package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Member;
import com.bitcamp.util.LinkedList;

// 회원 목록을 관리하는 역할
//
public class MemberList extends LinkedList {

  // 인덱스 대신 이메일로 회원 데이터를 찾을 수 있도록
  // 메서드를 추가한다.
  // 기존의 메서드와 같은 이름으로 지어서
  // 메서드 호출할 때 일관되게 사용할 수 있다.
  // 오버로딩 
  // 오버로딩 메서드의 이름을 수퍼 클래스의 메서드 이름에 맞춰 변경한다.
  public Member retrieve(String email) {
    for (int i = 0; i < length(); i++) {
      Member member = (Member) retrieve(i); // 파라미터 타입이 달라서 super를 적지 않아도됨
      if (member.email.equals(email)) {
        return member;
      }
    }
    return null;
  }

  // 인덱스 대신 이메일로 회원 데이터를 찾아 삭제하는 메서드
  // 수퍼 클래스로부터 상속 받은 메서드와 같은 일을 하며
  // 메서드 이름도 같다. => 오버로딩(상속받은 메서드에서 파라미터 타입만 바꿔도 오버로딩임)

  // 수퍼 클래스 교체에 따라 메서드의 이름도 일관성 있게
  // 수퍼 클래스 메서드 이름과 같게 한다. 오버로딩 규칙을 준수한다.
  public Object delete(String email) {
    for (int i = 0; i < length(); i++) {
      Member member = (Member) retrieve(i);
      if (member.email.equals(email)) {
        return delete(i);
      }
    }
    return null;
  }
}














