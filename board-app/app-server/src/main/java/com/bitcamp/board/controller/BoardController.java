package com.bitcamp.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.BoardService;


@Controller // 페이지 컨트롤러에 붙이는 애노테이션
@RequestMapping("/board/")
public class BoardController {

  // 서블릿 컨텍스트는 파라미터로 주입받을 수 없고 생성자를 통해 주입받는다.
  ServletContext sc;
  BoardService boardService;

  public BoardController(BoardService boardService, ServletContext sc) {
    System.out.println("BoardController() 호출됨!");
    this.boardService = boardService;
    this.sc = sc;
  }

  // InternalResourceViewResolver  사용 전
  //  @GetMapping("form") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  //  public String form() throws Exception {
  //    return "board/form";
  //  }

  // InternalResourceViewResolver  사용 후
  @GetMapping("form") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public void form() throws Exception {
  }

  @PostMapping("add") 
  public String add(
      //@RequestParam("title") String title, 파라미터명과 변수명이 같다면? 생략 가능
      //      String title,
      //      String content, 
      Board board,
      @RequestParam("files") MultipartFile[] files,
      HttpSession session) throws Exception {

    //    Board board = new Board();
    //    board.setTitle(title);
    //    board.setContent(content);
    board.setAttachedFiles(saveAttachedfiles(files));
    board.setWriter((Member) session.getAttribute("loginMember"));

    boardService.add(board);

    return "redirect:list";

  }

  private List<AttachedFile> saveAttachedfiles(Part[] files) throws IOException, ServletException {
    List<AttachedFile> attachedFiles = new ArrayList<>();
    String dirPath = sc.getRealPath("/board/files");
    for(Part part : files) {
      if (!part.getName().equals("files") || part.getSize() == 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      part.write(dirPath + "/" + filename);
      attachedFiles.add(new AttachedFile(filename));
    }
    return attachedFiles;
  }

  private List<AttachedFile> saveAttachedfiles(MultipartFile[] files) throws IOException, ServletException {
    List<AttachedFile> attachedFiles = new ArrayList<>();
    String dirPath = sc.getRealPath("/board/files");
    for(MultipartFile part : files) {
      if (part.isEmpty()) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      part.transferTo(new File(dirPath + "/" + filename));
      attachedFiles.add(new AttachedFile(filename));
    }
    return attachedFiles;
  }

  @GetMapping("list") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public void list(Model model) throws Exception {
    model.addAttribute("boards", boardService.list());
    //mv.setViewName("board/list");
  }

  @GetMapping("detail") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public Map detail(int no) throws Exception {

    Board board = boardService.get(no);

    if (board == null) {
      throw new Exception ("해당 번호의 게시글이 없습니다");
    }
    Map map = new HashMap();
    map.put("board", board);
    return map;
  }

  @PostMapping("update") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String update(
      int no,
      String title,
      String content,
      Part[] files,
      HttpSession session) throws Exception {
    Board board = new Board();
    board.setNo(no);
    board.setTitle(title);
    board.setContent(content);
    board.setAttachedFiles(saveAttachedfiles(files));

    checkOwner(board.getNo(), session);

    if (!boardService.update(board)) {
      throw new Exception("게시글 변경할 수 없습니다!");
    }
    return "redirect:list";
  }

  private void checkOwner(int boardNo, HttpSession session) throws Exception {
    Member loginMember = (Member) session.getAttribute("loginMember");
    if (boardService.get(boardNo).getWriter().getNo() != loginMember.getNo()) {
      throw new Exception("게시글 작성자가 아닙니다.");
    }
  }

  @GetMapping("delete") // 요청이 들어왔을 때 호출될 메서드에 붙이는 애노테이션
  public String delete(
      int no,
      HttpServletRequest request,
      HttpSession session) throws Exception {

    checkOwner(no, session);

    if (!boardService.delete(no)) {
      throw new Exception("게시글을 삭제할 수 없습니다!");
    }
    return "redirect:list";
  }

  @GetMapping("fileDelete") // 요청이 들어 왔을 때 호출될 메서드에 붙이는 애노테이션
  public String fileDelete(
      int no,
      HttpServletRequest request,
      HttpSession session) throws Exception {

    // 첨부파일 정보를 가져온다.
    AttachedFile attachedFile = boardService.getAttachedFile(no);
    // 게시글의 작성자가 로그인 사용자인지 검사한다.
    Member loginMember = (Member) session.getAttribute("loginMember");
    Board board = boardService.get(attachedFile.getBoardNo());

    if (board.getWriter().getNo() != loginMember.getNo()) {
      throw new Exception("게시글 작성자가 아닙니다.");
    }
    // 첨부파일을 삭제한다.
    if (!boardService.deleteAttachedFile(no)) {
      throw new Exception("게시글 첨부파일을 삭제할 수 없습니다!");
    }
    return "redirect:detail?no=" + board.getNo();
  }
}
