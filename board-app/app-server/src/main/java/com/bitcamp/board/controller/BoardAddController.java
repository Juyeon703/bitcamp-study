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
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;

// Servlet API에서 제공하는 multipart/form-data 처리기를 사용하려면
// 서블릿에 다음 애노테이션으로 설정해야한다.
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 최대 10MB까지 업로드 허용
@WebServlet("/board/add")
public class BoardAddController extends HttpServlet{
  private static final long serialVersionUID = 1L;

  BoardDao boardDao;

  @Override
  public void init() {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      //
      //      // URL 디코딩 한 바이트를 UTF-16으로 변환하기 전에
      //      // 그 바이트의 characterset이 무엇인지 알려줘야 한다.
      //      // 안 알려주면 그 디코딩 바이트가 ASCII코드라고 간주한다.
      //      // UTF-8코드를 ASCII 잘못 판단하니까 UTF-16으로 바꿀 때 오류가 발생하는 것이다.
      //      // 물론 영어나 숫자는 ASCII코드와 UTF-16코드가 같기 때문에 UTF-16으로 변환하더라도 문제가 되지않는다.
      //      // 그러나 한글은 UTF-8코드의 3바이트를 묶어서 UTF-16의 2바이트로 변환해야 하는데
      //      // 영어라고 간주하고 각각의 1바이트를 2바이트로 변환하니까 문제가 발생하는 것이다.
      request.setCharacterEncoding("UTF-8");
      //      // = multipart/form-data 형식으로 요청한 경우
      //      // 요청 데이터의 인코딩을 다음과 같이 지정할 수 없다.
      //
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
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      // 첨부파일명을 저장할 컬렉션 객체 준비
      List<AttachedFile> attachedFiles = new ArrayList<>();
      // 임시 폴더에 저장된 파일을 옮길 폴더 경로 알아내기
      String dirPath = this.getServletContext().getRealPath("/board/files");

      Collection<Part> parts = request.getParts(); // 첨부파일만 넘어오는것이 아님

      for(Part part : parts) {
        if (!part.getName().equals("files")) {
          continue;
        }
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

      //      board.setTitle(request.getParameter("title"));
      //      board.setContent(request.getParameter("content"));

      // 로그인 사용자 정보는 파라미터로 받아서는 안된다.
      // 반드시 세션에서 꺼내 써야한다.
      // 왜? 클라이언트가 다른 사용자 정보를 보낼 수 있기 때문이다. 
      // Board 객체에 로그인 사용자 정보를 저장한다.
      Member loginMember = (Member)request.getSession().getAttribute("loginMember");
      board.setWriter(loginMember);

      if (boardDao.insert(board) == 0) {
        throw new Exception("게시글 등록 실패!");
      }

      //JSP에게 UI생성을 위임한다.
      // Refresh:
      // - 응답 헤더 또는 HTML 문서에 refresh를 삽입할 수 있다.

      //      response.setHeader("Refresh", "1;url=list"); // 응답 헤더에 refresh 명령 삽입
      //      response.setContentType("text/html; charset=UTF-8"); // JSP가 출력할 콘텐트의 MIME 타입 설정
      //      request.getRequestDispatcher("/board/add.jsp").include(request, response); //JSP를 실행한 후 리턴된다.

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
      response.sendRedirect("list");
    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); //JSP를 실행한 후 리턴된다.
    }
  }
}
