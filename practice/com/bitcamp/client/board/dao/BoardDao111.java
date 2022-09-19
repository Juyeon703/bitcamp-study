package com.bitcamp.client.board.dao;

import java.util.List;
import com.bitcamp.common.board.domain.Board111;

public interface BoardDao111 {
  int insert(Board111 board) throws Exception;

  Board111 findByNo(int no) throws Exception;

  int update(Board111 board) throws Exception;

  int delete(int no) throws Exception;

  List<Board111> findAll() throws Exception;
}
