package com.bitcamp.board.domain;

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
}
