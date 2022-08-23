package com.bitcamp.board.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    FileInputStream in = new FileInputStream(filename);
    int size = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
    for (int i = 0; i < size; i++) {
      Board111 board = new Board111();

      int value = 0;
      value += in.read() << 24; 
      value += in.read() << 16; 
      value += in.read() << 8;  
      value += in.read();      
      board.no = value;


      int len = 0;
      len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
      byte[] bytes = new byte[len];
      in.read(bytes);
      board.title = new String(bytes, "UTF-8");

      len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.content = new String(bytes, "UTF-8");

      len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.writer = new String(bytes, "UTF-8");

      len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.password = new String(bytes, "UTF-8");

      board.viewCount = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();

      board.createdDate = 
          (((long)in.read()) << 56) + 
          (((long)in.read()) << 48) +
          (((long)in.read()) << 40) +
          (((long)in.read()) << 32) +
          (((long)in.read()) << 24) +
          (((long)in.read()) << 16) +
          (((long)in.read()) << 8) +
          ((in.read()));

      list.add(board);

      boardNo = board.no;
    }
    in.close();
  }

  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(filename);

    out.write(list.size() >> 24);  
    out.write(list.size() >> 16);
    out.write(list.size() >> 8);
    out.write(list.size());

    for (Board111 board : list) {
      out.write(board.no >> 24);
      out.write(board.no >> 16);
      out.write(board.no >> 8);
      out.write(board.no); 

      byte[] bytes = board.title.getBytes("UTF-8"); 
      out.write(bytes.length >> 24);
      out.write(bytes.length >> 16);
      out.write(bytes.length >> 8);
      out.write(bytes.length);
      out.write(bytes);

      bytes = board.content.getBytes("UTF-8"); 
      out.write(bytes.length >> 24);
      out.write(bytes.length >> 16);
      out.write(bytes.length >> 8);
      out.write(bytes.length);
      out.write(bytes);

      bytes = board.writer.getBytes("UTF-8"); 
      out.write(bytes.length >> 24);
      out.write(bytes.length >> 16);
      out.write(bytes.length >> 8);
      out.write(bytes.length);
      out.write(bytes);

      bytes = board.password.getBytes("UTF-8"); 
      out.write(bytes.length >> 24);
      out.write(bytes.length >> 16);
      out.write(bytes.length >> 8);
      out.write(bytes.length);
      out.write(bytes);

      out.write(board.viewCount >> 24);
      out.write(board.viewCount >> 16);
      out.write(board.viewCount >> 8);
      out.write(board.viewCount);

      out.write((int)(board.createdDate >> 56));
      out.write((int)(board.createdDate >> 48));
      out.write((int)(board.createdDate >> 40));
      out.write((int)(board.createdDate >> 32));
      out.write((int)(board.createdDate >> 24));
      out.write((int)(board.createdDate >> 16));
      out.write((int)(board.createdDate >> 8));
      out.write((int)(board.createdDate));
    }
    out.close();
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
