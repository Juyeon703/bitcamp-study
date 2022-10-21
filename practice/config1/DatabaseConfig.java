package config1;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;



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
    System.out.println("createTransactionManager() 호출됨!");
    return  new DataSourceTransactionManager(ds);
  }

  // DataSource를 생성하는 메서드를 호출하라고 표시한다.
  // 메서드가 리턴한 객체는 @Bean 애노테이션에 지정된 이름으로 컨테이너에 보관될 것이다.
  @Bean
  public DataSource dataSource() {
    System.out.println("createDataSource() 호출됨!");
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.mariadb.jdbc.Driver");
    ds.setUrl("jdbc:mariadb://localhost:3306/studydb");
    ds.setUsername("study");
    ds.setPassword("1111");
    return ds;
  }
}
