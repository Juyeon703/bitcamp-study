package com.bitcamp.client.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.common.board.domain.Member111;


public class MariaDBMemberDao111 implements MemberDao111{
  Connection con;

  public MariaDBMemberDao111(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(Member111 member) throws Exception {
    try (PreparedStatement pstmt = con.prepareStatement(
        "insert into app_member(name,email,pwd) values(?,?,sha2(?,256))")) {

      pstmt.setString(1, member.name);
      pstmt.setString(2, member.email);
      pstmt.setString(3, member.password);

      return pstmt.executeUpdate();
    }
  }

  @Override
  public Member111 findByNo(int no) throws Exception {
    try (PreparedStatement pstmt = con.prepareStatement(
        "select mno,name,email,cdt from app_member where mno=" + no);
        ResultSet rs = pstmt.executeQuery()) {

      if (!rs.next()) {
        return null;
      }

      Member111 member = new Member111();
      member.no = rs.getInt("mno");
      member.name = rs.getString("name");
      member.email = rs.getString("email");
      member.createdDate = rs.getDate("cdt");
      return member;
    }
  }

  @Override
  public int update(Member111 member) throws Exception {
    try (PreparedStatement pstmt = con.prepareStatement(
        "update app_member set name=?, email=?, pwd=sha2(?,256) where mno=?")) {

      pstmt.setString(1, member.name);
      pstmt.setString(2, member.email);
      pstmt.setString(3, member.password);
      pstmt.setInt(4, member.no);

      return pstmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement pstmt1 = con.prepareStatement("delete from app_board where mno=?");
        PreparedStatement pstmt2 = con.prepareStatement("delete from app_member where mno=?")) {

      pstmt1.setInt(1, no);
      pstmt1.executeUpdate();

      pstmt2.setInt(1, no);
      return pstmt2.executeUpdate();
    }
  }

  @Override
  public List<Member111> findAll() throws Exception {
    try (PreparedStatement pstmt = con.prepareStatement(
        "select mno,name,email from app_member");
        ResultSet rs = pstmt.executeQuery()) {

      ArrayList<Member111> list = new ArrayList<>();

      while (rs.next()) {
        Member111 member = new Member111();
        member.no = rs.getInt("mno");
        member.name = rs.getString("name");
        member.email = rs.getString("email");

        list.add(member);
      }

      return list;
    }
  }
}
