package com.bitcamp.util;

/**
 * 인덱스를 기반으로 목록을 다루는 메서드의 규격을 정한다.
 * @author User
 *
 */
// List에서 다루는 항목의 타입을 외부에서 받을 수 있도록
// 타입 파라미터를 선언한다.
// 타입 파라미터 이름: 
// - 타입을 받는 파라미터임을 드러내기 위해 대문자로 시작한다.
// - 간결하게 유지하기 위해 한 개의 알파벳을 사용한다. Element => E
// - 예)
//      E = Element (used extensively by the Java Collections Framework)
//      K = Key
//      N = Number
//      T = Type
//      V = Value
//      S, U, V etc. = 2nd, 3rd, 4th types


public interface List<E> {
  // public static final int a = 100; // public static final이 자동으로 붙음.
  // 메서드 형식
  // - 추상 메서드 형태
  // - 무조건 public으로 공개 (자동)

  // E가 가리키는 타입은 List를 사용하는 시점에 결정된다.
  void add (E value); // 목록에 값을 더하는 메서드의 형식
  E get (int index); // 목록에서 인덱스에 해당하는 항목을 꺼내는 메서드의 형식 
  E remove(int index); // 목록에서 인덱스에 해당하는 항목을 삭제하는 메서드의 형식
  Object[] toArray(); // 목록에 저장된 한목들을 배열에 담아 리턴하는 메서드의 형식
  E[] toArray(E[] arr); 
  int size(); // 목록에 저장된 항목의 개수를 리턴하는 메서드의 형식
}
