package com.bitcamp.util;

import java.lang.reflect.Array;

// List 규격에 따라 메서드를 구현할 것이라고 선언한다!
// 만약 규격에 따라 메서드를 구현하지 않으면 컴파일을 안 해준다.
public class ObjectList<E> extends AbstractList<E> {

  private static final int DEFAULT_CAPACITY = 10;

  private Object[] elementData;

  public ObjectList() {
    elementData = new Object[DEFAULT_CAPACITY];
  }

  public ObjectList(int initialCapacity) {
    elementData = new Object[initialCapacity];
  }

  @Override
  // 공개 범위를 넓히는것은 가능하지만 좁히는것은 안됨.
  public void add(E e) {
    if (size == elementData.length) {
      grow();
    }

    elementData[size++] = e;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = elementData[i];
    }
    return arr;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E[] toArray(E[] arr) {
    if(arr.length < size) { // 파라미터로 받은 배열이 목록에 저장된 항목의 개수보다 작다면

      // 파라미터로 받은 배열과 똑같은 타입이 배열을 만든다.
      // 단 크기는 size에 저장한 개수만큼 만든다.
      arr = (E[]) Array.newInstance(arr.getClass().getComponentType()/*레퍼런스 배열의 항목 타입*/, size/*배열의 개수*/);
    }
    // 목록에 있는 항목들을 파라미터로 받은배열에 복사한다.
    for (int i = 0; i < size; i++) {
      arr[i] = (E) elementData[i];
    }
    return arr;
  }

  // 개발자가 예외 클래스 이름만 보고도 
  // 어떤 작업을 하다가 예외가 발생했는지 
  // 직관적으로 알 수 있도록 사용자 정의 예외를 던진다!!!
  // => RuntimeException 계열의 예외는 메서드 선언부에 표시할 필요가 없다.
  /**
   * 목록에서 인덱스에 해당 하는 항목을 찾아 리턴한다. 
   * @param index 목록에 저장된 항목의 인덱스
   * @return index에 저장된 항목
   * @throws ListException 인덱스가 무효함 
   */
  @SuppressWarnings("unchecked")
  @Override
  public E get(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스가 무효함!");
    }
    return (E) elementData[index];
  }

  @SuppressWarnings("unchecked")
  @Override // 인터페이스 규격에 따라 메서드를 정의하는 것도 오버라이딩으로 간주한다.
  public E remove(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스가 무효합니다!");
    }

    // 삭제한 객체를 리턴할 수 있도록 임시 변수에 담아둔다.
    E deleted = (E) elementData[index];
    for (int i = index + 1; i < size; i++) {
      elementData[i - 1] = elementData[i];
    }
    elementData[--size] = null;
    return deleted;
  }


  private void grow() {
    int newCapacity = elementData.length + (elementData.length >> 1);
    Object[] newArray = new Object[newCapacity];
    for (int i = 0; i < elementData.length; i++) {
      newArray[i] = elementData[i];
    }
    elementData = newArray;
  }
}








