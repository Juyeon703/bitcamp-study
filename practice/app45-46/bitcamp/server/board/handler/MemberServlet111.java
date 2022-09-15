package com.bitcamp.server.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.common.board.domain.Member111;
import com.bitcamp.server.board.dao.MemberDao111;
import com.bitcamp.server.servlet.Servlet;
import com.google.gson.Gson;

public class MemberServlet111 implements Servlet {

  private MemberDao111 memberDao;
  private String filename;

  public MemberServlet111 (String dataName) {
    filename = dataName + ".json";
    memberDao = new MemberDao111(filename);
    try {
      memberDao.load();
    } catch (Exception e) {
      System.out.printf("%s 파일 로딩 중 오류 발생!\n", filename);
      e.printStackTrace();
    }
  }

  @Override
  public void service(DataInputStream in, DataOutputStream out) { 
    try {
      String command = in.readUTF();
      Member111 member = null;
      String email = null;
      String json = null;

      switch (command) {
        case "findAll":
          Member111[] members = memberDao.findAll();
          out.writeUTF(SUCCESS);
          out.writeUTF(new Gson().toJson(members));
          break;
        case "findByEmail":
          email = in.readUTF();
          member = memberDao.findByEmail(email);
          if (member != null) {
            out.writeUTF(SUCCESS);
            out.writeUTF(new Gson().toJson(member));
          } else {
            out.writeUTF(FAIL);
          }
          break;
        case "insert": 
          json = in.readUTF();
          member = new Gson().fromJson(json, Member111.class);
          memberDao.insert(member);
          memberDao.save();
          out.writeUTF(SUCCESS);
          break;
        case "update": 
          json = in.readUTF();
          member = new Gson().fromJson(json, Member111.class);
          if (memberDao.update(member)) {
            memberDao.save();
            out.writeUTF(SUCCESS);
          } else {
            out.writeUTF(FAIL);
          }
          break;
        case "delete": 
          email = in.readUTF();
          if (memberDao.delete(email)) {
            memberDao.save();
            out.writeUTF(SUCCESS);
          } else {
            out.writeUTF(FAIL);
          }
          break;
        default:
          out.writeUTF(FAIL);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
