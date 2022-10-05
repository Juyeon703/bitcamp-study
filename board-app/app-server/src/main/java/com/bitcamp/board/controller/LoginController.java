package com.bitcamp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.DefaultMemberService;

@WebServlet("/auth/login")
public class LoginController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  DefaultMemberService memberService;

  @Override
  public void init() {
    memberService = (DefaultMemberService) this.getServletContext().getAttribute("memberService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      Member member = memberService.get(email, password);

      if (member != null) {
        HttpSession session = request.getSession();//요청한 클라이언트의 전용 HttpSession 보관소를 얻는다.
        session.setAttribute("loginMember", member);
      }

      // 클라이언트에게 쿠키 보내기
      // - 쿠키 데이터는 문자열만 가능
      Cookie cookie = new Cookie("email", email); // 클라이언트 쪽에 저장할 쿠키 생성
      if (request.getParameter("saveEmail") == null) {
        cookie.setMaxAge(0); // 클라이언트에게 해당 이름의 쿠키를 지우라고 명령한다.
      } else {
        // 쿠키의 유지시간을 설정하지 않으면 웹브라우저가 실행되는 동안만 유지된다.
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
      }
      response.addCookie(cookie); // 응답 헤더에 쿠키를 포함한다. 설정을 한다음에 붙여야함

      // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
      request.setAttribute("member", member);

      //JSP에게 UI생성을 위임한다.
      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      request.getRequestDispatcher("/auth/loginResult.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
