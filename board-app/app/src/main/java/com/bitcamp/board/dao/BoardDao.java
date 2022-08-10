package com.bitcamp.board.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.bitcamp.board.domain.Board;

// 게시글 목록을 관리하는 역할
//
public class BoardDao {

  // List 인터페이스의 레퍼런스인 list 변수는
  // List 규격에 따라 만든 객체 주소를 담을 수 있다.
  List<Board> list = new LinkedList<>();

  private int boardNo = 0;

  String filename;

  public BoardDao(String filename) {
    this.filename = filename;
  }

  public void load() throws Exception{
    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
      String str;
      while((str = in.readLine()) != null) {
        Board board = Board.create(str);
        list.add(board);
        boardNo = board.no;
      }
    }
  }
  //        board.no = in.readInt();
  //        board.title = in.readUTF();
  //        board.content = in.readUTF();
  //        board.writer = in.readUTF();
  //        board.password = in.readUTF();
  //        board.viewCount = in.readInt();
  //        board.createdDate = in.readLong();
  //      // 2) 게시글 제목 읽기
  //      int len = 0;
  //      // 출력된 게시글 제목의 바이트 수를 읽어서 int 변수에 저장한다.
  //      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
  //      // 게시글 제목을 저장할 바이트 배열을 만든다.
  //      byte[] bytes = new byte[len];
  //      // 게시글 제목을 바이트 배열로 읽어 들인다.
  //      in.read(bytes);
  //      // 바이트 배열을 가지고 String 인스턴스를 생성한다.
  //      board.title = new String(bytes, "UTF-8");
  //
  //      // 3) 게시글 내용 읽기
  //      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
  //      bytes = new byte[len];
  //      in.read(bytes);
  //      board.content = new String(bytes, "UTF-8");
  //
  //      // 4) 게시글 작성자 읽기
  //      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
  //      bytes = new byte[len];
  //      in.read(bytes);
  //      board.writer = new String(bytes, "UTF-8");
  //
  //      // 5) 게시글 암호 읽기
  //      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
  //      bytes = new byte[len];
  //      in.read(bytes);
  //      board.password = new String(bytes, "UTF-8");
  //
  //      // 6) 게시글 조회수 읽기
  //      board.viewCount = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();
  //
  //      // 7) 게시글 등록일 읽기
  //      board.createdDate = ((long)in.read() << 56) + ((long)in.read() << 48) + 
  //          ((long)in.read() << 40) + ((long)in.read() << 32) + ((long)in.read() << 24) + 
  //          ((long)in.read() << 16) + ((long)in.read() << 8) + in.read();
  //
  //      //    System.out.println(board);
  //      //  게시글 데이터가 저장된 Board 객체를 목록에 추가한다.
  // 파일에서 게시글을 읽어 올 때는 항상 게시글 번호를 boardNo에 저장한다.
  // 그래야만 새 게시글을 저장할 때 마지막 게시글 번호보다 큰 값으로 저장할 수 있다.

  public void save() throws Exception {
    try (FileWriter out = new FileWriter(filename)) {

      for (Board board : list) {
        out.write(board.toCsv() + "\n");
      }
    }
  }

  //    // 첫번째로 먼저 게시글의 개수를 4바이트 int 값으로 출력한다.
  //    out.write(list.size() >> 24);
  //    out.write(list.size() >> 16);
  //    out.write(list.size() >> 8);
  //    out.write(list.size());
  //
  //    for(Board board : list) {
  //      // int ==> byte[]
  //      // 예) board.no = 0x12345678
  //      //      System.out.println("-------------------------------");
  //      //      System.out.printf("%08x\n", board.no);
  //      out.write(board.no >> 24); // 0x12345678 => 0x00000012|345678
  //      out.write(board.no >> 16); // 0x12345678 => 0x00001234|5678
  //      out.write(board.no >> 8);  // 0x12345678 => 0x00123456|78
  //      out.write(board.no);       // 0x12345678
  //
  //      // board.title = "Hello"
  //      // String(UTF-16) => UTF-8
  //      //      System.out.printf("%s\n", board.title);
  //      // 출력할 바이트 배열의 개수를 먼저 출력한다. (2바이트)
  //      byte[] bytes = board.title.getBytes("UTF-8");
  //      out.write(bytes.length >> 24);
  //      out.write(bytes.length >> 16);
  //      out.write(bytes.length >> 8);
  //      out.write(bytes.length);
  //      out.write(bytes);
  //      //      System.out.printf("%s\n", board.content);
  //      bytes = board.content.getBytes("UTF-8");
  //      out.write(bytes.length >> 24);
  //      out.write(bytes.length >> 16);
  //      out.write(bytes.length >> 8);
  //      out.write(bytes.length);
  //      out.write(bytes);
  //
  //      //      System.out.printf("%s\n", board.writer);
  //      bytes = board.writer.getBytes("UTF-8"); 
  //      out.write(bytes.length >> 24);
  //      out.write(bytes.length >> 16);
  //      out.write(bytes.length >> 8);
  //      out.write(bytes.length);
  //      out.write(bytes);
  //
  //      //      System.out.printf("%s\n", board.password);
  //      bytes = board.password.getBytes("UTF-8"); 
  //      out.write(bytes.length >> 24);
  //      out.write(bytes.length >> 16);
  //      out.write(bytes.length >> 8);
  //      out.write(bytes.length);
  //      out.write(bytes);
  //
  //      // int => byte[]
  //      //      System.out.printf("%08x\n", board.viewCount);
  //      out.write(board.viewCount >> 24);
  //      out.write(board.viewCount >> 16);
  //      out.write(board.viewCount >> 8);
  //      out.write(board.viewCount);
  //
  //      // long => byte[]
  //      //      System.out.printf("%16x\n", board.createdDate);
  //      out.write((int)(board.createdDate >> 56));
  //      out.write((int)(board.createdDate >> 48));
  //      out.write((int)(board.createdDate >> 40));
  //      out.write((int)(board.createdDate >> 32));
  //      out.write((int)(board.createdDate >> 24));
  //      out.write((int)(board.createdDate >> 16));
  //      out.write((int)(board.createdDate >> 8));
  //      out.write((int)board.createdDate);
  //    }
  //    out.close();
  //  }

  // BoardDao 에서 제공할 메서드를 정의하고,
  // 이 메서드가 호출되면 ObjectList의 도움을 받아 처리한다.
  public void insert(Board board) {
    board.no = nextNo();
    list.add(board);
  }

  public Board findByNo(int boardNo) {
    // 의존 객체 BoardList를 이용하여 기존에 저장된 게시글 목록 중에 
    // 해당 번호의 게시글을 찾는다.
    for (int i = 0; i < list.size(); i++) {
      Board board = list.get(i);
      if (board.no == boardNo) {
        return board;
      }
    }

    return null;
  }

  public boolean delete(int boardNo) {
    // 의존 객체 ObjectList을 이용하여 목록에 저장된 게시글을 찾아 삭제한다.
    for (int i = 0; i < list.size(); i++) {
      Board board = list.get(i);
      if (board.no == boardNo) {
        return list.remove(i) != null;
      }
    }

    return false;
  }

  public Board[] findAll() {
    // 목록에서 값을 꺼내는 일을 할 객체를 준비한다.
    Iterator<Board> iterator = list.iterator();

    // 역순으로 정렬하여 리턴한다.
    Board[] arr = new Board[list.size()];

    int index = list.size() - 1;
    while (iterator.hasNext()) {
      arr[index--] = iterator.next();
    }
    return arr;
  }

  private int nextNo() {
    return ++boardNo;
  }
}














