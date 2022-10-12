package com.bitcamp.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
public class MemberListController {
  MemberService memberService;

  public MemberListController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/member/list") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Member> members = memberService.list();
    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    request.setAttribute("members", members);

    return "/member/list.jsp";
  }
}
