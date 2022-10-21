package filter2;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.bitcamp.board.domain.Member;

//@WebFilter("/service/*")
@Component
public class LoginCheckFilter implements Filter{

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("LoginCheckFilter.init() 실행");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    System.out.println("LoginCheckFilter.doFilter() 실행");
    // 요청 URL을 통해 로그인 여부를 검사할 지 결정한다.
    // 요청 URL은 HTTP 프로토콜과 관련된 값이다.
    // ServletRequest 타입은 HTTP 프로토콜과 관련된 기능을 다룰 수 있는 메서드가 없다.
    // ServletRequest 타입의 객체를 HttpServletRequest 객체로 형변환 해야한다.
    // 필터의 파라미터로 넘어오는 객체는 원래 HttpServletRequest 객체이기 때문에 형변환 할 수 있다.
    // 즉, HTTP 프로토콜과 관련된 기능을 쓰고 싶다면,
    // 원래 타입으로 형변환 한 다음에 사용하라!
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    // 응답 기능에 대해서도 HTTP 관련 메서드를 사용하고 싶다면 형변환 해라 -> sendRedirect 사용하고 싶음
    HttpServletResponse httpResponse = (HttpServletResponse) response;


    // 요청 URL에서 서블릿 경로만 추출한다.
    // 예) 요청 URL : http://localhost:8888/app/board/add?title=aaa&content=bbb
    //     서블릿 경로 : /board/add <== 웹 애플리케이션 경로
    //                          (http://localhost:8888/app, ?title=aaa&content=bbb)는 뺀다.

    //    String servletPath = httpRequest.getServletPath();
    // URL 매핑이 "/service/*" 형식으로 되어 있을 때
    // * 경로를 알아내려면 다음의 메서드를 호출해야 한다.
    String servletPath = httpRequest.getPathInfo();
    //    System.out.println(servletPath);

    // 콘텐트를 등록, 변경, 삭제하는 경우 로그인 여부를 확인한다.
    if (servletPath.toLowerCase().endsWith("add") || // 경로 끝이 add, update, delete으로 끝난다면
        servletPath.toLowerCase().endsWith("update") ||
        servletPath.toLowerCase().endsWith("delete")) {
      Member loginMember = (Member) httpRequest.getSession().getAttribute("loginMember");
      if (loginMember == null) { // 로그인 하지 않았다면
        httpResponse.sendRedirect(httpRequest.getContextPath()/*/app 이름이 바뀔 경우가 있기 때문*/ +"/service/auth/form");
        return;
      }
    }


    // 다음 필터를 실행한다.
    // 다음으로 실행할 필터가 없다면 원래 목적지인 서블릿이 실행될 것이다.
    chain.doFilter(request, response);

  }

}