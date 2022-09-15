package com.bitcamp.client.board.handler;

import java.util.List;
import com.bitcamp.client.board.dao.MariaDBBoardDao;
import com.bitcamp.client.handler.AbstractHandler111;
import com.bitcamp.client.util.Prompt111;
import com.bitcamp.common.board.domain.Board111;

public class BoardHandler111 extends AbstractHandler111 {

  private MariaDBBoardDao boardDao;

  public BoardHandler111() {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});

    boardDao = new MariaDBBoardDao();
  }


  @Override
  public void service(int menuNo) {
    try {
      switch (menuNo) {
        case 1: onList(); break;
        case 2: onDetail(); break;
        case 3: onInput(); break;
        case 4: onDelete(); break;
        case 5: onUpdate(); break;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void onList() throws Exception{
    List<Board111> boards = boardDao.findAll();

    System.out.println("번호 제목 조회수 작성자 등록일");
    for (Board111 board : boards) {
      System.out.printf("%d\t%s\t%d\t%d\t%s\n", 
          board.no, board.title, board.viewCount, board.memberNo, board.createdDate);
    }
  }

  private void onDetail() throws Exception{
    int boardNo = 0;
    while (true) {
      try {
        boardNo = Prompt111.inputInt("조회할 게시글 번호? ");
        break;
      } catch (Exception ex) {
        System.out.println("입력 값이 옳지 않습니다!");
      }
    }

    Board111 board = boardDao.findByNo(boardNo);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    System.out.printf("번호: %d\n", board.no);
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("조회수: %d\n", board.viewCount);
    System.out.printf("작성자: %s\n", board.memberNo);
    System.out.printf("등록일: %s\n", board.createdDate);
  }

  private void onInput() throws Exception{
    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.memberNo = Prompt111.inputInt("작성자? ");

    boardDao.insert(board);
    System.out.println("게시글을 등록했습니다.");
  }

  private void onDelete() throws Exception{
    int boardNo = 0;
    while (true) {
      try {
        boardNo = Prompt111.inputInt("삭제할 게시글 번호? ");
        break;
      } catch (Exception ex) {
        System.out.println("입력 값이 옳지 않습니다!");
      }
    }

    if (boardDao.delete(boardNo) == 1) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 게시글이 없습니다!");
    }
  }

  private void onUpdate() throws Exception{
    int boardNo = 0;
    while(true) {
      try {
        boardNo = Prompt111.inputInt("변경할 게시글 번호? ");
        break;
      } catch(Exception ex) {
        System.out.println("입력 값이 옳지 않습니다!");
      }
    }
    Board111 board = boardDao.findByNo(boardNo);
    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    board.title = Prompt111.inputString("제목?(" + board.title +")");
    board.content = Prompt111.inputString("내용?(" + board.content +")");

    String input = Prompt111.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      if (boardDao.update(board) == 1) {
        System.out.println("변경했습니다.");
      } else {
        System.out.println("변경 실패입니다!");
      }
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
