package com.bitcamp.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

// 사용자 요청을 다룰 객체의 사용법을 정의한다.
//
public interface Servlet {
  //오타 방지 위해 상수를 사용함.
  // 인터페이스에 선언된 변수는 무조건 static final임
  String SUCCESS = "success";
  String FAIL = "fail";

  void service(DataInputStream in, DataOutputStream out);
}
