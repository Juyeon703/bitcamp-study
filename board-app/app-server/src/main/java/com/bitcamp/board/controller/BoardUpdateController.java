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

@WebServlet("/board/update")
public class BoardUpdateController extends HttpServlet{
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
      request.setCharacterEncoding("UTF-8");
      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      Member loginMember = (Member) request.getSession().getAttribute("loginMember");
      if (boardDao.findByNo(board.getNo()).getWriter().getNo() != loginMember.getNo()) {
        throw new Exception("게시글 작성자가 아닙니다.");
      }

      if (boardDao.update(board) == 0) {
        throw new Exception("게시글 변경 실패!");
      }

      // Refresh:
      // - 응답 헤더 또는 HTML 문서에 refresh를 삽입할 수 있다.
      // - 응답 프로토콜
      //      Content-Type: text/html;charset=UTF-8
      //      Refresh: 30;url=list;
      //      Connection: keep-alive
      //      Content-Length: 254
      //      Date: Mon, 26 Sep 2022 05:24:28 GMT
      //      Keep-Alive: timeout=20
      //      <!DOCTYPE html>
      //      <html>
      //      <head>
      //      <meta charset="UTF-8">
      //      <title>bitcamp</title>
      //      <meta http-equiv='Refresh' content='1; url=list'>
      //      </head>
      //      <body>
      //      <h1>게시글 변경(JSP+Servlet)</h1>
      //      <p>해당 게시글을 변경했습니다.</p>
      //      </body>
      //      </html>

      // 자바코드 :
      //      response.setHeader("Refresh", "1;url=list"); // 응답 헤더에 refresh 명령 삽입
      //      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      //      request.getRequestDispatcher("/board/update.jsp").include(request, response); //JSP를 실행한 후 리턴된다.
      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
