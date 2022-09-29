package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;

@WebServlet("/board/add")
public class BoardAddController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  BoardDao boardDao;

  @Override
  public void init() {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {

      // URL 디코딩 한 바이트를 UTF-16으로 변환하기 전에
      // 그 바이트의 characterset이 무엇인지 알려줘야 한다.
      // 안 알려주면 그 디코딩 바이트가 ASCII코드라고 간주한다.
      // UTF-8코드를 ASCII 잘못 판단하니까 UTF-16으로 바꿀 때 오류가 발생하는 것이다.
      // 물론 영어나 숫자는 ASCII코드와 UTF-16코드가 같기 때문에 UTF-16으로 변환하더라도 문제가 되지않는다.
      // 그러나 한글은 UTF-8코드의 3바이트를 묶어서 UTF-16의 2바이트로 변환해야 하는데
      // 영어라고 간주하고 각각의 1바이트를 2바이트로 변환하니까 문제가 발생하는 것이다.
      request.setCharacterEncoding("UTF-8");
      Board board = new Board();
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      // 로그인 사용자 정보는 파라미터로 받아서는 안된다.
      // 반드시 세션에서 꺼내 써야한다.
      // 왜? 클라이언트가 다른 사용자 정보를 보낼 수 있기 때문이다. 
      Member loginMember = (Member)request.getSession().getAttribute("loginMember");
      board.setWriter(loginMember);

      if (boardDao.insert(board) == 0) {
        throw new Exception("게시글 등록 실패!");
      }

      //JSP에게 UI생성을 위임한다.
      // Refresh:
      // - 응답 헤더 또는 HTML 문서에 refresh를 삽입할 수 있다.

      //      response.setHeader("Refresh", "1;url=list"); // 응답 헤더에 refresh 명령 삽입
      //      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      //      request.getRequestDispatcher("/board/add.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

      // Redirect:
      // - 클라이언트에게 콘텐트를 보내지 않는다.
      // - 응답 프로토콜
      //      Connection: keep-alive
      //      Content-Length: 254
      //      Content-Type: text/html;charset=UTF-8
      //      Date: Mon, 26 Sep 2022 05:24:28 GMT
      //      Keep-Alive: timeout=20

      // (콘텐트 없음)
      // 자바 코드 
      response.sendRedirect("list");
    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
