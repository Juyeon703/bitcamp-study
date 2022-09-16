package com.bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Prompt {

  DataInputStream in;
  DataOutputStream out;
  public Prompt(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }

  // 생성자를 사용하기 위해? static을 제거한다.
  public int inputInt() throws Exception {
    String str = in.readUTF();
    return Integer.parseInt(str); //"123" ==> 123, "5" ==> 5, "ok" ==> 실행 오류!

  }

  public  int inputInt(String title) throws Exception {
    out.writeUTF(title);
    String str = in.readUTF();
    return Integer.parseInt(str);
  }

  public  String inputString() throws Exception {
    return in.readUTF();
  }

  public  String inputString(String title) throws Exception {
    out.writeUTF(title);
    return in.readUTF();
  }
}
