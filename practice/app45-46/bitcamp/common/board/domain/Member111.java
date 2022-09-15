package com.bitcamp.common.board.domain;

import java.sql.Date;

public class Member111 {

  public int no;
  public String name;
  public String email;
  public String password;
  public Date createdDate;

  @Override
  public String toString() {
    return "Member111 [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password
        + ", createdDate=" + createdDate + "]";
  } 
}
