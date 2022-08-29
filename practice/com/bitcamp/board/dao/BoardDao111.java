package com.bitcamp.board.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.board.domain.Board111;


public class BoardDao111 {

  List<Board111> list = new LinkedList<>();

  private int boardNo = 0;
  String filename;

  public BoardDao111(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      int size = in.readInt();
      for (int i = 0; i < size; i++) {
        Board111 board = (Board111) in.readObject();
        list.add(board);
        boardNo = board.no;
      }
    }
  }

  public void save() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeInt(list.size());
      for (Board111 board : list) {
        out.writeObject(board);
      }
    }
  }

  public void insert(Board111 board) {
    board.no = nextNo();
    list.add(board);
  }

  public Board111 findByNo(int boardNo) {
    for (int i = 0; i < list.size(); i++) {
      Board111 board = list.get(i);
      if (board.no == boardNo) {
        return board;
      }
    }
    return null;
  }


  public boolean delete(int boardNo) {
    for (int i = 0; i < list.size(); i++) {
      Board111 board = list.get(i);
      if (board.no == boardNo) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Board111[] findAll() {
    Iterator<Board111> iterator = list.iterator();
    Board111[] arr = new Board111[list.size()];
    int index = list.size() - 1;
    while (iterator.hasNext()) {
      arr[index--] = iterator.next();
    }
    return arr;
  }

  private int nextNo() {
    return ++boardNo;
  }
}
