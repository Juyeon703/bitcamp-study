/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;
import com.bitcamp.handler.AbstractHandler;
import com.bitcamp.util.Prompt;

public class BoardHandler extends AbstractHandler{

  private BoardDao boardDao;

  public BoardHandler(BoardDao boardDao) {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});
    this.boardDao = boardDao;
  }

  // 템플릿 메서드 패턴(template method pattern): 
  // - 수퍼클래스의 execute()에서 동작의 전체적인 흐름을 정의하고(틀을 만들고),
  // - 서브클래스의 service()에서 동작을 구체적으로 정의한다.(세부적인 항목을 구현)
  @Override
  public void service(int menuNo, DataInputStream in, DataOutputStream out) {
    try {
      switch (menuNo) {
        // case 0: 
        // //핸들러를 종료할 때 breadcrumb 메뉴에 등록된 이 핸들러의 이름을 꺼낸다.
        // // App.breadcrumbMenu.pop();
        // return;
        case 1: this.onList(in, out); break;
        case 2: this.onDetail(in, out); break;
        case 3: this.onInput(in, out); break;
        case 4: this.onDelete(in, out); break;
        case 5: this.onUpdate(in, out); break;
        // default: System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void onList(DataInputStream in, DataOutputStream out) throws Exception{
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {
      List<Board> boards = boardDao.findAll();

      tempOut.println("번호 제목 조회수 작성자 등록일");
      for (Board board : boards) {
        tempOut.printf("%d\t%s\t%d\t%d\t%s\n",
            board.no, board.title, board.viewCount, board.memberNo, board.createdDate);
      }
      out.writeUTF(strOut.toString());
    }
  }

  private void onDetail(DataInputStream in, DataOutputStream out) throws Exception{

    Prompt prompt = new Prompt(in, out);

    int boardNo = 0;
    while (true) {
      try {
        boardNo = prompt.inputInt("조회할 게시글 번호? ");
        break;
      } catch (Exception ex) {
        out.writeUTF("입력 값이 옳지 않습니다!");
      }
    }
    Board board = boardDao.findByNo(boardNo);

    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {

      if (board == null) {
        tempOut.println("해당 번호의 게시글이 없습니다!");
        out.writeUTF(strOut.toString());
        return;
      }
      tempOut.printf("번호: %d\n", board.no);
      tempOut.printf("제목: %s\n", board.title);
      tempOut.printf("내용: %s\n", board.content);
      tempOut.printf("조회수: %d\n", board.viewCount);
      tempOut.printf("작성자: %d\n", board.memberNo);
      tempOut.printf("등록일: %s\n", board.createdDate);
      out.writeUTF(strOut.toString());
    }
  }


  private void onInput(DataInputStream in, DataOutputStream out) throws Exception{
    Prompt prompt = new Prompt(in, out);
    Board board = new Board();

    board.title = prompt.inputString("제목? ");
    board.content = prompt.inputString("내용? ");
    board.memberNo = prompt.inputInt("작성자? ");

    boardDao.insert(board);
    out.writeUTF("게시글을 등록했습니다.");
  }

  private void onDelete(DataInputStream in, DataOutputStream out) throws Exception{
    Prompt prompt = new Prompt(in, out);
    int boardNo = 0;
    while (true) {
      try {
        boardNo = prompt.inputInt("삭제할 게시글 번호? ");
        break;
      } catch (Exception ex) {
        out.writeUTF("입력 값이 옳지 않습니다!");
      }
    }

    if (boardDao.delete(boardNo) == 1) {
      out.writeUTF("삭제하였습니다.");
    } else {
      out.writeUTF("해당 번호의 게시글이 없습니다!");
    }
  }


  private void onUpdate(DataInputStream in, DataOutputStream out) throws Exception{
    Prompt prompt = new Prompt(in, out);
    int boardNo = 0;
    while (true) {
      try {
        boardNo = prompt.inputInt("변경할 게시글 번호? ");
        break;
      } catch (Throwable ex) {
        out.writeUTF("입력 값이 옳지 않습니다!");
      }
    }

    Board board = boardDao.findByNo(boardNo);
    if (board == null) {
      out.writeUTF("해당 번호의 게시글이 없습니다!");
      return;
    }
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {

      board.title = prompt.inputString("제목?(" + board.title + ") ");
      board.content = prompt.inputString(String.format("내용?(%s) ", board.content));

      String input = prompt.inputString("변경하시겠습니까?(y/n) ");
      if (input.equals("y")) {

        if (boardDao.update(board) == 1) {
          tempOut.println("변경했습니다.");
          out.writeUTF(strOut.toString());
        } else {
          out.writeUTF("변경 실패입니다!");
        } 
      } else {
        out.writeUTF("변경 취소했습니다.");
      } 
    }
  }
}





