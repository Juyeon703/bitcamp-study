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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
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

      response.setHeader("Refresh", "1;url=list"); // 응답 헤더에 refresh 명령 삽입
      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      request.getRequestDispatcher("/board/add.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

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
      //      response.sendRedirect("list");
    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
