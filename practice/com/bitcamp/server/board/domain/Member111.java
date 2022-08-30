package com.bitcamp.server.board.domain;

import java.io.Serializable;

public class Member111 implements Serializable{

  private static final long serialVersionUID = 1L;

  public int no;
  public String name;
  public String email;
  public String password;
  public long createdDate; 

  public static Member111 create(String csv) {
    String[] values = csv.split(",");

    Member111 member = new Member111();
    member.no = Integer.parseInt(values[0]);
    member.name = values[1];
    member.email = values[2];
    member.password = values[3];
    member.createdDate = Long.parseLong(values[4]);

    return member;
  }

  public String toCsv() {
    return String.format("%d,%s,%s,%s,%d",
        no,
        name,
        email,
        password,
        createdDate);
  }
}
