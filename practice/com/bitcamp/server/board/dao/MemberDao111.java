package com.bitcamp.server.board.dao;

import java.util.List;
import com.bitcamp.common.board.domain.Member111;

public interface MemberDao111 {
  int insert(Member111 member) throws Exception;

  Member111 findByNo(int no) throws Exception;

  int update(Member111 member) throws Exception;

  int delete(int no) throws Exception;

  List<Member111> findAll() throws Exception;
}
