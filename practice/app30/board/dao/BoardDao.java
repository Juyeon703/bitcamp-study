package com.bitcamp.board.dao;

import com.bitcamp.board.domain.Board;
import com.bitcamp.util.LinkedList;
import com.bitcamp.util.List;

// 게시글 목록을 관리하는 역할
//
public class BoardDao {

  // List 인터페이스의 레퍼런스인 list 변수는
  // List 규격에 따라 만든 객체 주소를 담을 수 있다.
  List<Board> list = new LinkedList<>();

  private int boardNo = 0;

  // BoardDao 에서 제공할 메서드를 정의하고,
  // 이 메서드가 호출되면 ObjectList의 도움을 받아 처리한다.
  public void insert(Board board) {
    board.no = nextNo();
    list.add(board);
  }

  public Board findByNo(int boardNo) {
    // 의존 객체 BoardList를 이용하여 기존에 저장된 게시글 목록 중에 
    // 해당 번호의 게시글을 찾는다.
    for (int i = 0; i < list.size(); i++) {
      Board board = list.get(i);
      if (board.no == boardNo) {
        return board;
      }
    }

    return null;
  }

  public boolean delete(int boardNo) {
    // 의존 객체 ObjectList을 이용하여 목록에 저장된 게시글을 찾아 삭제한다.
    for (int i = 0; i < list.size(); i++) {
      Board board = list.get(i);
      if (board.no == boardNo) {
        return list.remove(i) != null;
      }
    }

    return false;
  }

  public Board[] findAll() {
    return list.toArray(new Board[0]);
  }

  private int nextNo() {
    return ++boardNo;
  }
}














