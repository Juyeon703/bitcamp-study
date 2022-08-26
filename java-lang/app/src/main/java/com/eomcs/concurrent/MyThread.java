package com.eomcs.concurrent;

public class MyThread extends Thread{
  // 2. count 값을 담을 메모리 준비
  int count;
  // 1. count 값을 받기위해서 생성자 추가
  public MyThread(int count) {
    this.count = count;
  }

  @Override
  public void run() {
    for (int i = 0; i < count; i++) {
      System.out.println("==> " + i);
    }
  }
}
