package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board111;

public class BoardList111 {
  private static final int DEFAULT_SIZE = 3;
  private int boardCount;
  private Board111[] boards;
  private int no = 0;

  public BoardList111() {
    boards = new Board111[DEFAULT_SIZE];
  }

  public Board111[] toArray() {
    Board111[] arr = new Board111[boardCount];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = boards[i];
    }
    return arr;
  }

  public Board111 get(int boardNo) {
    for(int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        return boards[i];
      }
    }
    return null;
  }

  public void add(Board111 board) {
    if (boardCount == boards.length) {
      grow();
    }
    board.no = nextNo();
    boards[boardCount++] = board;
  }

  public boolean remove(int boardNo) {
    int boardIndex = -1;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        boardIndex = i;
        break;
      }
    }
    if (boardIndex == -1) {
      return false;
    }
    for (int i = boardIndex + 1; i < boardCount; i++) {
      boards[i - 1] = boards[i];
    }
    boards[--boardCount] = null;
    return true;
  }

  private void grow() {
    int newSize = boards.length + (boards.length >> 1);
    Board111[] newArray = new Board111[newSize];
    for (int i = 0; i < boards.length; i++) {
      newArray[i] = boards[i];
    }
    boards = newArray;
  }

  private int nextNo() {
    return ++no;
  }
}
