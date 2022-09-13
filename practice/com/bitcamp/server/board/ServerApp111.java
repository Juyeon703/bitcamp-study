package com.bitcamp.server.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import com.bitcamp.server.board.handler.BoardServlet111;
import com.bitcamp.server.board.handler.MemberServlet111;
import com.bitcamp.server.servlet.Servlet;

public class ServerApp111 {

  public static void main(String[] args) {
    System.out.println("[게시글 데이터 관리 서버");

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println(" 서버 소켓 준비 완료!");

      Hashtable<String,Servlet> servletMap = new Hashtable<>();
      servletMap.put("board", new BoardServlet111("board"));
      servletMap.put("reading", new BoardServlet111("reading"));
      servletMap.put("visit", new BoardServlet111("visit"));
      servletMap.put("notice", new BoardServlet111("notice"));
      servletMap.put("daily", new BoardServlet111("daily"));
      servletMap.put("member", new MemberServlet111("member"));

      while (true) {
        try (Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
          System.out.println("클라이언트와 연결 되었음!");

          while (true) {
            String dataName = in.readUTF();

            if (dataName.equals("exit")) {
              break;
            }

            Servlet servlet = servletMap.get(dataName);
            if (servlet != null) {
              servlet.service(in, out);
            } else {
              out.writeUTF("fail");
            }
          } 

          System.out.println("클라이언트와 연결 끊었음!");
        } 
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }
}
