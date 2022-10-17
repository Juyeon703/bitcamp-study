package com.bitcamp.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@ComponentScan(
    value="com.bitcamp.board.controller.admin")//하위패키지를 다 뒤져서 클래스 객체를 생성
// - com.bitcamp.board 패키지 및 그 하위 패키지에 소속된 클래스 중에서
// @Component, @Controller, @Service, @Repository 등의 애노테이션이 붙은 클래스를 찾아
// 객체를 생성한다.
public class AdminWebConfig {

  public AdminWebConfig() {
    System.out.println("AdminWebConfig() 생성자 호출됨!");

  }

  // Spring WebMVC의 기본 ViewResolver를 교체한다.
  @Bean("viewResolver")
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class); // 주어진 URL을 처리할 객체 => JSP를 실행시켜주는 객체
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }
}
