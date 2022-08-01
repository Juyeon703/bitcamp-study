package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board111;
import com.bitcamp.util.ObjectList;

public class BoardList111 extends ObjectList{

  private int boardNo = 0;

  @Override
  public void add(Object e) {
    Board111 board = (Board111) e;
    board.no = nextNo();
    super.add(e);
  }

  @Override
  public Board111 get(int boardNo) {
    for (int i = 0; i < size(); i++) {
      Board111 board = (Board111) super.get(i);
      if (board.no == boardNo) {
        return board;
      }
    }
    return null;
  }

  @Override
  public boolean remove(int boardNo) {
    for (int i = 0; i < size(); i++) {
      Board111 board = (Board111) super.get(i);
      if (board.no == boardNo) {
        return super.remove(i);
      }
    }
    return false;
  }

  private int nextNo() {
    return ++boardNo;
  }
}
