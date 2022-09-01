package com.bitcamp.server.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.bitcamp.server.board.handler.BoardServlet111;
import com.bitcamp.server.board.handler.MemberServlet111;

public class ServerApp111 {

  public static void main(String[] args) {
    System.out.println("[게시글 데이터 관리 서버");

    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println(" 서버 소켓 준비 완료!");

      try (Socket socket = serverSocket.accept();
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
        System.out.println("클라이언트와 연결 되었음!");

        BoardServlet111 boardServlet = new BoardServlet111("board");
        BoardServlet111 readingServlet = new BoardServlet111("reading");
        BoardServlet111 visitServlet = new BoardServlet111("visit");
        BoardServlet111 noticeServlet = new BoardServlet111("notice");
        BoardServlet111 dailyServlet = new BoardServlet111("daily");
        MemberServlet111 memberServlet = new MemberServlet111("member");

        while (true) {
          String dataName = in.readUTF();

          if (dataName.equals("exit")) {
            break;
          }

          switch (dataName) {
            case "board": boardServlet.service(in, out); break;
            case "reading": readingServlet.service(in, out); break;
            case "visit": visitServlet.service(in, out); break;
            case "notice": noticeServlet.service(in, out); break;
            case "daily": dailyServlet.service(in, out); break;
            case "member": memberServlet.service(in, out); break;
            default:
              out.writeUTF("fail");
          }
        } 

        System.out.println("클라이언트와 연결 끊었음!");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }
}
