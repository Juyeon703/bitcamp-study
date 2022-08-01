package com.bitcamp.util;

public class ObjectList {
  private static final int DEFAULT_CAPACITY = 10;
  private int size;
  private Object[] elementData;

  public ObjectList() {
    elementData = new Object[DEFAULT_CAPACITY];
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = elementData[i];
    }
    return arr;
  }

  public Object get(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    return elementData[index];
  }

  public void add(Object e) {
    if (size == elementData.length) {
      grow();
    }
    elementData[size++] = e;
  }

  public boolean remove(int index) {
    if (index < 0 || index >= size) {
      return false;
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

