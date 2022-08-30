package com.bitcamp.server.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp111 {

  public static void main(String[] args) {
    System.out.println("[게시글 데이터 관리 서버");

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println(" 서버 소켓 준비 완료!");

      try (Socket socket = serverSocket.accept();
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
        System.out.println("클라이언트와 연결 되었음!");



        System.out.println("클라이언트와 연결 끊었음!");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }
}
