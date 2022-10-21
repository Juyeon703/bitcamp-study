package com.bitcamp.board.filter;

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
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    System.out.println(httpRequest.getServletContext());
    System.out.println(httpRequest.getServletPath());

    String servletPath = httpRequest.getServletPath();
    if (servletPath.toLowerCase().endsWith("add") || // 경로 끝이 add, update, delete으로 끝난다면
        servletPath.toLowerCase().endsWith("update") ||
        servletPath.toLowerCase().endsWith("delete")) {
      Member loginMember = (Member) httpRequest.getSession().getAttribute("loginMember");
      if (loginMember == null) { // 로그인 하지 않았다면
        httpResponse.sendRedirect(httpRequest.getContextPath()/*/app 이름이 바뀔 경우가 있기 때문*/ +"/auth/form");
        return;
      }
    }
    chain.doFilter(request, response);

  }

}
