package com.bitcamp.server.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.server.board.dao.BoardDao111;
import com.bitcamp.server.board.domain.Board111;
import com.bitcamp.server.servlet.Servlet;
import com.google.gson.Gson;

public class BoardServlet111 implements Servlet {

  private BoardDao111 boardDao;
  private String filename;

  public BoardServlet111(String dataName) {
    filename = dataName + ".json";

    boardDao = new BoardDao111(filename);
    try {
      boardDao.load();
    } catch (Exception e) {
      System.out.printf("%s 파일 로딩 중 오류 발생!\n", filename);
      e.printStackTrace();
    }
  }

  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {

      String command = in.readUTF();
      Board111 board = null;
      int no = 0;
      String json = null;

      switch (command) {
        case "findAll":
          Board111[] boards = boardDao.findAll();
          out.writeUTF(SUCCESS);
          out.writeUTF(new Gson().toJson(boards));
          break;
        case "findByNo":
          no = in.readInt();
          board = boardDao.findByNo(no);
          if (board != null) {
            out.writeUTF(SUCCESS);
            out.writeUTF(new Gson().toJson(board));
          } else {
            out.writeUTF(FAIL);
          }
          break;
        case "insert": 
          json = in.readUTF();
          board = new Gson().fromJson(json, Board111.class);
          boardDao.insert(board);
          boardDao.save();
          out.writeUTF(SUCCESS);
          break;
        case "update": 
          json = in.readUTF();
          board = new Gson().fromJson(json, Board111.class);
          if (boardDao.update(board)) {
            boardDao.save();
            out.writeUTF(SUCCESS);
          } else {
            out.writeUTF(FAIL);
          }
          break;
        case "delete": 
          no = in.readInt();
          if (boardDao.delete(no)) {
            boardDao.save();
            out.writeUTF(SUCCESS);
          } else {
            out.writeUTF(FAIL);
          }
          break;
        default:
          out.writeUTF(FAIL);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
