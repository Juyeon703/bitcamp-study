package com.bitcamp.server.board.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.common.board.domain.Board111;
import com.google.gson.Gson;


public class BoardDao111 {

  List<Board111> list = new LinkedList<>();

  private int boardNo = 0;
  String filename;

  public BoardDao111(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception {
    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
      StringBuilder strBuilder = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }
      Board111[] arr = new Gson().fromJson(strBuilder.toString(), Board111[].class);
      for (int i = 0; i < arr.length; i++) {
        list.add(arr[i]);
      }
      boardNo = arr[arr.length - 1].no;
    }
  }

  public void save() throws Exception {
    try (FileWriter out = new FileWriter(filename)) {
      Board111[] boards = list.toArray(new Board111[0]);
      out.write(new Gson().toJson(boards));
    }
  }

  public void insert(Board111 board) {
    board.no = nextNo();
    list.add(board);
  }

  public boolean update(Board111 board) {
    for (int i = 0; i < list.size(); i++) {
      Board111 b = list.get(i);
      if (b.no == board.no) {
        list.set(i, board);
        return true;
      }
    }
    return false;
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
