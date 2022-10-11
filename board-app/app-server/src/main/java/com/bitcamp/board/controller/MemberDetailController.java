package com.bitcamp.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;
import com.bitcamp.servlet.Controller;

@Component("/member/detail")
public class MemberDetailController implements Controller{
  MemberService memberService;

  public MemberDetailController(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Member member = memberService.get(no);

    if (member == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    request.setAttribute("member", member);

    return "/member/detail.jsp";
  }
}
