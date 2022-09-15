package com.bitcamp.board;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import com.bitcamp.board.dao.MariaDBBoardDao;
import com.bitcamp.board.dao.MariaDBMemberDao;
import com.bitcamp.board.handler.BoardHandler;
import com.bitcamp.board.handler.ErrorHandler;
import com.bitcamp.board.handler.WelcomeHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

// 1) 기본 서버 만들기
// 2) 한글 콘텐트 응답하기
// 3) HTML 콘텐트 출력하기
// 4) 메인 화면을 출력하는 요청처리 객체를 분리하기
// 5) 요청 자원의 경로를 구분하여 처리하기
// 6) 요청 자원을 처리하는 객체의 사용 규칙을 통일한다.
public class MiniWebServer {
  public static void main(String[] args) throws Exception{
    Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");

    MariaDBBoardDao boardDao = new MariaDBBoardDao(con);
    MariaDBMemberDao memberDao = new MariaDBMemberDao(con);

    WelcomeHandler welcomeHandler = new WelcomeHandler();
    ErrorHandler errorHandler = new ErrorHandler();
    BoardHandler boardHandler = new BoardHandler(boardDao);

    // 클라이언트 요청이 들어 왔을 때 호출되는 객체
    class MyHttpHandler implements HttpHandler{
      @Override
      public void handle(HttpExchange exchange) throws IOException {
        // 클라이언트 요청이 들어왔을 때마다 호출된다.
        System.out.println("클라이언트가 요청함!");

        URI requestUri = exchange.getRequestURI();

        String path =requestUri.getPath();
        String query = requestUri.getQuery();

        byte[] bytes = null;

        try(StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter)) {

          Map<String, String> paramMap = new HashMap<>();
          if (query !=  null && query.length() > 0) { // 예) no=1&title=aaaa&content=bbb
            String[] entries = query.split("&");
            for (String entry : entries) { //예) no=1
              String[] kv = entry.split("=");
              paramMap.put(kv[0], kv[1]);
            }
          }
          System.out.println(paramMap);

          if (path.equals("/")) {
            welcomeHandler.service(paramMap, printWriter);
          } else if (path.equals("/board/list")) {
            boardHandler.list(paramMap, printWriter);
          } else if (path.equals("/board/detail")) {
            boardHandler.detail(paramMap, printWriter);
          } else if (path.equals("/board/update")) {
            boardHandler.update(paramMap, printWriter);
          }else if (path.equals("/board/delete")) {
            boardHandler.delete(paramMap, printWriter);
          }else {
            errorHandler.error(paramMap, printWriter);
          }
          bytes = stringWriter.toString().getBytes("UTF-8");
        } catch (Exception e) {
          bytes = "요청 처리 중 오류 발생".getBytes("UTF-8"); // 오류 내용을 클라이언트에게 보낸다.
          e.printStackTrace(); // 서버 콘솔 창에 오류에 대한 자세한 내용을 출력한다.
        }

        // 보내는 콘텐트의 MIME 타입이 무엇인지 응답 헤더에 추가한다.
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
        // 응답 헤더 전송
        exchange.sendResponseHeaders(200, bytes.length);
        // 콘텐트 출력 스트림 준비
        OutputStream out = exchange.getResponseBody();

        out.write(bytes);
        out.close();
      }
    }
    HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
    server.createContext("/", new MyHttpHandler());
    server.setExecutor(null); // HttpServer에 기본으로 설정되어 있는 Executor 사용
    // Executor? 멀티 스레딩을 수행하는 객체
    server.start(); //HttpServer를 시작시킨 후 즉시 리턴한다.
    System.out.println("서버 시작!");
    // main() 메서드 호출이 끝났다 하더라도
    // 내부에서 생성한 스레드(예:HttpServer)가 종료되지 않으면 JVM도 종료되지 않는다.
  }
}
