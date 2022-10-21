package com.bitcamp.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;

//@MapperScan("com.bitcamp.board.dao")
@SpringBootApplication
@EnableTransactionManagement
@Controller
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @GetMapping("/")
  public String welcome() {
    return "welcome";
  }
}
