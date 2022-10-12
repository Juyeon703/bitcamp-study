package com.bitcamp.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitcamp.board.service.MemberService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
public class MemberDeleteController {

  MemberService memberService;

  public MemberDeleteController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/member/delete") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    return "redirect:list";
  }
}
