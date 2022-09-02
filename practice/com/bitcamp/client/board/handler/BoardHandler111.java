package com.bitcamp.client.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.bitcamp.client.board.domain.Board111;
import com.bitcamp.client.handler.AbstractHandler111;
import com.bitcamp.client.util.Prompt111;
import com.google.gson.Gson;

public class BoardHandler111 extends AbstractHandler111 {

  private String dataName;
  private DataInputStream in;
  private DataOutputStream out;

  public BoardHandler111(String dataName, DataInputStream in, DataOutputStream out) {
    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});

    this.dataName = dataName;
    this.in = in;
    this.out = out;
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
    out.writeUTF(dataName);
    out.writeUTF("findAll");

    if (in.readUTF().equals("fail")) {
      System.out.println("목록을 가져오는데 실패했습니다!");
      return;
    }

    String json = in.readUTF();
    Board111[] boards = new Gson().fromJson(json, Board111[].class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    System.out.println("번호 제목 조회수 작성자 등록일");

    for (Board111 board : boards) {
      Date date = new Date(board.createdDate);
      String dateStr = formatter.format(date); 
      System.out.printf("%d\t%s\t%d\t%s\t%s\n", board.no, board.title, board.viewCount, board.writer, dateStr);
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

    out.writeUTF(dataName);
    out.writeUTF("findByNo");
    out.writeInt(boardNo);

    if (in.readUTF().equals("fail")) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    String json = in.readUTF();
    Board111 board = new Gson().fromJson(json, Board111.class);

    System.out.printf("번호: %d\n", board.no);
    System.out.printf("제목: %s\n", board.title);
    System.out.printf("내용: %s\n", board.content);
    System.out.printf("조회수: %d\n", board.viewCount);
    System.out.printf("작성자: %s\n", board.writer);
    Date date = new Date(board.createdDate);
    System.out.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }

  private void onInput() throws Exception{
    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.writer = Prompt111.inputString("작성자? ");
    board.password = Prompt111.inputString("암호? ");
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();
    out.writeUTF(dataName);
    out.writeUTF("insert");
    String json = new Gson().toJson(board);
    out.writeUTF(json);

    if (in.readUTF().equals("success")) {
      System.out.println("게시글을 등록했습니다.");
    } else {
      System.out.println("게시글 등록에 실패했습니다!");
    }
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
    out.writeUTF(dataName);
    out.writeUTF("delete");
    out.writeInt(boardNo);

    if (in.readUTF().equals("success")) {
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
    out.writeUTF(dataName);
    out.writeUTF("findByNo");
    out.writeInt(boardNo);

    if (in.readUTF().equals("fail")) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    String json = in.readUTF();
    Board111 board = new Gson().fromJson(json, Board111.class);

    board.title = Prompt111.inputString("제목?(" + board.title +")");
    board.content = Prompt111.inputString("내용?(" + board.content +")");
    String input = Prompt111.inputString("변경하시겠습니까?(y/n) ");
    if (input.equals("y")) {
      out.writeUTF(dataName);
      out.writeUTF("update");
      out.writeUTF(new Gson().toJson(board));

      if (in.readUTF().equals("success")) {
        System.out.println("변경했습니다.");
      } else {
        System.out.println("변경 실패입니다!");
      }
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }
}