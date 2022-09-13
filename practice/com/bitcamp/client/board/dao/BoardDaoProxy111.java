package com.bitcamp.server.board.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.server.board.domain.Board111;
import com.google.gson.Gson;

public class BoardDaoProxy111 {

  String dataName;
  DataInputStream in;
  DataOutputStream out;

  public BoardDaoProxy111(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;
  }

  public boolean insert(Board111 board) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("insert");
    out.writeUTF(new Gson().toJson(board));
    return in.readUTF().equals("success");
  }

  public boolean update(Board111 board) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("update");
    out.writeUTF(new Gson().toJson(board));
    return in.readUTF().equals("success");
  }

  public Board111 findByNo(int boardNo) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("findByNo");
    out.writeInt(boardNo);

    if (in.readUTF().equals("fail")) {
      return null;
    }
    return new Gson().fromJson(in.readUTF(), Board111.class);
  }

  public boolean delete(int boardNo) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("delete");
    out.writeInt(boardNo);
    return in.readUTF().equals("success");
  }

  public Board111[] findAll() throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("findAll");

    if (in.readUTF().equals("fail")) {
      return null;
    }
    return new Gson().fromJson(in.readUTF(), Board111[].class);
  }
}
