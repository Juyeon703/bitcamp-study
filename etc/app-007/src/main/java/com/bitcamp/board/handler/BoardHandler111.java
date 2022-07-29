package com.bitcamp.board.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.bitcamp.board.dao.BoardList111;
import com.bitcamp.board.domain.Board111;
import com.bitcamp.util.Prompt111;

public class BoardHandler111 {

  private String title;

  private BoardList111 boardList = new BoardList111();

  public BoardHandler111() {
    title = "게시판";
  }

  public BoardHandler111(String title) {
    this.title = title;
  }

  public void execute() {
    while (true) {
      System.out.printf("%s\n: ", title);
      System.out.println("  1: 목록");
      System.out.println("  2: 상세보기");
      System.out.println("  3: 등록");
      System.out.println("  4: 삭제");
      System.out.println("  5: 변경");
      System.out.println();
      int menuNo = Prompt111.inputInt("메뉴를 선택하세요[1..5](0: 이전) ");
      System.out.println("---------------------------------------------");
      switch (menuNo) {
        case 0: return;
        case 1: onList(); break;
        case 2: onDetail(); break;
        case 3: onInput(); break;
        case 4: onDelete(); break;
        case 5: onUpdate(); break;
        default: System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    }
  }

  private void onList() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    System.out.printf("[%s 목록]\n", title);
    System.out.println("번호 제목 조회수 작성자 등록일");

    Board111[] list = boardList.toArray();

    for (Board111 board : list) {
      Date date = new Date(board.createdDate);
      String dateStr = formatter.format(date); 
      System.out.printf("%d\t%s\t%d\t%s\t%s\n", board.no, board.title, board.viewCount, board.writer, dateStr);
    }
  }

  private void onDetail() {
    System.out.printf("[%s 상세보기]\n", title);
    int boardNo = Prompt111.inputInt("조회할 게시글 번호? ");
    Board111 board = boardList.get(boardNo);
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
    System.out.printf("[%s 등록]\n", title);
    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.writer = Prompt111.inputString("작성자? ");
    board.password = Prompt111.inputString("암호? ");
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();
    boardList.add(board);
    System.out.println("게시글을 등록했습니다.");
  }

  private void onDelete() {
    System.out.printf("[%s 삭제]\n", title);
    int boardNo = Prompt111.inputInt("삭제할 게시글 번호? ");

    if (boardList.remove(boardNo)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 게시글이 없습니다!");
    }
  }

  private void onUpdate() {
    System.out.printf("[%s 변경]\n", title);
    int boardNo = Prompt111.inputInt("변경할 게시글 번호? ");
    Board111 board = boardList.get(boardNo);
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
