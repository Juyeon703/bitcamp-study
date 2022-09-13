package com.bitcamp.board.dao;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.board.domain.Member;
import com.google.gson.Gson;

// 회원 목록을 관리하는 역할
//
public class MemberDao {

  // MemberDao는 List 규격에 맞춰 새안한 객체를 사용할 것이다.
  // => ObjectList 클래스는 List 규격에 맞춰 메서드를 정의한 클래스이다.
  // => 따라서 List 레퍼런스 변수에 그 주소를 저장할 수 있다.
  List<Member> list = new LinkedList<Member>();

  String filename;

  public MemberDao(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception {
    // 이때 try는 close() 자동으로 호출하기 위함
    try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
      StringBuilder strBuilder = new StringBuilder();
      String str;
      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }
      Member[] arr = new Gson().fromJson(strBuilder.toString(), Member[].class);
      for (int i = 0; i < arr.length; i++) {
        list.add(arr[i]);
      }
    } // try() ==> try 블록을 벗어나기 전에 in.close()가 자동으로 실행된다.
  }
  public void save() throws Exception {
    try(FileWriter out = new FileWriter(filename)) {
      Member[] members = list.toArray(new Member[0]);
      out.write(new Gson().toJson(members));
    } // try() ==> try 블록을 벗어나기 전에 out.close()가 자동으로 실행됨.
  }

  public void insert(Member member) {
    list.add(member);
  }

  public boolean update(Member member) {
    for (int i = 0; i < list.size(); i++) {
      Member m = list.get(i);
      if (m.email.equals(member.email)) {
        list.set(i, member);
        return true;
      }
    }
    return false;
  }
  // MemberList 에서 MemberDao 로 바꿔는 것에 맞춰
  // 메서드의 이름도 데이터에 초점을 맞춰 변경한다.
  //
  public Member findByEmail(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member member = list.get(i);
      if (member.email.equals(email)) {
        return member;
      }
    }
    return null;
  }

  public boolean delete(String email) {
    for (int i = 0; i < list.size(); i++) {
      Member member = list.get(i);
      if (member.email.equals(email)) {
        return list.remove(i) != null;
      }
    }
    return false;
  }

  public Member[] findAll() {
    Iterator<Member> iterator = list.iterator();

    Member[] arr = new Member[list.size()];

    int i = 0;
    while (iterator.hasNext()) {
      arr[i++] = iterator.next(); 
    }
    return arr;
  }
}














