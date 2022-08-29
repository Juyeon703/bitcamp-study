package com.bitcamp.board.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.board.domain.Member111;

public class MemberDao111 {

  List<Member111> list = new LinkedList<Member111>();
  String filename;

  public MemberDao111(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Member111 member = (Member111) in.readObject();
        list.add(member);
      }
    } 
  }

  public void save() throws Exception {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
      out.writeInt(list.size());

      for (Member111 member : list) {
        out.writeObject(member);
      }
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
