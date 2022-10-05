package com.bitcamp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.board.service.DefaultMemberService;

@WebServlet("/member/delete")
public class MemberDeleteController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  DefaultMemberService memberService;

  @Override
  public void init() {
    memberService = (DefaultMemberService) this.getServletContext().getAttribute("memberService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      if (!memberService.delete(no)) {
        throw new Exception("해당 번호의 회원이 없습니다.");
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
