package com.bitcamp.board.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
public class LoginController {

  MemberService memberService;

  public LoginController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/auth/login") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    //    // include 되는 서블릿에서는 응답헤더에 쿠키를 포함시킬수 없다.
    response.addCookie(cookie); // 응답 헤더에 쿠키를 포함한다. 설정을 한다음에 붙여야함
    //    // 프론트 컨트롤러에서 쿠키를 응답헤더에 포함시키도록 ServletRequest보관소에 저장한다.
    //    List<Cookie> cookies = new ArrayList<>();
    //    cookies.add(cookie);
    //    request.setAttribute("cookies", cookies);

    request.setAttribute("member", member);
    return "/auth/loginResult.jsp";
  }
}
