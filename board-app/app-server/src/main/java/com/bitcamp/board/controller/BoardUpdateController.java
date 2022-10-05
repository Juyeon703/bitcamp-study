package com.bitcamp.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;
import com.bitcamp.board.service.BoardService;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 최대 10MB까지 업로드 허용
@WebServlet("/board/update")
public class BoardUpdateController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) this.getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      request.setCharacterEncoding("UTF-8");
      //      // 멀티파트 데이터 처리하기
      //      // 1) 클라이언트가 보낸 멀티파트 데이터를 임시 보관할 객체 준비
      //      // - 임시폴더 HDD(SSD)에 저장한다.
      //      DiskFileItemFactory factory = new DiskFileItemFactory(); 
      //
      //      // 2) 멀티파트 데이터를 분석할 객체 준비
      //      // - 멀티파트 데이터를 파트 별로 쪼개서 이름과 데이터를 분리하여
      //      //   DiskFileItemFactory를 이용하여 임시 폴더에 저장한다.
      //      ServletFileUpload upload = new ServletFileUpload(factory);
      //
      //      // 3) HttpServletRequest 객체를 통해 데이터를 읽어서 멀티파트 데이터를 처리한다.
      //      // - 각각의 파트 정보는 FileItem 객체에 담긴다.
      //      // - 그 FileItem 객체들을 List에 담아 리턴한다.
      //      // - 물론 업로드 된 파일의 데이터를 DiskFileItemFactory에서 관리하고 있다.
      //      List<FileItem> items = upload.parseRequest(request);
      //
      //      // 클라이언트가 멀티파트로 보낸 데이터를 저장할 도메인 객체를 준비한다.
      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      // 첨부파일명을 저장할 컬렉션 객체 준비
      List<AttachedFile> attachedFiles = new ArrayList<>();
      // 임시 폴더에 저장된 파일을 옮길 폴더 경로 알아내기
      String dirPath = this.getServletContext().getRealPath("/board/files");

      Collection<Part> parts = request.getParts(); // 첨부파일만 넘어오는것이 아님

      for(Part part : parts) {
        if (!part.getName().equals("files") || part.getSize() == 0) {
          continue;
        }
        // = if (part.getName().equals("files")) continue;

        // 다른 클라이언트가 보낸 파일명과 중복되지 않도록 임의의 새 파일명을 생성한다.
        String filename = UUID.randomUUID().toString();
        // FileItem 객체가 가리키는 임시 폴더에 저장된 파일을
        // 우리가 지정한 디렉토리로 옮긴다.
        // 이때 파일명은 원래의 이름 대신 UUID로 생성한 이름을 사용한다
        part.write(dirPath + "/" + filename);
        attachedFiles.add(new AttachedFile(filename));
      }

      // Board 객체에서 파일명 목록을 담고 있는 컬렉션 객체를 저장한다.
      board.setAttachedFiles(attachedFiles);
      //      // 첨부파일명을 저장할 컬렉션 객체 준비
      //      List<AttachedFile> attachedFiles = new ArrayList<>();
      //
      //      // 첨부파일을 저장할 OS의 파일시스템 경로를 알아낸다.
      //      String dirPath = request.getServletContext().getRealPath("/board/files");
      //
      //      // 각 파트의 데이터를 꺼내 Board 객체에 담는다.
      //      for (FileItem item : items) {
      //        if (item.isFormField()) { // 일반 입력 값이라면
      //          String paramName = item.getFieldName();
      //          String paramValue = item.getString("UTF-8");
      //
      //          switch (paramName) {
      //            case "no" : board.setNo(Integer.parseInt(paramValue));
      //            case "title" : board.setTitle(paramValue);
      //            case "content" : board.setContent(paramValue);
      //          } 
      //    } else {
      //      // 첨부파일을 저장할 때 사용할 파일명을 생성한다.
      //      String filename = UUID.randomUUID().toString();
      //      // 지정한 위치에 생성한 이름을로 첨부파일을 저장한다.
      //      item.write(new File(dirPath + "/" + filename));
      //      // 첨부파일의 이름을 DB에 저장할 수 있도록 List에 보관한다.
      //      attachedFiles.add(new AttachedFile(filename));
      //    }
      //  }
      //
      //  // Board 객체에서 파일명 목록을 담고 있는 컬렉션 객체를 저장한다.
      //  board.setAttachedFiles(attachedFiles);

      //      request.setCharacterEncoding("UTF-8");
      //      Board board = new Board();
      //      board.setNo(Integer.parseInt(request.getParameter("no")));
      //      board.setTitle(request.getParameter("title"));
      //      board.setContent(request.getParameter("content"));

      // 게시글 작성자인지 검사한다.
      Member loginMember = (Member) request.getSession().getAttribute("loginMember");
      if (boardService.get(board.getNo()).getWriter().getNo() != loginMember.getNo()) {
        throw new Exception("게시글 작성자가 아닙니다.");
      }

      //      // 게시글 변경
      //      if (boardDao.update(board) == 0) {
      //        throw new Exception("게시글 변경 실패!");
      //      }
      //
      //      // 첨부파일 추가
      //      boardDao.insertFiles(board);
      if (!boardService.update(board)) {
        throw new Exception("게시글 변경할 수 없습니다!");
      }
      // Refresh:
      // - 응답 헤더 또는 HTML 문서에 refresh를 삽입할 수 있다.
      // - 응답 프로토콜
      //      Content-Type: text/html;charset=UTF-8
      //      Refresh: 30;url=list;
      //      Connection: keep-alive
      //      Content-Length: 254
      //      Date: Mon, 26 Sep 2022 05:24:28 GMT
      //      Keep-Alive: timeout=20
      //      <!DOCTYPE html>
      //      <html>
      //      <head>
      //      <meta charset="UTF-8">
      //      <title>bitcamp</title>
      //      <meta http-equiv='Refresh' content='1; url=list'>
      //      </head>
      //      <body>
      //      <h1>게시글 변경(JSP+Servlet)</h1>
      //      <p>해당 게시글을 변경했습니다.</p>
      //      </body>
      //      </html>

      // 자바코드 :
      //      response.setHeader("Refresh", "1;url=list"); // 응답 헤더에 refresh 명령 삽입
      //      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      //      request.getRequestDispatcher("/board/update.jsp").include(request, response); //JSP를 실행한 후 리턴된다.
      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
