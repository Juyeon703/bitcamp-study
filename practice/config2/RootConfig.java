package config2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;


// 스프링 IoC 컨테이너의 설정을 수행하는 클래스
// 1) DB 커넥션 객체 관리자 준비 : DataSource
// 2) 트랜잭션 관리자 준비 : PlatformTransactionManager
// 3) 어떤 패키지에 있는 객체를 자동으로 생성할 것인지 지정한다. : @ComponentScan

@ComponentScan(
    value="com.bitcamp.board", //하위패키지를 다 뒤져서 클래스 객체를 생성
    excludeFilters = @Filter(type = FilterType.REGEX, pattern = "com.bitcamp.board.controller.*")) // 여기 있는 필터는 제외해라
// - com.bitcamp.board 패키지 및 그 하위 패키지에 소속된 클래스 중에서
// @Component, @Controller, @Service, @Repository 등의 애노테이션이 붙은 클래스를 찾아
// 객체를 생성한다.
public class RootConfig {

  public RootConfig() {
    System.out.println("RootConfig() 생성자 호출됨!");

  }
}
