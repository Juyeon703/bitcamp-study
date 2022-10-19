package com.bitcamp.board.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



// @Transactional 애노테이션을 사용하여 트랜잭션을 제어할 수 있게 기능을 활성화시킨다.
@EnableTransactionManagement
// JDBC 프로퍼티 값이 저장되어 있는 .properties 파일을 로딩한다.
@PropertySource("classpath:com/bitcamp/board/config/jdbc.properties")
public class DatabaseConfig {

  public DatabaseConfig() {
    System.out.println("DatabaseConfig() 생성자 호출됨!");
  }

  // @Bean 애노테이션을 붙일 때 객체 이름을 지정하면
  // 그 이름으로 리턴 값을 컨테이너에 보관한다.
  // 이름을 지정하지 않으면 메서드 이름으로 보관한다.
  //  @Bean("transactionManager")
  @Bean
  public PlatformTransactionManager transactionManager(DataSource ds) {
    // Spring IoC 컨테이너는 이 메서드를 호출하기 전에
    // 이 메서드가 원하는 파라미터 값인 DataSource를 먼저 생성한다.
    // => createDataSource() 메서드를 먼저 호출한다.(순서를 고민할 필요 없음)
    System.out.println("PlatformTransactionManager 객체 생성!");
    return  new DataSourceTransactionManager(ds);
  }

  // DataSource를 생성하는 메서드를 호출하라고 표시한다.
  // 메서드가 리턴한 객체는 @Bean 애노테이션에 지정된 이름으로 컨테이너에 보관될 것이다.

  // ${jdbc.url} ==> .properties 파일에서 jdbc.url 이름으로 저장된 값을 가져온다.
  // @Value("...") ==> 애노테이션에 지정된 문자열을 파라미터 변수에 전달한다.
  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driverClassName}") String dirverClassName,
      @Value("${jdbc.url}") String url,
      @Value("${jdbc.username}") String username,
      @Value("${jdbc.password}") String password) {
    System.out.println("DataSource 객체 생성!");
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(dirverClassName);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }
}
