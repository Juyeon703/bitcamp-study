package com.bitcamp.board.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public void form() throws Exception {
  }

  @PostMapping("add") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String add(Member member) throws Exception {
    //    Member member = new Member();
    //    member.setName(request.getParameter("name"));
    //    member.setEmail(request.getParameter("email"));
    //    member.setPassword(request.getParameter("password"));

    memberService.add(member);

    return "redirect:list";
  }

  @GetMapping("list") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public void list(Model model) throws Exception {
    // 프론트 컨트롤러가 건네준 Model 객체에 작업 결과를 담아 두면
    // 핸들러 호출이 끝났을 때  JSP를 실행하기 전에
    // 먼저 Model 객체에 담아둔 값을 ServletRequest 보관소로 옮긴다.
    model.addAttribute("members", memberService.list());
    //    List<Member> members = memberService.list();
    //    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    //    request.setAttribute("members", members);
  }

  @GetMapping("detail") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public void detail(int no, Map map) throws Exception {
    //    int no = Integer.parseInt(request.getParameter("no"));
    Member member = memberService.get(no);

    if (member == null) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
    map.put("member", member);
  }

  @PostMapping("update") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String update(Member member) throws Exception {
    //    Member member = new Member();
    //    member.setNo(Integer.parseInt(request.getParameter("no")));
    //    member.setName(request.getParameter("name"));
    //    member.setEmail(request.getParameter("email"));
    //    member.setPassword(request.getParameter("password"));
    if (!memberService.update(member)) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    return "redirect:list";
  }

  @GetMapping("delete") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String delete(int no) throws Exception {
    //    int no = Integer.parseInt(request.getParameter("no"));
    if (!memberService.delete(no)) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    return "redirect:list";
  }
}
