package com.bitcamp.board.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    try (FileInputStream in = new FileInputStream(filename)) {

      int size = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();

      for (int i = 0; i < size; i++) {
        Member111 member = new Member111();
        member.no = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();

        int len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
        byte[] bytes = new byte[len];
        in.read(bytes);
        member.name = new String(bytes, "UTF-8");

        len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
        bytes = new byte[len];
        in.read(bytes);
        member.email = new String(bytes, "UTF-8");

        len = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
        bytes = new byte[len];
        in.read(bytes);
        member.password = new String(bytes, "UTF-8");

        member.createdDate = 
            (((long)in.read()) << 56) + 
            (((long)in.read()) << 48) +
            (((long)in.read()) << 40) +
            (((long)in.read()) << 32) +
            (((long)in.read()) << 24) +
            (((long)in.read()) << 16) +
            (((long)in.read()) << 8) +
            ((in.read()));

        list.add(member);
      }
    } 
  }

  public void save() throws Exception {
    try (FileOutputStream out = new FileOutputStream(filename)) {

      out.write(list.size() >> 24);  
      out.write(list.size() >> 16);
      out.write(list.size() >> 8);
      out.write(list.size());

      for (Member111 member : list) {
        out.write(member.no >> 24);  
        out.write(member.no >> 16);
        out.write(member.no >> 8);
        out.write(member.no);

        byte[] bytes = member.name.getBytes("UTF-8"); 
        out.write(bytes.length >> 24);
        out.write(bytes.length >> 16);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.email.getBytes("UTF-8"); 
        out.write(bytes.length >> 24);
        out.write(bytes.length >> 16);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.password.getBytes("UTF-8"); 
        out.write(bytes.length >> 24);
        out.write(bytes.length >> 16);
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        out.write((int)(member.createdDate >> 56));
        out.write((int)(member.createdDate >> 48));
        out.write((int)(member.createdDate >> 40));
        out.write((int)(member.createdDate >> 32));
        out.write((int)(member.createdDate >> 24));
        out.write((int)(member.createdDate >> 16));
        out.write((int)(member.createdDate >> 8));
        out.write((int)(member.createdDate));
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
