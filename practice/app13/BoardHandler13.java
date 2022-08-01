/*
 * 게시글 메뉴 처리 클래스
 */
package com.bitcamp.board;

import java.util.Date;

public class BoardHandler {

  String title;

  //게시글 목록을 관리할 객체 준비
  BoardList111 boardList = new BoardList111();  
  public BoardHandler() {
    this.title = "게시판";
  }
  // 제목을 입력 받는 생성자
  BoardHandler(String title) {
    this.title = title;
  }


  void execute() {
    // App클래스에서 이 메서드를 호출할 때 BoardHandler의 인스턴스 주소를 줄 것이다.
    // 그 주소는 this 라는 내장 변수에 보관될 것이다.

    while (true) {
      System.out.printf("%s: \n", this.title);
      System.out.println("  1: 목록");
      System.out.println("  2: 상세보기");
      System.out.println("  3: 등록");
      System.out.println("  4: 삭제");
      System.out.println("  5: 변경");
      System.out.println();
      int menuNo = Prompt111.inputInt("메뉴를 선택하세요[1..5](0: 이전) ");
      displayHeadLine();

      // 다른 인스턴스 메서드를 호출할 때 this에 보관된 인스턴스 주소를 사용한다.
      switch (menuNo) {
        case 0 : return;
        case 1 : this.onList(); break;
        case 2 : this.onDetail(); break;
        case 3 : this.onInput(); break;
        case 4 : this.onDelete(); break;
        case 5 : this.onUpdate(); break;
        default : System.out.println("메뉴 번호가 옳지 않습니다!");
      }
      displayBlankLine();
    } //게시판 while
  }

  static void displayHeadLine() {
    System.out.println("------------------------------------------------");
  }

  static void displayBlankLine() {
    System.out.println();
  }

  void onList() {
    //인스턴스 메서드는 호출될 때 넘겨 받은 인스턴스 주소를
    // this 라는 내장변수에 보관한다.
    //날짜 정보에서 값을 추출하여 특정 포맷의 문자열로 만들어줄 도구를 준비
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    System.out.printf("[%s 목록]\n", this.title);
    System.out.println("번호  제목  조회수  작성자  등록일");


    //boardList 인스턴스에 들어 있는 데이터 목록을 가져온다.
    Board111[] list = this.boardList.toArray();

    for (Board111 board : list) {
      Date date = new Date(board.createdDate);
      String dateStr = formatter.format(date); 
      System.out.printf("%d\t%s\t%d\t%s\t%s\n", // 포멧에 따라 출력
          board.no, board.title, board.viewCount, board.writer, dateStr);
    }
  }


  void onDetail() {
    System.out.printf("[%s 상세보기]\n", this.title);
    System.out.println();

    int boardNo = Prompt111.inputInt("조회할 게시글 번호? "); 

    //해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
    Board111 board = this.boardList.get(boardNo);


    // 사용자가 입력한 번호에 해당하는 게시글을 못찾았다면
    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      System.out.println();
      return;
    }

    System.out.printf("번호 : %d\n", board.no);
    System.out.printf("제목 : %s\n", board.title);
    System.out.printf("내용 : %s\n", board.content);
    System.out.printf("조회수 : %d\n", board.viewCount);
    System.out.printf("작성자 : %s\n", board.writer);
    Date date = new Date(board.createdDate);
    System.out.printf("등록일 : %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }


  void onInput() {
    System.out.printf("[%s 등록]\n", this.title);
    System.out.println();

    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.writer = Prompt111.inputString("작성자? ");
    board.password = Prompt111.inputString("암호? ");
    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();

    //새로 만든 인스턴스 주소를 레퍼런스 배열에 저장
    this.boardList.add(board);

    System.out.println("게시글을 등록했습니다.");
  }

  void onDelete() {
    System.out.printf("[%s 삭제]\n", this.title);
    System.out.println();

    int boardNo = Prompt111.inputInt("삭제할 게시글 번호? "); 

    if (boardList.remove(boardNo)) {
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("해당 번호의 게시글이 없습니다!");
    }
  }

  void onUpdate() {
    System.out.printf("[%s 변경]\n", this.title);
    System.out.println();

    int boardNo = Prompt111.inputInt("변경할 게시글 번호? "); 

    Board111 board = this.boardList.get(boardNo);

    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    String newTitle = Prompt111.inputString("제목?(" + board.title +") ");
    String newContent = Prompt111.inputString(String.format("내용?(%s) ", board.content));

    String input = Prompt111.inputString("변경하시겠습니까?(y/n)");
    if (input.equals("y")) {
      board.title = newTitle;
      board.content = newContent;
      System.out.println("변경했습니다.");
    } else {
      System.out.println("변경 취소했습니다.");
    }
  }


} //class


