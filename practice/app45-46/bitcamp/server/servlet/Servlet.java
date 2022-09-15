package com.bitcamp.server.servlet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Servlet {

  String SUCCESS = "success";
  String FAIL = "fail";

  void service(DataInputStream in, DataOutputStream out);
}
