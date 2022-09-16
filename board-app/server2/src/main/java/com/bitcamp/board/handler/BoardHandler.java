/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board.handler;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;

public class BoardHandler {

  private BoardDao boardDao;

  public BoardHandler(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  //  // 템플릿 메서드 패턴(template method pattern): 
  //  // - 수퍼클래스의 execute()에서 동작의 전체적인 흐름을 정의하고(틀을 만들고),
  //  // - 서브클래스의 service()에서 동작을 구체적으로 정의한다.(세부적인 항목을 구현)
  //  @Override
  //  public void service(int menuNo, DataInputStream in, DataOutputStream out) {
  //    try {
  //      switch (menuNo) {
  //        // case 0: 
  //        // //핸들러를 종료할 때 breadcrumb 메뉴에 등록된 이 핸들러의 이름을 꺼낸다.
  //        // // App.breadcrumbMenu.pop();
  //        // return;
  //        case 1: this.onList(in, out); break;
  //        case 2: this.onDetail(in, out); break;
  //        case 3: this.onInput(in, out); break;
  //        case 4: this.onDelete(in, out); break;
  //        case 5: this.onUpdate(in, out); break;
  //        // default: System.out.println("메뉴 번호가 옳지 않습니다!");
  //      }
  //    } catch (Exception e) {
  //      throw new RuntimeException(e);
  //    }
  //  }

  public void list(Map<String, String> paramMap, PrintWriter out) throws Exception{

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<style>");
    out.println("tr:hover {");
    out.println("  background-color: navy;");
    out.println("  color: white;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글</h1>");
    out.println("<a href='form'>새 글</a>");
    out.println("<table border='1'>");
    out.println("  <tr> ");
    out.println("    <th>번호</th>");
    out.println("    <th>제목</th>");
    out.println("    <th>조회수</th>");
    out.println("    <th>작성자</th>");
    out.println("    <th>등록일</th>");
    out.println("  </tr>");

    List<Board> boards = boardDao.findAll();
    for (Board board : boards) {
      //      tempOut.printf("%d\t%s\t%d\t%d\t%s\n",
      //          board.no, board.title, board.viewCount, board.memberNo, board.createdDate);
      out.println("  <tr> ");
      out.printf("    <td>%d</td>", board.no);
      out.printf("    <td><a href='/board/detail?no=%d'>%s</a></td>", board.no, board.title);
      out.printf("    <td>%d</td>", board.viewCount);
      out.printf("    <td>%d</td>", board.memberNo);
      out.printf("    <td>%s</td>", board.createdDate);
      out.println("  </tr>");
    }
    out.println("</table>");
    out.println("<p><a href='/'>메인</a></p>");
    out.println("</body>");
    out.println("</html>");
  }

  public void detail(Map<String, String> paramMap, PrintWriter out) throws Exception{
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글 상세정보</h1>");

    int boardNo = Integer.parseInt(paramMap.get("no"));
    Board board = boardDao.findByNo(boardNo);

    if (board == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
    } else {
      out.println("<form action='update'>");

      out.println("<table border='1'>");
      out.println("  <tr> ");
      out.printf("    <th>번호</th><td><input name='no' type='number' value='%d' readonly></td>", board.no); 
      // readonly의 값은 브라우저가 사용하지 않아서 아무거나 넣어도됨, 적지 않아도됨
      out.println("  </tr>");
      out.println("  <tr> ");
      out.printf("    <th>제목</th><td><input name='title' type='text' value='%s'></td>", board.title);
      out.println("  </tr>");
      out.println("  <tr> ");
      out.printf("    <th>내용</th><td><textarea name='content' rows='10' cols='60'>%s</textarea></td>", board.content);
      out.println("  </tr>");
      out.println("  <tr> ");
      out.printf("    <th>조회수</th><td>%d</td>", board.viewCount);
      out.println("  </tr>");
      out.println("  <tr> ");
      out.printf("    <th>작성자</th><td>%d</td>", board.memberNo);
      out.println("  </tr>");
      out.println("  <tr> ");
      out.printf("    <th>등록일</th><td>%s</td>", board.createdDate);
      out.println("  </tr>");
      out.println("</table>");

      out.println("<p>");
      out.println("<button type='submit'>변경</button>");
      out.printf("<a href='delete?no=%d'>삭제</a>", board.no);
      out.println("</p>");
      out.println("</form>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  //    Prompt prompt = new Prompt(in, out);
  //
  //    int boardNo = 0;
  //    while (true) {
  //      try {
  //        boardNo = prompt.inputInt("조회할 게시글 번호? ");
  //        break;
  //      } catch (Exception ex) {
  //        out.writeUTF("입력 값이 옳지 않습니다!");
  //      }
  //    }
  //    Board board = boardDao.findByNo(boardNo);
  //
  //    try (StringWriter strOut = new StringWriter();
  //        PrintWriter tempOut = new PrintWriter(strOut);) {
  //
  //      if (board == null) {
  //        tempOut.println("해당 번호의 게시글이 없습니다!");
  //        out.writeUTF(strOut.toString());
  //        return;
  //      }
  //      tempOut.printf("번호: %d\n", board.no);
  //      tempOut.printf("제목: %s\n", board.title);
  //      tempOut.printf("내용: %s\n", board.content);
  //      tempOut.printf("조회수: %d\n", board.viewCount);
  //      tempOut.printf("작성자: %d\n", board.memberNo);
  //      tempOut.printf("등록일: %s\n", board.createdDate);
  //      out.writeUTF(strOut.toString());
  //    }
  //  }

  public void delete(Map<String, String> paramMap, PrintWriter out) throws Exception{
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>"); // 시간지나면 목록페이지로 넘어가도록
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글 삭제</h1>");

    int no = Integer.parseInt(paramMap.get("no"));

    if (boardDao.delete(no) == 0) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
    } else {
      out.println("<p>해당 게시글을 삭제했습니다!</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  //    Prompt prompt = new Prompt(in, out);
  //    int boardNo = 0;
  //    while (true) {
  //      try {
  //        boardNo = prompt.inputInt("삭제할 게시글 번호? ");
  //        break;
  //      } catch (Exception ex) {
  //        out.writeUTF("입력 값이 옳지 않습니다!");
  //      }
  //    }
  //
  //    if (boardDao.delete(boardNo) == 1) {
  //      out.writeUTF("삭제하였습니다.");
  //    } else {
  //      out.writeUTF("해당 번호의 게시글이 없습니다!");
  //    }


  public void update(Map<String, String> paramMap, PrintWriter out) throws Exception{
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>"); // 시간지나면 목록페이지로 넘어가도록
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    Board board = new Board();
    board.no = Integer.parseInt(paramMap.get("no"));
    board.title = paramMap.get("title");
    board.content = paramMap.get("content");

    if (boardDao.update(board) == 0) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
    } else {
      out.println("<p>해당 게시글을 변경했습니다!</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  //    Prompt prompt = new Prompt(in, out);
  //    int boardNo = 0;
  //    while (true) {
  //      try {
  //        boardNo = prompt.inputInt("변경할 게시글 번호? ");
  //        break;
  //      } catch (Throwable ex) {
  //        out.writeUTF("입력 값이 옳지 않습니다!");
  //      }
  //    }
  //
  //    Board board = boardDao.findByNo(boardNo);
  //    if (board == null) {
  //      out.writeUTF("해당 번호의 게시글이 없습니다!");
  //      return;
  //    }
  //
  //    board.title = prompt.inputString("제목?(" + board.title + ") ");
  //    board.content = prompt.inputString(String.format("내용?(%s) ", board.content));
  //
  //    String input = prompt.inputString("변경하시겠습니까?(y/n) ");
  //    if (input.equals("y")) {
  //
  //      if (boardDao.update(board) == 1) {
  //        out.writeUTF("변경했습니다.");
  //      } else {
  //        out.writeUTF("변경 실패입니다!");
  //      } 
  //    } else {
  //      out.writeUTF("변경 취소했습니다.");
  //    } 
  //  }

  public void form(Map<String, String> paramMap, PrintWriter out) {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글 입력</h1>");

    out.println("<form action='add'>");

    out.println("<table border='1'>");
    out.println("  <tr> ");
    out.println("    <th>제목</th><td><input name='title' type='text' size='60'></td>");
    out.println("  </tr>");
    out.println("  <tr> ");
    out.println("    <th>내용</th><td><textarea name='content' rows='10' cols='60'></textarea></td>");
    out.println("  </tr>");
    out.println("  <tr> ");
    out.println("    <th>작성자</th><td><input name='writerNo' type='number' size='5'></td>");
    out.println("  </tr>");
    out.println("</table>");

    out.println("<p>");
    out.println("<button type='submit'>등록</button>");
    out.println("<a href='list'>목록</a>");
    out.println("</p>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

  public void add(Map<String, String> paramMap, PrintWriter out) throws Exception{
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>"); // 시간지나면 목록페이지로 넘어가도록
    out.println("</head>");

    out.println("<body>");
    out.println("<h1>게시글 등록</h1>");

    Board board = new Board();
    board.title = paramMap.get("title");
    board.content = paramMap.get("content");
    board.memberNo = Integer.parseInt(paramMap.get("writerNo"));

    if (boardDao.insert(board) == 0) {
      out.println("<p>게시글을 등록할 수 없습니다!</p>");
    } else {
      out.println("<p>게시글을 등록했습니다!</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
  //    Prompt prompt = new Prompt(in, out);
  //    Board board = new Board();
  //
  //    board.title = prompt.inputString("제목? ");
  //    board.content = prompt.inputString("내용? ");
  //    board.memberNo = prompt.inputInt("작성자? ");
  //
  //    boardDao.insert(board);
  //    out.writeUTF("게시글을 등록했습니다.");
}





