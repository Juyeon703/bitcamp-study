package com.bitcamp.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


// 스프링 IoC 컨테이너의 설정을 수행하는 클래스
// 1) DB 커넥션 객체 관리자 준비 : DataSource
// 2) 트랜잭션 관리자 준비 : PlatformTransactionManager
// 3) 어떤 패키지에 있는 객체를 자동으로 생성할 것인지 지정한다. : @ComponentScan

@ComponentScan(
    value="com.bitcamp.board.controller", //하위패키지를 다 뒤져서 클래스 객체를 생성
    excludeFilters = @Filter(
        type = FilterType.REGEX, 
        pattern = "com.bitcamp.board.controller.admin.*")) // 여기 패키지는 제외
// - com.bitcamp.board 패키지 및 그 하위 패키지에 소속된 클래스 중에서
// @Component, @Controller, @Service, @Repository 등의 애노테이션이 붙은 클래스를 찾아
// 객체를 생성한다.
public class AppWebConfig {

  public AppWebConfig() {
    System.out.println("AppWebConfig() 생성자 호출됨!");

  }

  // multipart/form-data 형식으로 보내온 요청 데이터를
  // 도메인 객체로 받는 일을 할 도우미 객체를 등록한다.
  // 이 객체가 등록된 경우 multipart/form-data를 도메인 객체로 받을 수 있다.
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
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
