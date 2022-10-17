package com.bitcamp.board.listener;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import com.bitcamp.board.config.AppConfig;
import com.bitcamp.board.filter.AdminCheckFilter;
import com.bitcamp.board.filter.LoginCheckFilter;

// 웹애플리케이션이 시작되었을 때 공유할 자원을 준비시키거나 해제하는 일을 한다.
//
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) 
@WebListener
public class ContextLoaderListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("ContextLoaderListener.contextInitialized()");
    try {

      // 웹 기능이 포함된 스프링 IoC 컨테이너준비
      AnnotationConfigWebApplicationContext iocContainer = 
          new AnnotationConfigWebApplicationContext();
      iocContainer.register(AppConfig.class);
      //iocContainer.refresh(); // 자바 config 클래스(AppConfig)에 설정된 대로 객체를 설정한다.
      // refresh는 webcontext가 아닌 그냥 context 사용시 필요했던거임
      ServletContext ctx = sce.getServletContext();
      // 웹 애플리케이션의 루트 경로를 ServletContext
      ctx.setAttribute("contextPath", ctx.getContextPath());

      // 자바 코드로 서블릿 객체를 직접 생성하여 서버에 등록하기
      DispatcherServlet servlet = new DispatcherServlet(iocContainer);
      Dynamic config = ctx.addServlet("app", servlet);
      config.addMapping("/app/*");
      config.setMultipartConfig(new MultipartConfigElement(
          this.getClass().getAnnotation(MultipartConfig.class)));
      config.setLoadOnStartup(1); // 웹 애플리케이션을 시작할 때 프론트 컨트롤러를 자동 생성한다.

      // 우리가 만든 필터는 애노테이션을 붙여서 사용하면 되지만
      // 외부 필터를 가져와서 쓸때는 자바코드로 등록해야함
      // 필터 등록
      CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
      // "CharacterEncodingFilter" 이 이름으로 된 필터를 설정해서
      FilterRegistration.Dynamic filterConfig = ctx.addFilter("CharacterEncodingFilter", filter);
      // 필터 설정
      filterConfig.addMappingForServletNames(
          EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), // EnumSet은 상수 배열
          // "DispatcherServlet"에 요청이 들어왔을때, 다른 서블릿을 통해서 포워드로 실행될때, 인클루드로 실행될 때 모두 필터를 실행할건데
          // 그 필터는 "CharacterEncodingFilter" 이것임
          false, // 요청이 들어올때 실행할것이다 <-> true는 응답할때 실행할거라는 뜻임
          "app"); // 필터를 실행할 서블릿

      //필터 등록
      AdminCheckFilter adminFilter = new AdminCheckFilter();
      FilterRegistration.Dynamic adminFilterConfig = ctx.addFilter("AdminCheckFilter", adminFilter);
      // 필터 설정
      adminFilterConfig.addMappingForUrlPatterns(
          EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), 
          false, 
          "/app/member/*"); // 이 url에 해당하는 경로가 들어왔을때마다 필터를 실행할것임

      //필터 등록
      LoginCheckFilter loginFilter = new LoginCheckFilter();
      FilterRegistration.Dynamic loginFilterConfig = ctx.addFilter("LoginCheckFilter", loginFilter);
      // 필터 설정
      loginFilterConfig.addMappingForUrlPatterns(
          EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), 
          false, 
          "/app/*");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
