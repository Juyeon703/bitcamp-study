package com.bitcamp.board.listener;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.web.servlet.DispatcherServlet;

// 웹애플리케이션이 시작되었을 때 공유할 자원을 준비시키거나 해제하는 일을 한다.
//@WebListener
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class ContextLoaderListener2 implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    System.out.println("공유 자원을 준비 중!!");
    try {

      ServletContext ctx = sce.getServletContext();

      //      DataSource ds = new DataSource(
      //          "org.mariadb.jdbc.Driver", 
      //          "jdbc:mariadb://localhost:3306/studydb", 
      //          "study", 
      //          "1111");

      //      TransactionManager txManager = new TransactionManager(ds);

      //      BoardDao boardDao = new MariaDBBoardDao(ds);
      //      MemberDao memberDao = new MariaDBMemberDao(ds);
      //
      //      BoardService boardService = new DefaultBoardService(boardDao, txManager);
      //      MemberService memberService = new DefaultMemberService(memberDao);

      //      ctx.setAttribute("boardService", new DefaultBoardService(boardDao, txManager));
      //      ctx.setAttribute("memberService", new DefaultMemberService(memberDao));
      //      ctx.setAttribute("/board/list", new BoardListController(boardService));
      //      ctx.setAttribute("/board/detail", new BoardDetailController(boardService));
      //      ctx.setAttribute("/board/form", new BoardFormController());
      //      ctx.setAttribute("/board/add", new BoardAddController(boardService));
      //      ctx.setAttribute("/board/delete", new BoardDeleteController(boardService));
      //      ctx.setAttribute("/board/fileDelete", new BoardFileDeleteController(boardService));
      //      ctx.setAttribute("/board/update", new BoardUpdateController(boardService));
      //
      //      ctx.setAttribute("/auth/form", new LoginFormController());
      //      ctx.setAttribute("/auth/login", new LoginController(memberService));
      //      ctx.setAttribute("/auth/logout", new LogoutController());
      //
      //      ctx.setAttribute("/member/list", new MemberListController(memberService));
      //      ctx.setAttribute("/member/detail", new MemberDetailController(memberService));
      //      ctx.setAttribute("/member/form", new MemberFormController());
      //      ctx.setAttribute("/member/add", new MemberAddController(memberService));
      //      ctx.setAttribute("/member/delete", new MemberDeleteController(memberService));
      //      ctx.setAttribute("/member/update", new MemberUpdateController(memberService));

      // 자바 코드로 프론트 컨트롤러를 직접 생성하여 서버에 등록하기
      DispatcherServlet servlet = new DispatcherServlet();
      Dynamic config = ctx.addServlet("DispatcherServlet", servlet);
      config.addMapping("/service/*"); 

      config.setMultipartConfig(new MultipartConfigElement(
          this.getClass().getAnnotation(MultipartConfig.class)));

      // 프론트 컨트롤러가 사용할 서블릿, 서비스, DAO, 트랜잭션 관리자, 데이터소스 객체는
      // 프론트 컨트롤러에서 준비한다.

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
