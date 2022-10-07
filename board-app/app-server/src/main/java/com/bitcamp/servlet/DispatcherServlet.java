package com.bitcamp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



// 역할 :
// 페이지 컨트롤러의 앞쪽에서 클라이언트 요청을 받는 일을 한다.
// 클라이언트가 요청한 경로에 따라 적절한 페이지 컨트롤러를 실행한다.
// 페이지 컨트롤러의 공통 기능을 수행한다.

//@WebServlet(value = "/service/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      // 프론트 컨트롤러를 경유해서 실행할 페이지 컨트롤러의 경로를 알아낸다.
      // "/service" 다음에 오는 경로, 즉 *에 해당하는 경로를 리턴한다.
      String pathInfo = req.getPathInfo();

      // 페이지 컨트롤러를 찾는다.
      Controller controller = (Controller) req.getServletContext().getAttribute(pathInfo);
      if (controller == null) {
        throw new Exception("페이지 컨트롤러가 없습니다!");
      }

      // 페이지 컨트롤러를 실행한다.
      resp.setContentType("text/html;charset=UTF-8");
      String viewName = controller.execute(req, resp);
      //    RequestDispatcher 요청배달자 = req.getRequestDispatcher(pathInfo);
      //    요청배달자.include(req, resp);


      //    // include되는 서블릿은 응답헤더에 쿠키를 넣을수없지만? 페이지 컨트롤러는 더이상 서블릿이 아니기 때문에 
      //    // 직접 넣으면 된다?
      //    // 페이지 컨트롤러가 추가한 쿠키가 있다면, 응답헤더에 추가시킨다.
      //    @SuppressWarnings("unchecked")
      //    List<Cookie> cookies = (List<Cookie>) req.getAttribute("cookies");
      //    if (cookies != null) {
      //      for (Cookie cookie : cookies) {
      //        resp.addCookie(cookie);
      //      }
      //    }

      // 페이지 컨트롤러에서 오류가 발생했다면 
      // 페이지 컨트롤러를 실행한 후에 페이지 컨트롤러가 지정한 뷰 컴포넌트를 실행한다.
      //    String viewName = (String) req.getAttribute("viewName");
      //    if (viewName != null) { // 페이지 컨트롤러를 정상적으로 실행했다면,
      if (viewName.startsWith("redirect:")) { // 예) "redirect:list"
        resp.sendRedirect(viewName.substring(9)); // 예) "list" <-- 리다이렉트할 URL을 잘라낸다.
      } else {
        req.getRequestDispatcher(viewName).include(req, resp); 
      }
    } catch (Exception e) { // 페이지 컨트롤러를 실행하다가 오류가 발생했다면
      req.setAttribute("exception", e);
      req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
  }
}
