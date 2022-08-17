package com.bitcamp.util;

public class Stack111 extends LinkedList111{
  public void push(Object value) {
    add(value);
  }

  public Object pop() {
    return remove(size()-1);
  }

  public boolean empty() {
    return size() == 0;
  }

  public Object peek() {
    return get(size() - 1);
  }
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < size(); i++) {
      if (buf.length() > 0) {
        buf.append(" > ");
      }
      buf.append(get(i));
    }
    return buf.toString();
  }
}
