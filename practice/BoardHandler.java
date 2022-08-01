/*
 * 게시글 메뉴 처리 클래스
 */
package hello;

import java.util.Date;

public class BoardHandler {


  // 모든 게시판 공유하는 데이터는 클래스 변수에 저장한다.
  //왜? 클래스 변수는 클래스를 로딩할 때 한 번만 생성되기 때문이다.
  static final int DEFAULT_SIZE = 3; // final 변수 -> 상수는 한눈에 알아보기 위해 대문자 사용하는게 좋음

  // 각 게시판이 별도로 관리해야 할 데이터는 인스턴스 변수에 저장한다.
  //왜? 인스턴스 변수는 게시판 별로 생성할 수 있기 때문이다.
  int boardCount; //저장된 게시글의 개수
  /*
  static int[] no = new int[SIZE];
  static String[] title = new String[SIZE];
  static String[] content = new String[SIZE];
  static String[] writer = new String[SIZE];
  static String[] password = new String[SIZE];
  static int[] viewCount = new int[SIZE];
  static long[] createdDate = new long[SIZE]; */

  //Board 인스턴스의 주소를 저장할 레퍼런스 배열을 만든다.
  Board111[] boards;
  String title;

  // 클래스 생성자가 정의되어 있지 않으면
  //다음과 같이 파라미터가 없는 기본 생성자를 컴파일러가자동으로 추가된다.
  //  기본 생성자?
  //  - 파라미터가 없다.
  //  - 메서드 몸체는 비어 있다.
  //  - 메서드의 접근 범위는 무조건 public이다.
  public BoardHandler() {
    this.boards = new Board111[DEFAULT_SIZE];
    this.title = "게시판";
  }
  // 제목을 입력 받는 생성자
  BoardHandler(String title) {
    this.boards = new Board111[DEFAULT_SIZE];
    this.title = title;
  }
  // 배열의 기본 크기를 설정하는 생성자
  BoardHandler(int initSize) {
    this.boards = new Board111[initSize];
    this.title = "게시판";
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

    for (int i = 0; i < this.boardCount; i++) {
      Board111 board = this.boards[i];
      // 밀리초 데이터 => Date 도구함으로 날짜 정보를 설정
      Date date = new Date(board.createdDate);

      //날짜 => "yyyy-MM-dd" 형식의 문자열
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
    Board111 board = null;
    for (int i = 0; i < this.boardCount; i++) {
      if (this.boards[i].no == boardNo) {
        board = this.boards[i];
        break;
      }
    }

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
    // Date 도구함의 도구를 쓸 수 있도록 데이터를 준비시킨다.
    // new Date(밀리초) => 지정한 밀리초를 가지고 날짜 관련 도구를 사용할 수 있도록 설정한다.
    // Date date
    //      => createdDate 밀리초를 가지고 설정한 날짜 정보 
    Date date = new Date(board.createdDate);

    //Date 도구함을 통해 설정한 날짜 정보를 가지고 printf()를 실행한다.
    // %tY : date에 설정된 날짜 정보에서 년도만 추출한다.
    System.out.printf("등록일 : %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);
  }


  void onInput() {
    System.out.printf("[%s 등록]\n", this.title);
    System.out.println();

    // 배열의 크기를 초과하지 않았는지 검사한다.
    // 배열의 크기를 초과하면 배열 크기를 50% 증가시킨다.
    if (this.boardCount == this.boards.length) {
      // 새로만들 배열의 크기를 계산한다.
      int newSize = this.boards.length + (this.boards.length >> 1);
      // 새 배열 준비
      Board111[] newArray = new Board111[newSize];

      //기존 배열의 값을 새 배열에 넣는다.
      for (int i = 0; i < this.boards.length; i++) {
        newArray[i] = this.boards[i];
      }
      // 기존 배열(주소)을 버리고 새 배열(주소)을 사용한다.
      this.boards = newArray;
    }

    Board111 board = new Board111();
    board.title = Prompt111.inputString("제목? ");
    board.content = Prompt111.inputString("내용? ");
    board.writer = Prompt111.inputString("작성자? ");
    board.password = Prompt111.inputString("암호? ");

    /*        if (boardCount == 0) {
        no[boardCount] = 1;
      } else {
        no[boardCount] = no[boardCount - 1] + 1;
      } */
    board.no = this.boardCount == 0 ? 1 : this.boards[this.boardCount - 1].no + 1;

    board.viewCount = 0;
    board.createdDate = System.currentTimeMillis();

    //새로 만든 인스턴스 주소를 레퍼런스 배열에 저장
    this.boards[this.boardCount] = board;

    this.boardCount++;
    System.out.println("게시글을 등록했습니다.");
  }

  void onDelete() {
    System.out.printf("[%s 삭제]\n", this.title);
    System.out.println();

    int boardNo = Prompt111.inputInt("삭제할 게시글 번호? "); 

    int boardIndex = -1;
    for (int i = 0; i < this.boardCount; i++) {
      if (this.boards[i].no == boardNo) {
        boardIndex = i;
        break;
      }
    }

    if(boardIndex == -1) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    for(int i = boardIndex + 1; i < this.boardCount; i++) {
      this.boards[i - 1] = this.boards[i];
    }


    this.boards[--this.boardCount] = null;
    System.out.println("게시글을 삭제합니다.");
  }

  void onUpdate() {
    System.out.printf("[%s 변경]\n", this.title);
    System.out.println();

    int boardNo = Prompt111.inputInt("변경할 게시글 번호? "); 

    Board111 board = null;
    for (int i = 0; i < this.boardCount; i++) {
      if (this.boards[i].no == boardNo) {
        board = this.boards[i];
        break;
      }
    }

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


