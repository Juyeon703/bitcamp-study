package com.bitcamp.util;

public class ObjectList111 extends AbstractList111{

  private static final int DEFAULT_CAPACITY = 10;
  private Object[] elementData;

  public ObjectList111() {
    elementData = new Object[DEFAULT_CAPACITY];
  }

  @Override
  public Object[] toArray() { 
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = elementData[i];
    }
    return arr;
  }

  @Override
  public Object get(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스가 무효함!");
    }
    return elementData[index];
  }

  @Override
  public void add(Object e) {
    if (size == elementData.length) {
      grow();
    }
    elementData[size++] = e;
  }

  @Override
  public Object remove(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스가 무효합니다!");
    }
    Object deleted = elementData[index];
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

