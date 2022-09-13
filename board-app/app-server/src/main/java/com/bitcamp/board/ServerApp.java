package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

public class ServerApp {

  // 메인 메뉴 목록 준비, main메서드와 printMainmenus 메서드 둘다에서 쓰기 때문에 위로 빼줌
  static String[] menus = {"게시판", "회원"};

  public static void main(String[] args) {
    try(ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      // 핸들러를 담을 컬렉션을 준비한다.
      ArrayList<Handler> handlers = new ArrayList<>();
      handlers.add(new BoardHandler(null));
      handlers.add(new MemberHandler(null));

      while(true) {
        Socket socket = serverSocket.accept();
        //        new Thread(new Runnable() {
        //          @Override
        //          public void run() {
        //            
        //          }
        //        }).start();

        // 추상메서드 1개짜리 인터페이스를 구현하는 경우
        // -> 람다라는 방식으로 축약할 수 있음.
        new Thread(() -> {
          // 스레드를 시작하는 순간, 별도의 실행 흐름에서 병행으로 실행된다.
          try (
              DataOutputStream out = new DataOutputStream(socket.getOutputStream());
              DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("클라이언트 접속");

            // 접속한 클라이언트의 이동 경로를 보관할 breadcrumb 객체 준비
            BreadCrumb breadcrumb = new BreadCrumb(); // 현재 스레드 보관소에 저장된다.
            breadcrumb.put("메인");

            boolean first = true;
            String errorMessage = null;
            while (true) {
              try (StringWriter strOut = new StringWriter();
                  PrintWriter tempOut = new PrintWriter(strOut);) {
                if (first) { // 최초 접속이면 환영메시지도 출력한다.
                  welcome(tempOut);
                  first = false;
                }
                if (errorMessage != null) {
                  tempOut.println(errorMessage);
                  errorMessage = null;
                }

                tempOut.println(breadcrumb.toString());
                printMainMenus(tempOut);
                //클라이언트로 출력하기 -- StirngWriter 버퍼에 들어있음
                out.writeUTF(strOut.toString());
                // 클라이언트로 응답한 후에 새 StringWriter로 교체한다. <- 버퍼를 초기화시키는 메서드가 없음.
                // -> while문 안으로 StirngWriter, PrintWriter 넣음
              }
              // 클라이언트가 보낸 요청을 읽는다.
              String request = in.readUTF();
              if(request.equals("quit")) {
                break;
              }
              try {
                int mainMenuNo = Integer.parseInt(request);
                if (mainMenuNo >=1 && mainMenuNo <= menus.length) {
                  // 핸들러에 들어가기 전에 breadcrumb 메뉴에 하위 메뉴 이름을 추가한다.
                  breadcrumb.put(menus[mainMenuNo - 1]);

                  // 메뉴 번호로 Handler 객체를 찾아 실행한다.
                  handlers.get(mainMenuNo - 1).execute(in, out);

                  // 다시 메인 메뉴로 돌아 왔다면 breadcrumb 메뉴에서 한단계 위로 올라간다.
                  breadcrumb.pickUp();
                } else {
                  throw new Exception("해당 번호의 메뉴가 없습니다.");
                }
              } catch (Exception e) {
                errorMessage = String.format("실행오류: %s\n", e.getMessage());
              }
            }
            System.out.println("클라이언트와 접속 종료!");

          } catch (Exception e) {
            System.out.println("클라이언트와 통신하는 중 오류 발생!");
            e.printStackTrace();
          }
        }).start();
      }


    } catch (Exception e) {
      System.out.println("서버 실행 중 오류 발생!");
      e.printStackTrace();
    }
    System.out.println("서버 종료!");
  }

  /*
  public static void main2(String[] args) {
    try(Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111")) {
      System.out.println("[게시글 관리 클라이언트]");

      // Dao 객체를 준비한다.
      MariaDBMemberDao memberDao = new MariaDBMemberDao(con);
      MariaDBBoardDao boardDao = new MariaDBBoardDao(con);



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
  static void welcome(PrintWriter out) {
    out.println("[게시판 애플리케이션]");
    out.println();
    out.println("환영합니다!");
    out.println(); // 항상 응답의 끝은 빈 문자열
  }

  static void printMainMenus(PrintWriter out) {

    // 메뉴 목록 출력
    for (int i = 0; i < menus.length; i++) {
      out.printf("  %d: %s\n", i + 1, menus[i]);
    }
    // 메뉴 번호 입력을 요구하는 문장 출력
    out.printf("메뉴를 선택하세요[1..%d](quit: 종료) ", menus.length);
  }
}


