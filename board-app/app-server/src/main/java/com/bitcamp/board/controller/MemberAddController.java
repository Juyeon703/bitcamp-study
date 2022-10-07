package com.bitcamp.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;
import com.bitcamp.servlet.Controller;

public class MemberAddController implements Controller{

  MemberService memberService;

  public MemberAddController(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("UTF-8");
    Member member = new Member();
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));

    memberService.add(member);

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
    return "redirect:list";
  }
}
