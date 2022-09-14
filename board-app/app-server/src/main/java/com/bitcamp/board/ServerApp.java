package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import com.bitcamp.board.dao.MariaDBBoardDao;
import com.bitcamp.board.dao.MariaDBMemberDao;
import com.bitcamp.board.handler.BoardHandler;
import com.bitcamp.board.handler.MemberHandler;
import com.bitcamp.handler.Handler;
import com.bitcamp.util.BreadCrumb;


// 1) 클라이언트 접속 시 환영 메시지 전송
// 2) 여러 클라이언트를 순차적으로 접속 처리
// 3) 스레드를 이용하여 여러 클라이언트를 동시 접속 처리
// 4) 클라이언트가 보낸 요청 값을 받아서 그대로 돌려준다.
// 5) 요청/응답을 무한 반복한다.
// 6) quit 명령을 보내면 연결 끊기
// 7) 환영 메시지 후에 메인 메뉴를 응답한다.
// 8) 사용자가 선택한 메뉴 번호의 유효성을 검증한다.
// 9) 메인 메뉴 선택에 따라 핸들러를 실행하여 클라이언트에게 하위 메뉴를 출력한다.
//      - Handler 인터페이스 변경
//      - AbstractHandler 추상 클래스의 execute() 변경
// 10) breadcrumb 기능을 객체로 분리한다.
//      - BreadCrumb 클래스를 정의한다.
// 11) 코드 리팩토링
//      - execute() 메서드의 
// 11) 클라이언트에게 응답 메시지를 보내는 기능을 별도의 객체로 분리하여 캡슐화한다.
//      - Response 클래스 
// 11) "menu" 요청이 들어 왔을 때 클라이언트에게 

public class ServerApp {

  // 메인 메뉴 목록 준비, main메서드와 printMainmenus 메서드 둘다에서 쓰기 때문에 위로 빼줌
  private String[] menus = {"게시판", "회원"};
  private int port;

  // 핸들러를 담을 컬렉션을 준비한다.
  ArrayList<Handler> handlers = new ArrayList<>();


  public static void main(String[] args) {
    try {
      ServerApp app = new ServerApp(8888);
      app.execute();
    } catch (Exception e) {
      System.out.println("서버 실행 오류 발생!");
    }
  }

  public ServerApp(int port) throws Exception {
    this.port = port;
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    // Dao 객체를 준비한다.
    MariaDBBoardDao boardDao = new MariaDBBoardDao(con);
    MariaDBMemberDao memberDao = new MariaDBMemberDao(con);

    // 생성자에서 Handle를 초기화시킨다.
    handlers.add(new BoardHandler(boardDao));
    handlers.add(new MemberHandler(memberDao));
  }

  public void execute() {
    try(ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while(true) {
        new Thread(new ServiceProcessor(serverSocket.accept())).start();
        System.out.println("클라이언트 접속");
        /* Socket socket = serverSocket.accept();
        new Thread(new ServiceProcessor(socket)).start();
         클라이언트 연결을 기다리고 있다가 연결이 되면 연결된 소켓을 리턴받아 
         그 소켓을 들고 클라이언트에게 응답을 처리하는 ServiceProcessor를 만들어서
         ServiceProcessor를 스레드에 넘겨 그 스레드를 별도의 실행흐름으로 실행한다.*/

        // 스레드를 시작하는 순간, 별도의 실행 흐름에서 병행으로 실행된다.
        //        new Thread(new Runnable() {
        //          @Override
        //          public void run() {
        //            
        //          }
        //        }).start();

        // 추상메서드 1개짜리 인터페이스를 구현하는 경우
        // -> 람다라는 방식으로 축약할 수 있음.
        // new Thread(() -> {}).start();
      } // while

      //      System.out.println("서버 종료!");
    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  /*
  public static void main2(String[] args) {

      System.out.println("[게시글 관리 클라이언트]");
      // 메인 메뉴의 이름을 스택에 등록한다.
      breadcrumbMenu.push("메인");


      loop: while (true) {

        // 메인 메뉴 출력
        printTitle();
        printMenus(menus);
        System.out.println();

        try {


          // 메뉴에 진입할 때 breadcrumb 메뉴바에 그 메뉴를 등록한다.
          //        if (mainMenuNo > 0 && mainMenuNo <= menus.length) {
          breadcrumbMenu.push(menus[mainMenuNo -1]);
          //        }



          breadcrumbMenu.pop();
        } catch (Exception ex) {
          System.out.println("입력 값이 옳지 않습니다.");
        }


      } // while

      Prompt.close();

      // 네트워크 끊기
      // => 서버와 연결된 것을 끊는다.
      //      socket.close();

      //      // 통신 프로토콜에 따라 요청한다.
      //      out.writeUTF("board");
      //      out.writeUTF("insert");
      //
      //      // 통신 프로토콜에 따라 응답을 처리한다.
      //      String status = in.readUTF();
      //      System.out.println(status);

      System.out.println("종료!");
    } catch (Exception e) {
      System.out.println("시스템 오류 발생!");
      e.printStackTrace();
    }
}*/


  //원래 sysout의 경우 콘솔창으로 환영메시지를 출력하지만 클라이언트에게 보내기 위해 PrintStream을 사용함
  static void welcome(DataOutputStream out) throws Exception{
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {
      tempOut.println("[게시판 애플리케이션]");
      tempOut.println();
      tempOut.println("환영합니다!");
      tempOut.println(); // 항상 응답의 끝은 빈 문자열
      out.writeUTF(strOut.toString());
    }
  }

  static void error(DataOutputStream out, Exception e) {
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {
      tempOut.printf("실행 오류: %s\n", e.getMessage());
      out.writeUTF(strOut.toString());
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  // 인스턴스 필드인 menus변수를 사용할 수 있도록 static을 제거한다.
  void printMainMenus(DataOutputStream out) throws Exception{
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut);) {

      tempOut.println(BreadCrumb.getBreadCrumboOfCurrentThread().toString());
      // 메뉴 목록 출력
      for (int i = 0; i < menus.length; i++) {
        tempOut.printf("  %d: %s\n", i + 1, menus[i]);
      }
      // 메뉴 번호 입력을 요구하는 문장 출력
      tempOut.printf("메뉴를 선택하세요[1..%d](quit: 종료) ", menus.length);
      out.writeUTF(strOut.toString());
    }
  }

  void processMainMenu(DataInputStream in, DataOutputStream out, String request) {
    try {
      int menuNo = Integer.parseInt(request);
      if (menuNo < 1 || menuNo > menus.length) {
        throw new Exception("메뉴 번호가 옳지 않습니다.");
      }

      // 현재 스레드가 사용하는 breadcrumb를 꺼낸다.
      BreadCrumb breadcrumb = BreadCrumb.getBreadCrumboOfCurrentThread();

      //    핸들러에 들어가기 전에 breadcrumb 메뉴에 하위 메뉴 이름을 추가한다.
      breadcrumb.put(menus[menuNo - 1]);

      // 메뉴 번호로 Handler 객체를 찾아 실행한다.
      handlers.get(menuNo - 1).execute(in, out);

      // 다시 메인 메뉴로 돌아 왔다면 breadcrumb 메뉴에서 한단계 위로 올라간다.
      breadcrumb.pickUp();

      // 하위 메뉴에서 빠져나오면 현재의 경로를 출력한다.
      out.writeUTF(breadcrumb.toString());
    } catch (Exception e) {
      error(out, e);
    }
  }


  // 중첩클래스 장점 -> 바깥 클래스의 멤버들을 마치 자신의 멤버인냥 사용할 수 있음
  private class ServiceProcessor implements Runnable {

    Socket socket;

    public ServiceProcessor(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try (Socket s = this.socket; // socket도 run 메서드가 끝날때마다 자동으로close되도록
          DataOutputStream out = new DataOutputStream(s.getOutputStream());
          DataInputStream in = new DataInputStream(s.getInputStream())) {

        // 접속한 클라이언트의 이동 경로를 보관할 breadcrumb 객체 준비
        BreadCrumb breadcrumb = new BreadCrumb(); // 현재 스레드 보관소에 저장된다.
        breadcrumb.put("메인");

        // 클라이언트에게 환영 메시지를 보낸다.
        welcome(out);

        while(true) {
          // 클라이언트가 보낸 요청 정보를 읽는다.
          String request = in.readUTF();
          if(request.equals("quit")) {
            break;
          } else if (request.equals("menu")) { // 클라이언트에게 응답한다.
            printMainMenus(out);
            continue;
          } else {
            processMainMenu(in, out, request);
          }
        } //while
        System.out.println("클라이언트와 접속 종료!");
      } catch (Exception e) {
        System.out.println("클라이언트와 통신하는 중 오류 발생!");
        e.printStackTrace();
      }
    } // run
  } // ServiceProcessor
} // class



