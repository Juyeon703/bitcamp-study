package com.bitcamp.server.board.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.bitcamp.server.board.domain.Member111;
import com.google.gson.Gson;

public class MemberDaoProxy111 {
  String dataName;
  DataInputStream in;
  DataOutputStream out;

  public MemberDaoProxy111(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;
  }

  public boolean insert(Member111 member) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("insert");
    out.writeUTF(new Gson().toJson(member));

    return in.readUTF().equals("success");
  }

  public Member111 findByEmail(String email) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("findByEmail");
    out.writeUTF(email);

    if (in.readUTF().equals("fail")) {
      return null;
    }
    return new Gson().fromJson(in.readUTF(), Member111.class);
  }

  public boolean update(Member111 member) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("update");
    out.writeUTF(new Gson().toJson(member));

    return in.readUTF().equals("success");
  }

  public boolean delete(String email) throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("delete");
    out.writeUTF(email);

    return in.readUTF().equals("success");
  }

  public Member111[] findAll() throws Exception {
    out.writeUTF(dataName);
    out.writeUTF("findAll");

    if (in.readUTF().equals("fail")) {
      return null;
    }
    return new Gson().fromJson(in.readUTF(), Member111[].class);
  }
}
