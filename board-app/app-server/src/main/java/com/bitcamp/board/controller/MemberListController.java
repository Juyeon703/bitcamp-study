package com.bitcamp.board.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@WebServlet("/member/list")
public class MemberListController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  MemberDao memberDao;

  @Override
  public void init() {
    memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      List<Member> members = memberDao.findAll();
      // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
      request.setAttribute("members", members);

      //JSP에게 UI생성을 위임한다.
      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      request.getRequestDispatcher("/member/list.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
