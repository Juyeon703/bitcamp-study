package com.bitcamp.util;

public class Stack111 extends LinkedList111{
  public void push(Object value) {
    add(value);
  }

  public Object pop() {
    return remove(size()-1);
  }

  public boolean empty() {}
  public Object peek() {}
  @Override
  public String toString() {}

}
