/*
 * 게시판 관리 애플리케이션
 * 비트캠프 - 20220704
 */
package com.bitcamp.board;

public class App {

  public static void main(String[] args) {
    System.out.println("[게시판 애플리케이션]");
    System.out.println();
    System.out.println("환영합니다!");
    System.out.println();

    java.util.Scanner keyboardInput = new java.util.Scanner(System.in);

    final int SIZE = 3; // final 변수 -> 상수는 한눈에 알아보기 위해 대문자 사용하는게 좋음

    int[] no = new int[SIZE];
    String[] title = new String[SIZE];
    String[] content = new String[SIZE];
    String[] writer = new String[SIZE];
    String[] password = new String[SIZE];
    int[] viewCount = new int[SIZE];
    long[] createdDate = new long[SIZE];

    int boardCount = 0; //저장된 게시글의 개수

    while (true) {
      System.out.println("---------------------------------------------------------");
      System.out.println("메뉴:");
      System.out.println("  1: 게시글 목록");
      System.out.println("  2: 게시글 상세보기");
      System.out.println("  3: 게시글 등록");
      System.out.println();
      System.out.print("메뉴를 선택하세요[1..3](0: 종료) ");

      int menuNo = keyboardInput.nextInt(); // 저장할 메모리 필요! 익스프레이션이란? 결과를 리턴하는 명령문 변수명은 알아보기 쉽게
      keyboardInput.nextLine(); // 입력한 숫자 뒤에 남아 있는 줄바꿈 코드 제거

      System.out.println("---------------------------------------------------------");
      if (menuNo == 0) {
        break;

      } else if (menuNo == 1) {
        System.out.println("[게시글 목록]");
        System.out.println("번호 제목 조회수 작성자 등록일");
        
        //날짜 정보에서 값을 추출하여 특정 포맷의 문자열로 만들어줄 도구를 준비
        java.text.SimpleDateFormat formatter =
          new java.text.SimpleDateFormat("yyyy-MM-dd");


        int i = 0;
        while (i < boardCount) {
          // 밀리초 데이터 => Date 도구함으로 날짜 정보를 설정
          java.util.Date date = new java.util.Date(createdDate[i]);

          //날짜 => "yyyy-MM-dd" 형식의 문자열
          String dateStr = formatter.format(date); 

          System.out.printf("%d\t%s\t%d\t%s\t%s\n", // 포멧에 따라 출력
          no[i], title[i], viewCount[i], writer[i], dateStr);

          i++; // 배열 인덱스를 증가시킨다
        }

      } else if (menuNo == 2) { // else { if {} else {} } => else if {} else{} 대괄호 생략으로 else if 된거임
        System.out.println("[게시글 상세보기]");
        System.out.println();
        System.out.print("조회할 게시글 번호? ");
        String input = keyboardInput.nextLine();
        int boardNo = Integer.parseInt(input); //형변환, 이렇게 쓰면 엔터를 또 따로 안읽어도됨

        //해당 번호의 게시글이 몇 번 배열에 들어 있는지 알아내기
        int boardIndex = -1;
        int i = 0;
        while ( i < boardCount) {
          if (no[i] == boardNo) {
            boardIndex = i;
            break;
          }
          i++;
        }
        // 사용자가 입력한 번호에 해당하는 게시글을 못찾았다면
        if(boardIndex == -1) {
          System.out.println("해당 번호의 게시글이 없습니다!");
          System.out.println();
          continue;
        }
        System.out.printf("번호 : %d\n", no[boardIndex]);
        System.out.printf("제목 : %s\n", title[boardIndex]);
        System.out.printf("내용 : %s\n", content[boardIndex]);
        System.out.printf("조회수 : %d\n", viewCount[boardIndex]);
        System.out.printf("작성자 : %s\n", writer[boardIndex]);
        // Date 도구함의 도구를 쓸 수 있도록 데이터를 준비시킨다.
        // new Date(밀리초) => 지정한 밀리초를 가지고 날짜 관련 도구를 사용할 수 있도록 설정한다.
        // Date date
        //      => createdDate 밀리초를 가지고 설정한 날짜 정보 
        java.util.Date date = new java.util.Date(createdDate[boardIndex]);

        //Date 도구함을 통해 설정한 날짜 정보를 가지고 printf()를 실행한다.
        // %tY : date에 설정된 날짜 정보에서 년도만 추출한다.
        System.out.printf("등록일 : %tY-%1$tm-%1$td %1$tH:%1$tM\n", date);

      } else if (menuNo == 3) {
        System.out.println("[게시글 등록]");
        System.out.println();

        // 배열의 크기를 초과하지 않았는지 검사한다.
        if (boardCount == SIZE) {
          System.out.println("게시글을 더이상 저장할 수 없습니다!");
          System.out.println();
          continue;
        }

        System.out.print("제목? ");
        title[boardCount] = keyboardInput.nextLine();

        System.out.print("내용? ");
        content[boardCount] = keyboardInput.nextLine();

        System.out.print("작성자? ");
        writer[boardCount] = keyboardInput.nextLine();

        System.out.print("암호? ");
        password[boardCount] = keyboardInput.nextLine();
        
/*        if (boardCount == 0) {
            no[boardCount] = 1;
          } else {
            no[boardCount] = no[boardCount - 1] + 1;
          } */
        no[boardCount] = boardCount == 0 ? 1 : no[boardCount - 1] + 1;

        viewCount[boardCount] = 0;
        createdDate[boardCount] = System.currentTimeMillis();

        boardCount++;

      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      } 
      System.out.println(); //메뉴를 처리한 후 빈 줄 출력
    } // while 
    System.out.println("안녕히 가세요!");
    keyboardInput.close();
  }
}
