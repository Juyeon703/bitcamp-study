package com.bitcamp.board.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.bitcamp.board.dao.BoardDao111;
import com.bitcamp.board.domain.Board111;
import com.bitcamp.handler.AbstractHandler;
import com.bitcamp.util.Prompt111;

public class BoardHandler111 extends AbstractHandler {

  private BoardDao111 boardDao = new BoardDao111();

  public BoardHandler111() {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});
  }

  @Override
  public void service(int menuNo) {
    switch (menuNo) {
      case 1: onList(); break;
      case 2: onDetail(); break;
      case 3: onInput(); break;
      case 4: onDelete(); break;
      case 5: onUpdate(); break;
    }
  }

  private void onList() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    System.out.println("번호 제목 조회수 작성자 등록일");

    Board111[] boards = boardDao.findAll();

    for (Board111 board : boards) {
      Date date = new Date(board.createdDate);
      String dateStr = formatter.format(date); 
      System.out.printf("%d\t%s\t%d\t%s\t%s\n", board.no, board.title, board.viewCount, board.writer, dateStr);
    }
  }

  private void onDetail() {
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
    System.out.printf("작성자: %s\n", board.writer);
    Date date = new Date(board.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }

  private void onInput() {
    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.writer = Prompt111.inputString("작성자? ");
    board.password = Prompt111.inputString("암호? ");
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();
    boardDao.insert(board);
    System.out.println("게시글을 등록했습니다.");
  }

  private void onDelete() {
    int boardNo = 0;
    while (true) {
      try {
        boardNo = Prompt111.inputInt("삭제할 게시글 번호? ");
        break;
      } catch (Exception ex) {
        System.out.println("입력 값이 옳지 않습니다!");
      }
    }
    if (boardDao.delete(boardNo)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 게시글이 없습니다!");
    }
  }

  private void onUpdate() {
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
    String newTitle = Prompt111.inputString("제목?(" + board.title +")");
    String newContent = Prompt111.inputString("내용?(" + board.content +")");
    String input = Prompt111.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      board.title = newTitle;
      board.content = newContent;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
