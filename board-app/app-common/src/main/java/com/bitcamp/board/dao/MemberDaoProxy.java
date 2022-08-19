package com.bitcamp.board.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import com.bitcamp.board.domain.Member;
import com.google.gson.Gson;

// 게시글 목록을 관리하는 역할
//
public class MemberDaoProxy {

  String dataName;
  String ip;
  int port;

  public MemberDaoProxy(String dataName,String ip, int port) {
    this.dataName = dataName;
    this.ip = ip;
    this.port = port;
  }

  public boolean insert(Member member) throws Exception {
    try(Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      out.writeUTF(dataName);
      out.writeUTF("insert");
      out.writeUTF(new Gson().toJson(member));

      return in.readUTF().equals("success");
    }
  }

  public boolean update(Member member) throws Exception{
    try(Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      out.writeUTF(dataName);
      out.writeUTF("update");
      out.writeUTF(new Gson().toJson(member));

      return in.readUTF().equals("success");
    }
  } 

  public Member findByEmail(String email) throws Exception{
    try(Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      out.writeUTF(dataName);
      out.writeUTF("findByEmail");
      out.writeUTF(email);

      if (in.readUTF().equals("fail")) {
        return null;
      }
      //    String json = in.readUTF();
      //    Board board = new Gson().fromJson(json, Board.class);
      //    return board;
      return new Gson().fromJson(in.readUTF(), Member.class);
    }
  }

  public boolean delete(String email) throws Exception{
    try(Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      out.writeUTF(dataName);
      out.writeUTF("delete");
      out.writeUTF(email);
      return in.readUTF().equals("success");
    }
  }

  public Member[] findAll() throws Exception{
    try(Socket socket = new Socket(ip, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());) {
      out.writeUTF(dataName);
      out.writeUTF("findAll");

      if (in.readUTF().equals("fail")) {
        return null;
      }
      //    String json = in.readUTF();
      //    Board[] boards = new Gson().fromJson(json, Board[].class);
      //    return boards;
      return new Gson().fromJson(in.readUTF(), Member[].class);
    }
  }
}














