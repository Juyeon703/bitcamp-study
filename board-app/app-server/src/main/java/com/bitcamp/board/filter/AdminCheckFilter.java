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

//@WebFilter("/service/member/*")
@Component
public class AdminCheckFilter implements Filter{

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("AdminCheckFilter.init() 실행");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (httpRequest.getServletPath().startsWith("/member")) {
      System.out.println("AdminCheckFilter.doFilter() 실행");

      Member loginMember = (Member) httpRequest.getSession().getAttribute("loginMember");
      // filter 순서는 제어할 수 없음
      if (loginMember == null || !loginMember.getEmail().equals("admin@test.com")) { // 로그인이 안되었거나 관리자가 아니라면
        httpResponse.sendRedirect(httpRequest.getContextPath() +"/");
        return;
      }
    }
    chain.doFilter(request, response);
  }
}
