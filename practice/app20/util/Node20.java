package com.bitcamp.util;
/**
 * 연결 리스트의 각 항목의 값을 저장하는 일을 할 클래스이다.
 * @author User
 *
 */
public class Node {
  Object value; // 데이터 타입을 여러가지 사용하기 위함
  Node prev;
  Node next;

  public Node() {}
  public Node(Object v) {
    this.value = v;
  }
}
