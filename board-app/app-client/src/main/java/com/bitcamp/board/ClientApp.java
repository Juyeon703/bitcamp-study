package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import com.bitcamp.util.Prompt;

// 1) 서버에 접속
// 4) 사용자 입력을 서버에 전송
// 5) 요청/응답을 무한 반복한다.
// 6) quit 명령을 보내면 연결끊기
public class ClientApp {

  //breadcrumb 메뉴를 저장할 스택을 준비
  //  public static Stack<String> breadcrumbMenu = new Stack<>();

  public static void main(String[] args) {
    //    try(Connection con = DriverManager.getConnection(
    //        "jdbc:mariadb://localhost:3306/studydb","study","1111")) {
    System.out.println("[게시글 관리 클라이언트]");

    try(Socket socket = new Socket("localhost", 8888);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      String response = null;

      // 사용자의 입력 값을 서버에 전달한 후 서버의 응답을 출력한다.
      while(true) {
        response = in.readUTF();
        System.out.println(response);

        String input = Prompt.inputString("> ");
        out.writeUTF(input);

        if(input.equals("quit")) {
          break;
        }

      }
    } catch (Exception e) {
      System.out.println("서버와 통신 중 오류 발생!");
      e.printStackTrace();
    }
    System.out.println("종료!");
  }
}


//```
//[client] <---------------------> [server]
//    |---- 접속 ---------------------->|
//    |<----- 환영 메시지 --------------|
//    |---- 사용자 입력 전송(요청) ---->|
//    |<----- 응답 메시지 --------------|
//    |         요청/응답 반복          |
//    |---- "quit" -------------------->| 
//    |          연결 끊기              |
//```
//```
//요청메시지:    
//  한 덩어리의 문자열
//
//응답메시지:
//  한 덩어리의 문자열
//```








//    String ip = "127.0.0.1";
//    int port = 8888;
//    try (
//        // 네트워크 준비
//        // => 정상적으로 연결되었으면 Socket 객체를 리턴한다.
//        Socket socket = new Socket("127.0.0.1", 8888);
//        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//        DataInputStream in = new DataInputStream(socket.getInputStream());) {
//      // 127.0.0.1 => local host
//      System.out.println("연결되었음!");

//      welcome();

//      // Dao 객체를 준비한다.
//      MariaDBMemberDao memberDao = new MariaDBMemberDao(con);
//      MariaDBBoardDao boardDao = new MariaDBBoardDao(con);
//
//      // 핸들러를 담을 컬렉션을 준비한다.
//      ArrayList<Handler> handlers = new ArrayList<>();
//      handlers.add(new BoardHandler(boardDao));
//      handlers.add(new MemberHandler(memberDao));

//      // 핸들러를 담을 레퍼런스 배열을 준비한다.
//      Handler[] handlers = new Handler[] {
//          new BoardHandler("board", in, out), // 게시판
//          new BoardHandler("reading", in, out), // 독서록
//          new BoardHandler("visit", in, out), // 방명록
//          new BoardHandler("notice", in, out), // 공지사항
//          new BoardHandler("daily", in, out), // 일기장
//          new MemberHandler("member", in, out) // 회원
//      };

//      // 메인 메뉴의 이름을 스택에 등록한다.
//      breadcrumbMenu.push("메인");
//
//      // 메뉴명을 저장할 배열을 준비한다.
//      String[] menus = {"게시판", "회원"};

//      loop: while (true) {
//
//        // 메인 메뉴 출력
//        printTitle();
//        printMenus(menus);
//        System.out.println();
//
//        try {
//          int mainMenuNo = Prompt.inputInt(String.format(
//              "메뉴를 선택하세요[1..%d](0: 종료) ", handlers.size()));
//
//          if (mainMenuNo < 0 || mainMenuNo > menus.length) {
//            System.out.println("메뉴 번호가 옳지 않습니다!");
//            continue; // while 문의 조건 검사로 보낸다.
//          } else if (mainMenuNo == 0) {
//            break loop;
//          }
//          // 메뉴에 진입할 때 breadcrumb 메뉴바에 그 메뉴를 등록한다.
//          //        if (mainMenuNo > 0 && mainMenuNo <= menus.length) {
//          breadcrumbMenu.push(menus[mainMenuNo -1]);
//          //        }
//
//          // 메뉴 번호로 Handler 레퍼런스에 들어있는 객체를 찾아 실행한다.
//          handlers.get(mainMenuNo - 1).execute();
//
//          breadcrumbMenu.pop();
//        } catch (Exception ex) {
//          System.out.println("입력 값이 옳지 않습니다.");
//        }
//
//
//      } // while

//    Prompt.close();

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
//    } catch (Exception e) {
//      System.out.println("시스템 오류 발생!");
//      e.printStackTrace();
//    }


//  static void welcome() {
//    System.out.println("[게시판 애플리케이션]");
//    System.out.println();
//    System.out.println("환영합니다!");
//    System.out.println();
//  }
//
//  static void printMenus(String[] menus) {
//    for (int i = 0; i < menus.length; i++) {
//      System.out.printf("  %d: %s\n", i + 1, menus[i]);
//    }
//  }
//
//  protected static void printTitle() {
//    StringBuilder builder = new StringBuilder();
//    for (String title : breadcrumbMenu) {
//      if(!builder.isEmpty()) {
//        builder.append(" > ");
//      }
//      builder.append(title);
//    }
//    System.out.printf("%s:\n", builder.toString());
//  }



