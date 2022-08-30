package com.bitcamp.server.board.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.server.board.domain.Member111;

public class MemberDao111 {

  List<Member111> list = new LinkedList<Member111>();
  String filename;

  public MemberDao111(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception {
    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
      StringBuilder strBuilder = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }
      Member111[] arr = new Gson().fromJson(strBuilder.toString(), Member111[].class);
      for (int i = 0; i < arr.length; i++) {
        list.add(arr[i]);
      }
    } 
  }

  public void save() throws Exception {
    try (FileWriter out = new FileWriter(filename)) {
      Member111[] members = list.toArray(new Member111[0]);
      out.write(new Gson().toJson(members));
    }
  }

  public void insert(Member111 member) {
    list.add(member);
  }

  public Member111 findByEmail(String email) {
    for(int i = 0; i < list.size(); i++) {
      Member111 member = list.get(i);
      if (member.email.equals(email)) { 
        return member;
      }
    }
    return null;
  }

  public boolean delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member111 member = list.get(i);
      if (member.email.equals(email)) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Member111[] findAll() {
    Iterator<Member111> iterator = list.iterator();
    Member111[] arr = new Member111[list.size()];
    int i = 0;
    while (iterator.hasNext()) {
      arr[i++] = iterator.next(); 
    }
    return arr;
  }
}
