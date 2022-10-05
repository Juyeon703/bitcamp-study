package com.bitcamp.board.service;

import java.util.List;

import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;

// 비지니스 로직을 수행하는 객체의 사용규칙(호출규칙)
public interface BoardService {

  public void add(Board board) throws Exception;

  public boolean update(Board board) throws Exception;

  public Board get(int no) throws Exception;

  public boolean delete(int no) throws Exception;

  public List<Board> list() throws Exception;

  public AttachedFile getAttachedFile(int fileNo) throws Exception;

  public boolean deleteAttachedFile(int fileNo) throws Exception;
}

