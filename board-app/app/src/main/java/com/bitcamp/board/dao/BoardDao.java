package com.bitcamp.board.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    FileInputStream in = new FileInputStream(filename);
    //FileInputStream 도구를 사용하여 파일로부터 데이터를 읽어 들인다.
    // => 파일에서 읽은 게시글 데이터를 저장할 객체를 준비한다.

    // => 먼제 게시글 개수를 읽는다.
    int size = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
    for (int i = 0; i < size; i++) {
      Board board = new Board();
      // => 저장된 순서로 데이터를 읽는다.
      // 1) 게시글 번호 읽기
      int value = 0;
      value += in.read() << 24; // 예) 12 => 12000000
      value += in.read() << 16; // 예) 34 => 00340000
      value += in.read() << 8;  // 예) 56 => 00005600
      value += in.read();       // 예) 78 => 00000078
      board.no = value;

      // 2) 게시글 제목 읽기
      int len = 0;
      // 출력된 게시글 제목의 바이트 수를 읽어서 int 변수에 저장한다.
      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
      // 게시글 제목을 저장할 바이트 배열을 만든다.
      byte[] bytes = new byte[len];
      // 게시글 제목을 바이트 배열로 읽어 들인다.
      in.read(bytes);
      // 바이트 배열을 가지고 String 인스턴스를 생성한다.
      board.title = new String(bytes, "UTF-8");

      // 3) 게시글 내용 읽기
      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.content = new String(bytes, "UTF-8");

      // 4) 게시글 작성자 읽기
      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.writer = new String(bytes, "UTF-8");

      // 5) 게시글 암호 읽기
      len = (in.read() <<24) + (in.read() << 16) + (in.read() << 8) + in.read();
      bytes = new byte[len];
      in.read(bytes);
      board.password = new String(bytes, "UTF-8");

      // 6) 게시글 조회수 읽기
      board.viewCount = (in.read() << 24) + (in.read() << 16) + (in.read() << 8) + in.read();

      // 7) 게시글 등록일 읽기
      board.createdDate = ((long)in.read() << 56) + ((long)in.read() << 48) + 
          ((long)in.read() << 40) + ((long)in.read() << 32) + ((long)in.read() << 24) + 
          ((long)in.read() << 16) + ((long)in.read() << 8) + in.read();

      //    System.out.println(board);
      //  게시글 데이터가 저장된 Board 객체를 목록에 추가한다.
      list.add(board);
      // 파일에서 게시글을 읽어 올 때는 항상 게시글 번호를 boardNo에 저장한다.
      // 그래야만 새 게시글을 저장할 때 마지막 게시글 번호보다 큰 값으로 저장할 수 있다.
      boardNo = board.no;
    }
    in.close();
  }

  public void save() throws Exception {
    FileOutputStream out = new FileOutputStream(filename);

    // 첫번째로 먼저 게시글의 개수를 4바이트 int 값으로 출력한다.
    out.write(list.size() >> 24);
    out.write(list.size() >> 16);
    out.write(list.size() >> 8);
    out.write(list.size());

    for(Board board : list) {
      // int ==> byte[]
      // 예) board.no = 0x12345678
      System.out.println("-------------------------------");
      System.out.printf("%08x\n", board.no);
      out.write(board.no >> 24); // 0x12345678 => 0x00000012|345678
      out.write(board.no >> 16); // 0x12345678 => 0x00001234|5678
      out.write(board.no >> 8);  // 0x12345678 => 0x00123456|78
      out.write(board.no);       // 0x12345678

      // board.title = "Hello"
      // String(UTF-16) => UTF-8
      System.out.printf("%s\n", board.title);
      // 출력할 바이트 배열의 개수를 먼저 출력한다. (2바이트)
      byte[] bytes = board.title.getBytes("UTF-8");
      out.write(bytes.length >> 24);
      out.write(bytes.length >> 16);
      out.write(bytes.length >> 8);
      out.write(bytes.length);
      out.write(bytes);
      out.write(board.title.getBytes("UTF-8"));
      System.out.printf("%s\n", board.content);
      out.write(board.content.getBytes("UTF-8"));
      System.out.printf("%s\n", board.writer);
      out.write(board.writer.getBytes("UTF-8"));
      System.out.printf("%s\n", board.password);
      out.write(board.password.getBytes("UTF-8"));

      // int => byte[]
      System.out.printf("%08x\n", board.viewCount);
      out.write(board.viewCount >> 24);
      out.write(board.viewCount >> 16);
      out.write(board.viewCount >> 8);
      out.write(board.viewCount);

      // long => byte[]
      System.out.printf("%16x\n", board.createdDate);
      out.write((int)(board.createdDate >> 56));
      out.write((int)(board.createdDate >> 48));
      out.write((int)(board.createdDate >> 40));
      out.write((int)(board.createdDate >> 32));
      out.write((int)(board.createdDate >> 24));
      out.write((int)(board.createdDate >> 16));
      out.write((int)(board.createdDate >> 8));
      out.write((int)board.createdDate);
    }
    out.close();
  }

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
    // 역순으로 정렬하여 리턴한다.
    Board[] arr = list.toArray(new Board[0]);
    Board[] arr2 = new Board[arr.length];
    for (int i = 0; i < arr2.length; i++) {
      arr2[i] = arr[arr.length - i - 1];
    }
    return arr;
  }

  private int nextNo() {
    return ++boardNo;
  }
}














