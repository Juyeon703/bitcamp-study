package com.bitcamp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.BoardService;

@WebServlet("/board/delete")
public class BoardDeleteController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) this.getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      // 작성자만 삭제할 수 있도록
      Member loginMember = (Member) request.getSession().getAttribute("loginMember");
      if (boardService.get(no).getWriter().getNo() != loginMember.getNo()) {
        throw new Exception("게시글 작성자가 아닙니다.");
      }

      //      // 첨부파일 삭제
      //      boardDao.deleteFiles(no);
      //
      //      // 게시글 삭제 
      //      if (boardDao.delete(no) == 0) {
      //        throw new Exception("게시글 삭제 실패!");
      //      }

      if (!boardService.delete(no)) {
        throw new Exception("게시글을 삭제할 수 없습니다!");
      }

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
