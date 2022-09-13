package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import com.bitcamp.board.servlet.BoardServlet;
import com.bitcamp.board.servlet.MemberServlet;
import com.bitcamp.servlet.Servlet;

public class ServerApp02 {
  public static void main(String[] args) {
    // 클라이언트 요청을 처리할 객체 준비
    // 반복문 바깥으로 뺌 -> 한번만 만들어져야하기 때문에
    Hashtable<String, Servlet> servletMap = new Hashtable<>();
    servletMap.put("board", new BoardServlet("board"));
    servletMap.put("reading", new BoardServlet("reading"));
    servletMap.put("visit", new BoardServlet("visit"));
    servletMap.put("notice", new BoardServlet("notice"));
    servletMap.put("daily", new BoardServlet("daily"));
    servletMap.put("member", new MemberServlet("member"));
    //        BoardServlet boardServlet = new BoardServlet("board");
    //        BoardServlet readingServlet = new BoardServlet("reading");
    //        BoardServlet visitServlet = new BoardServlet("visit");
    //        BoardServlet noticeServlet = new BoardServlet("notice");
    //        BoardServlet dailyServlet = new BoardServlet("daily");
    //        MemberServlet memberServlet = new MemberServlet("member");

    //      while (true) {
    //        try(
    //            // 클라이언트의 연결을 기다림
    //            // => 클라이언트와 연결되면 그 클라이언트와 통신할 준비를 한다.
    //            // 즉 Socket 객체 리턴
    //            // => 클라이언트와 연결될 때까지 리턴하지 않는다.
    //            Socket socket = serverSocket.accept();
    //
    //            // 클라이언트와 연결된 것을 끊는다.
    //            //      socket.close();
    //            //      System.out.println("클라이언트와 연결을 끊었음!");
    //
    //            //      // 클라이언트와 데이터를 주고 받는다.
    //            //      // 클라이언트가 보낸 데이터를 읽을 때 사용할 도구를 준비한다.
    //            //      InputStream in = socket.getInputStream();
    //            //      // => 데이터를 읽을 때 primitive type 또는 String 타입의 값을
    //            //      //    보다 손쉽게 읽을 수 있도록 기존의 입력 도구에 보조 도구(decorator) 붙여 사용한다.
    //            //      DataInputStream in2 = new DataInputStream(in);
    //            DataInputStream in = new DataInputStream(socket.getInputStream());
    //
    //            //      // => 클라이언트로 데이터를 보낼 때 사용할 도구를 준비한다,.
    //            //      OutputStream out = socket.getOutputStream();
    //            //      // => 데이터를 출력할때 primitive type 또는 String 타입의 값을
    //            //      //    보다 손쉽게 출력할 수 있도록 기존의 출력 도구에 보조 도구(decorator) 붙여 사용한다.
    //            //      DataOutputStream out2 = new DataOutputStream(out);
    //
    //            DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
    //          System.out.println("클라이언트와 연결 되었음!");
    //
    //          // 클라이언트와 서버 사이에 정해진 규칙(protocol)에 따라 데이터를 주고 받는다.
    //          String dataName = in.readUTF(); // 클라이언트에서 문자열이 올때까지 기다림?
    //
    //          if (dataName.equals("exit")) {
    //            break;
    //          }
    //
    //          Servlet servlet = servletMap.get(dataName);
    //          if (servlet != null) {
    //            servlet.service(in, out);
    //          } else {
    //            out.writeUTF("fail");
    //          }
    //          System.out.println("클라이언트와 연결 끊었음!");
    //        } // 안쪽 try
    //        //          switch (dataName) {
    //        //            case "board" : boardServlet.service(in, out); break;
    //        //            case "reading" : readingServlet.service(in, out); break;
    //        //            case "visit" : visitServlet.service(in, out); break;
    //        //            case "notice" : noticeServlet.service(in, out); break;
    //        //            case "daily" : dailyServlet.service(in, out); break;
    //        //            case "member" : memberServlet.service(in, out); break;
    //        //            default :
    //        //              out.writeUTF("fail");
    //        //          }
    //        //            // 네트워크 종료
    //        //            // => 더이상 클리아언트와 연결하고 싶지 않다면 네트워크를 종료한다.
    //        //            serverSocket.close();
    class RequestThread extends Thread{

      private Socket socket;

      public RequestThread(Socket socket) {
        this.socket = socket;
      }

      // 별도의 실행흐름에서 수행할 작업 정의
      @Override
      public void run() {
        try(Socket socket = this.socket;
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

          System.out.println("클라이언트와 연결 되었음!");

          String dataName = in.readUTF(); 

          // 로컬 클래스는 바깥 메서드의 로컬 변수를 자신의 멤버인 것 처럼 사용할 수 있다.
          // 어떻게? 컴파일러가 그것이 가능하도록 필드와 생성자에 파라미터를 자동으로 추가한다.
          Servlet servlet = servletMap.get(dataName);
          if (servlet != null) {
            servlet.service(in, out);
          } else {
            out.writeUTF("fail");
          }
          System.out.println("클라이언트와 연결 끊었음!");
        } catch (Exception e) {
          System.out.println("클라이언트 요청 처리 중 오류 발생!");
          e.printStackTrace();
        }
      }
    }

    System.out.println("[게시글 데이터 관리 서버]");

    try (
        // 네트워크 준비
        // => 클라이언트 연결을 관리할 객체를 준비한다.
        ServerSocket serverSocket = new ServerSocket(8888);) {

      System.out.println("서버 소켓 준비 완료!");


      while (true) {
        // 클라이언트가 연결되면
        Socket socket = serverSocket.accept();

        // 클라이언트 요청을 처리할 스레드를 만든다.
        RequestThread t = new RequestThread(socket);
        t.start();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } // 바깥쪽 try
    System.out.println("서버 종료!");
  }
}
