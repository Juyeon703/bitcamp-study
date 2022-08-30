package com.bitcamp.client.board.domain;

import java.io.Serializable;

public class Board111 implements Serializable{

  private static final long serialVersionUID = 1L;

  public int no;
  public String title;
  public String content;
  public String writer;
  public String password; 
  public int viewCount;
  public long createdDate;

  @Override
  public String toString() {
    return "Board111 [no=" + no + ", title=" + title + ", content=" + content + ", writer=" + writer
        + ", password=" + password + ", viewCount=" + viewCount 
        + ", createdDate=" + new java.sql.Date(createdDate)
        + "]";
  }

  public static Board111 create(String csv) {
    String[] values = csv.split(",");

    Board111 board = new Board111();
    board.no = Integer.parseInt(values[0]);
    board.title = values[1];
    board.content = values[2];
    board.writer = values[3];
    board.password = values[4];
    board.viewCount = Integer.parseInt(values[5]);
    board.createdDate = Long.parseLong(values[6]);

    return board;
  }

  public String toCsv() {
    return String.format("%d,%s,%s,%s,%s,%d,%d\n",
        no,
        title,
        content,
        writer,
        password,
        viewCount,
        createdDate);
  }
}
