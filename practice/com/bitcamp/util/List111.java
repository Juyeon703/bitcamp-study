package com.bitcamp.util;
/**
 * 인덱스를 기반으로 목록을 다루는 메서드의 규격을 정한다.
 * @author User
 *
 */
public interface List111 {

  void add(Object value);
  Object get(int index);
  Object remove(int index);
  Object[] toArray();
  int size();
}
