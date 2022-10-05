package com.bitcamp.board.service;

import java.util.List;

import com.bitcamp.board.domain.Member;

// 비지니스 로직을 수행하는 객체의 사용규칙(호출규칙)
public interface MemberService {

  public void add(Member member) throws Exception;

  public boolean update(Member member) throws Exception;

  public Member get(int no) throws Exception;

  public Member get(String email, String password) throws Exception;

  public boolean delete(int no) throws Exception;

  public List<Member> list() throws Exception;
}

