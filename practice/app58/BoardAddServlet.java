/*
 * 게시글 메뉴 처리 클래스
 */
package app58;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;


//@WebServlet(value="/board/add")
public class BoardAddServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  BoardDao boardDao;

  @Override
  public void init() throws ServletException {
    // this는 상속클래스말고 우리 클래스에서 가져오려고 붙임
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html; charset=UTF-8");
    PrintWriter out = resp.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='1; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 입력</h1>");

    try {
      Board board = new Board();
      board.title = req.getParameter("title");
      board.content = req.getParameter("content");
      board.memberNo = Integer.parseInt(req.getParameter("writerNo"));

      if (boardDao.insert(board) == 0) {
        out.println("<p>게시글을 등록할 수 없습니다!</p>");

      } else {
        out.println("<p>게시글을 등록했습니다.</p>");
      }
    } catch (Exception e) {
      out.println("<p>실행 중 오류발생!</p>");
    }

    out.println("</body>");
    out.println("</html>");

  }


}




