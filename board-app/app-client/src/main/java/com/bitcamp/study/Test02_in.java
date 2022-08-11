// FileInputStream: read() 사용법
package com.bitcamp.study;

import java.io.FileInputStream;

public class Test02_in {

  public static void main(String[] args) throws Exception {
    FileInputStream in = new FileInputStream("test2.data");

    // 파일 데이터를 끝까지 읽기
    // => -1을 리턴할 때까지 읽으면 된다.
    // => read() 메서드는 더이상 읽을 바이트가 없다면 -1을 리턴한다.
    int b;

    while ((b = in.read()) != -1) {
      System.out.printf("%08x\n", b);
    }
    // 4바이트 읽기
    //    int result = 0;
    //    int b = in.read();
    //    result += b << 24;
    //    System.out.printf("%08x\n", b);
    //
    //    b = in.read();
    //    result += b << 16;
    //    System.out.printf("%08x\n", b);
    //
    //    b = in.read();
    //    result += b << 8;
    //    System.out.printf("%08x\n", b);
    //
    //    b = in.read();
    //    result += b;
    //    System.out.printf("%08x\n", b);
    //    System.out.printf("%08x\n", result);
    //
    //    // 읽을 바이트가 없다면 -1을 리턴한다.
    //    b = in.read();
    //    System.out.printf("%08x, %d\n", b , b);
    in.close();

    System.out.println("실행 완료!");
  }

}
