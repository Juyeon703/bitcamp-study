package com.bitcamp.board;

public class BoardTest {
  public static void main(String[] args) {
    Board111 b1;
    Board111 b2;

    b1 = new Board111();
    b1.no = 1;

    b2 = b1;
    b2.no = 100;

    b1 = new Board111();
    b2 = b1;

    System.out.println(b1.no);
  }
}
