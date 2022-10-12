package com.bitcamp.board.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
@RequestMapping("/auth/") // 요청 url을 리턴은 요청이 아니기때문에 해당되지않음
public class AuthController {

  MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  //@RequestMapping(value="/auth/form", method=RequestMethod.GET) 
  @GetMapping("form")
  public String form(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/auth/form.jsp";
  }

  // 'value'나  'path'나 같다
  //@RequestMapping(path="/auth/login", method=RequestMethod.POST) 
  @PostMapping("login")
  public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

  //@RequestMapping(value="/auth/logout", method=RequestMethod.GET)
  @GetMapping("logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(); 
    session.invalidate(); // 현재 세션을 무효화시킨다.
    return "redirect:../../"; // 로그아웃한 후 메인 페이지를 요청하라고 응답한다.
  }
}
