package com.bitcamp.util;

public class LinkedList111 extends AbstractList111 {

  private Node head;
  private Node tail;

  @Override
  public void add(Object value) {
    Node node = new Node(value);

    size++;

    if (tail == null) {
      head = tail = node;
      return;
    }

    tail.next = node;
    node.prev = tail;

    tail = node;
  }

  @Override
  public Object get(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스의 범위를 초과했습니다!");
    }

    Node cursor = head;

    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    return cursor.value;
  }

  @Override
  public Object remove(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스의 범위를 초과했습니다!");
    }
    size--;
    Object deleted;

    if(head == tail) {
      deleted = head.value;
      head.value = null;
      head = tail = null;
      return deleted;
    }

    Node cursor = head;

    for (int i = 0; i < index; i++) {
      cursor = cursor.next;
    }

    if (cursor.prev != null) {
      cursor.prev.next = cursor.next;
    } else {
      head = cursor.next;
      head.prev = null;
    }

    if (cursor.next != null) {
      cursor.next.prev = cursor.prev;
    } else {
      tail = cursor.prev;
      tail.next = null;
    }

    deleted = cursor.value;
    cursor.value = null;
    cursor.prev = null;
    cursor.next = null;

    return deleted; 
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[size];

    Node cursor = head;
    for (int i = 0; i < size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }
    return arr;
  }

  private static class Node {
    Object value;
    Node prev;
    Node next;

    public Node(Object v) {
      value = v;
    }
  }
}
