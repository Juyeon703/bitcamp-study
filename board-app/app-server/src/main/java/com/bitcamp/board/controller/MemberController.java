package com.bitcamp.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.MemberService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
@RequestMapping("/member/")
public class MemberController {

  MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("form") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String form(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/member/form.jsp";
  }

  @PostMapping("add") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member member = new Member();
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));

    memberService.add(member);

    return "redirect:list";
  }

  @GetMapping("list") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<Member> members = memberService.list();
    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    request.setAttribute("members", members);

    return "/member/list.jsp";
  }

  @GetMapping("detail") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Member member = memberService.get(no);

    if (member == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    request.setAttribute("member", member);

    return "/member/detail.jsp";
  }

  @PostMapping("update") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member member = new Member();
    member.setNo(Integer.parseInt(request.getParameter("no")));
    member.setName(request.getParameter("name"));
    member.setEmail(request.getParameter("email"));
    member.setPassword(request.getParameter("password"));

    if (!memberService.update(member)) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    return "redirect:list";
  }

  @GetMapping("delete") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    if (!memberService.delete(no)) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    return "redirect:list";
  }
}
