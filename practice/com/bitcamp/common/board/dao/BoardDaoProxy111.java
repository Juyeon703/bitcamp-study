package com.bitcamp.common.board.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import com.bitcamp.common.board.domain.Board111;
import com.google.gson.Gson;

public class BoardDaoProxy111 {

  String dataName;
  String ip;
  int port;

  public BoardDaoProxy111(String dataName, String ip, int port) {
    this.dataName = dataName;
    this.ip = ip;
    this.port = port;
  }

  public boolean insert(Board111 board) throws Exception {
    try (Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {

      out.writeUTF(dataName);
      out.writeUTF("insert");
      out.writeUTF(new Gson().toJson(board));
      return in.readUTF().equals("success");
    }
  }

  public boolean update(Board111 board) throws Exception {
    try (Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {

      out.writeUTF(dataName);
      out.writeUTF("update");
      out.writeUTF(new Gson().toJson(board));
      return in.readUTF().equals("success");
    }
  }

  public Board111 findByNo(int boardNo) throws Exception {
    try (Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {

      out.writeUTF(dataName);
      out.writeUTF("findByNo");
      out.writeInt(boardNo);

      if (in.readUTF().equals("fail")) {
        return null;
      }
      return new Gson().fromJson(in.readUTF(), Board111.class);
    }
  }

  public boolean delete(int boardNo) throws Exception {
    try (Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {

      out.writeUTF(dataName);
      out.writeUTF("delete");
      out.writeInt(boardNo);
      return in.readUTF().equals("success");
    }
  }

  public Board111[] findAll() throws Exception {
    try (Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {

      out.writeUTF(dataName);
      out.writeUTF("findAll");

      if (in.readUTF().equals("fail")) {
        return null;
      }
      return new Gson().fromJson(in.readUTF(), Board111[].class);
    }
  }
}
