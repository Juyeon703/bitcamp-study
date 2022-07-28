package com.bitcamp.board;

public class BoardHandler {

  static int boardCount = 0;

  static final int SIZE = 3;
  static Board[] boards = new Board[SIZE];

  static void processList() {
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    System.out.println("[게시글 목록]");
    System.out.println("번호 제목 조회수 작성자 등록일");
    for (int i = 0; i < boardCount; i++) {
      Board board = boards[i];
      java.util.Date date = new java.util.Date(board.createdDate);
      String dateStr = formatter.format(date); 
      System.out.printf("%d\t%s\t%d\t%s\t%s\n", board.no, board.title, board.viewCount, board.writer, dateStr);
    }
  }

  static void processDetail() {
    System.out.println("[게시글 상세보기]");
    int boardNo = Prompt.inputInt("조회할 게시글 번호? ");
    Board board = null;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        board = boards[i];
        break;
      }
    }

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    System.out.printf("번호: %d\n", board.no);
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("조회수: %d\n", board.viewCount);
    System.out.printf("작성자: %s\n", board.writer);
    java.util.Date date = new java.util.Date(board.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }

  static void processInput() {
    System.out.println("[게시글 등록]");
    if (boardCount == SIZE) {
      System.out.println("게시글을 더이상 저장할 수 없습니다.");
      return;
    }

    Board board = new Board();
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    board.password = Prompt.inputString("암호? ");
    board.no = boardCount == 0 ? 1 : boards[boardCount - 1].no + 1;
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();

    boards[boardCount] = board;
    boardCount++;

    System.out.println("게시글을 등록했습니다.");
  }
  static void processDelete() {
    System.out.println("[게시글 삭제]");
    int boardNo = Prompt.inputInt("삭제할 게시글 번호? ");
    int boardIndex = -1;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        boardIndex = i;
        break;
      }
    }

    if (boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    for (int i = boardIndex + 1; i < boardCount; i++) {
      boards[i - 1] = boards[i];
    }
    boards[--boardCount] = null;
    System.out.println("삭제하였습니다.");
  }

  static void processUpdate() {
    System.out.println("[게시글 변경]");
    int boardNo = Prompt.inputInt("변경할 게시글 번호? ");
    Board board = null;
    for (int i = 0; i < boardCount; i++) {
      if (boards[i].no == boardNo) {
        board = boards[i];
        break;
      }
    }

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }
    String newTitle = Prompt.inputString("제목?(" + board.title +")");
    String newContent = Prompt.inputString("내용?(" + board.content +")");
    String input = Prompt.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      board.title = newTitle;
      board.content = newContent;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}
