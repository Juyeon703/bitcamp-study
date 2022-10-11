package com.bitcamp.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.BoardService;

@Controller // 페이지 컨트롤러에 붙이는 애노테이션
//애노테이션을 붙일 때 객체 이름을 명시하면 그 이름으로 저장한다.
//프론트 컨트롤러는 페이지 컨트롤러를 찾을 때 이 이름으로 찾을 것이다.
public class BoardDeleteController {

  BoardService boardService;

  public BoardDeleteController(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/board/delete") // 요청이 들어왔을 때 호출될 메서드에 붙이는 애노테이션
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));

    // 작성자만 삭제할 수 있도록
    Member loginMember = (Member) request.getSession().getAttribute("loginMember");
    if (boardService.get(no).getWriter().getNo() != loginMember.getNo()) {
      throw new Exception("게시글 작성자가 아닙니다.");
    }

    //      // 첨부파일 삭제
    //      boardDao.deleteFiles(no);
    //
    //      // 게시글 삭제 
    //      if (boardDao.delete(no) == 0) {
    //        throw new Exception("게시글 삭제 실패!");
    //      }

    if (!boardService.delete(no)) {
      throw new Exception("게시글을 삭제할 수 없습니다!");
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
