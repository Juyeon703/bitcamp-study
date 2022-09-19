package com.bitcamp.common.board.domain;

import java.sql.Date;

public class Board222 {

  public int no;
  public String title;
  public String content;
  public int memberNo;
  public String password; 
  public int viewCount;
  public Date createdDate;

  @Override
  public String toString() {
    return "Board111 [no=" + no + ", title=" + title + ", content=" + content + ", memberNo="
        + memberNo + ", password=" + password + ", viewCount=" + viewCount + ", createdDate="
        + createdDate + "]";
  }
}
