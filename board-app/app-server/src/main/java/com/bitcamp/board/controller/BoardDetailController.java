package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;

@WebServlet("/board/detail")
public class BoardDetailController extends HttpServlet{
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
      int boardNo = Integer.parseInt(request.getParameter("no"));

      Board board = boardDao.findByNo(boardNo);

      if (board == null) {
        throw new Exception ("해당 번호의 게시글이 없습니다");
      }
      // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
      request.setAttribute("board", board);

      //JSP에게 UI생성을 위임한다.
      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      request.getRequestDispatcher("/board/detail.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
