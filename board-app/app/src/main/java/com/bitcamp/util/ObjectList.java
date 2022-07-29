package com.bitcamp.util;

public class ObjectList {

  private static final int DEFAULT_CAPACITY = 10;
  private int size;
  private Object[] elementData;

  public ObjectList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
  }
  public ObjectList(int initailCapacity) {
    elementData = new Object[initailCapacity];
  }

  public void add(Object e) {
    if (size == elementData.length) {
      grow();
    }
    elementData[size++] = e;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = elementData[i];
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
   * @return 목록에 저장된 항목
   * @throws ListException 인덱스가 무효함
   */
  public Object get(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      // 인덱스가 무효하면 예외를 발생시킨다.
      // 즉 예외 정보를 객체에 담아서 호출한 쪽으로 던진다.
      // 예외 정보는 던질 수 있는 객체에 담아야 한다.
      // 던질 수 있는 객체? java.lang.Throwable 객체이다.
      // 단, 메서드 선언부에 어떤 예외를 던지는지 표시해야 한다.
      throw new ListException("인덱스가 무효함!");
    }
    return elementData[index];
  }

  // 예외를 보고하는 메서드인 경우
  // 메서드 선언부에 어떤 예외를 보고하는지 표시해야 한다.
  public boolean remove(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      // 인덱스가 무효할 때 false를 리턴하는 대신
      // 예외 정보를 호출자에게 던진다.
      // 예외 상황을 호출자에게 보고한다.
      throw new ListException("인덱스가 무효합니다");
    }

    for (int i = index + 1; i < size; i++) {
      elementData[i - 1] = elementData[i];
    }

    elementData[--size] = null;

    return true;
  }

  public int size() {
    return size;
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
