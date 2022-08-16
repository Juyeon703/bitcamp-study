/*
 * board 데이터 처리
 */
package com.bitcamp.board.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.servlet.Servlet;

public class BoardServlet implements Servlet{

  // 게시글 목록을 관리할 객체 준비
  private BoardDao boardDao;

  public BoardServlet(String dataName) {
    //    // 수퍼 클래스의 생성자를 호출할 때 메뉴 목록을 전달한다.
    //    // String[] menus = new String[] {"목록", "상세보기", "등록", "삭제", "변경"};
    //    super(new String[] {"목록", "상세보기", "등록", "삭제", "변경"});
    //    boardDao = new BoardDao(filename);
    //    try {
    //      boardDao.load();
    //    } catch(Exception e) {
    //      System.out.printf("%s 파일 로딩 중 오류 발생!\n", filename);
    //      e.printStackTrace(); // 오류 발자취를 추적해서 출력하라?
    //    } 
  }

  // 템플릿 메서드 패턴(template method pattern): 
  // - 수퍼클래스의 execute()에서 동작의 전체적인 흐름을 정의하고(틀을 만들고),
  // - 서브클래스의 service()에서 동작을 구체적으로 정의한다.(세부적인 항목을 구현)
  @Override
  public void service(DataInputStream in, DataOutputStream out) {
    try {

      String command = in.readUTF();


      switch (command) {
        // case 0: 
        // //핸들러를 종료할 때 breadcrumb 메뉴에 등록된 이 핸들러의 이름을 꺼낸다.
        // // App.breadcrumbMenu.pop();
        // return;
        case "findAll": 
          out.writeUTF("success");
          break;
        case "findByNo": 
          out.writeUTF("success");
          break;
        case "insert": 
          out.writeUTF("success");
          break;
        case "delete": 
          out.writeUTF("success");
          break;
        case "update": 
          out.writeUTF("success");
          break;
        default :
          out.writeUTF("fail");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}




