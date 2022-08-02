package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board111;
import com.bitcamp.util.LinkedList111;
import com.bitcamp.util.List111;

public class BoardDao111 {

  List111 list = new LinkedList111();

  private int boardNo = 0;

  public void insert(Object e) {
    Board111 board = (Board111) e;
    board.no = nextNo();
    list.add(e);
  }

  public Board111 findByNo(int boardNo) {
    for (int i = 0; i < list.size(); i++) {
      Board111 board = (Board111) list.get(i);
      if (board.no == boardNo) {
        return board;
      }
    }
    return null;
  }


  public boolean delete(int boardNo) {
    for (int i = 0; i < list.size(); i++) {
      Board111 board = (Board111) list.get(i);
      if (board.no == boardNo) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Board111[] findAll() {
    Object[] arr = list.toArray();

    Board111[] boards = new Board111[arr.length];

    for (int i = 0; i < arr.length; i++) {
      boards[i] = (Board111) arr[i];
    }

    return boards;
  }

  private int nextNo() {
    return ++boardNo;
  }
}
