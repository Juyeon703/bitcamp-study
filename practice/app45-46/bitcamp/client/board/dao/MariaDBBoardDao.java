package com.bitcamp.client.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.common.board.domain.Board111;

public class MariaDBBoardDao {

  public int insert(Board111 board) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
        PreparedStatement pstmt = con.prepareStatement(
            "insert into app_board(title,cont,mno) values(?,?,?)")) {
      pstmt.setString(1, board.title);
      pstmt.setString(2, board.content);
      pstmt.setInt(3, board.memberNo);
      return pstmt.executeUpdate();
    }
  }

  public Board111 findByNo(int no) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
        PreparedStatement pstmt = con.prepareStatement(
            "select bno,title,cont,mno,cdt,vw_cnt from app_board where bno=" + no);
        ResultSet rs = pstmt.executeQuery()) {

      if (!rs.next()) {
        return null;
      }

      Board111 board = new Board111();
      board.no = rs.getInt("bno");
      board.title = rs.getString("title");
      board.content = rs.getString("cont");
      board.memberNo = rs.getInt("mno");
      board.createdDate = rs.getDate("cdt");
      board.viewCount = rs.getInt("vw_cnt");

      return board;
    }
  }

  public int update(Board111 board) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
        PreparedStatement pstmt = con.prepareStatement(
            "update app_board set title=?, cont=? where bno=?")) {

      pstmt.setString(1, board.title);
      pstmt.setString(2, board.content);
      pstmt.setInt(3, board.no);

      return pstmt.executeUpdate();
    }
  }

  public int delete(int no) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
        PreparedStatement pstmt = con.prepareStatement("delete from app_board where bno=?")) {

      pstmt.setInt(1, no);
      return pstmt.executeUpdate();
    }
  }

  public List<Board111> findAll() throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/studydb","study","1111");
        PreparedStatement pstmt = con.prepareStatement(
            "select bno,title,mno,cdt,vw_cnt from app_board");
        ResultSet rs = pstmt.executeQuery()) {

      ArrayList<Board111> list = new ArrayList<>();

      while (rs.next()) {
        Board111 board = new Board111();
        board.no = rs.getInt("bno");
        board.title = rs.getString("title");
        board.memberNo = rs.getInt("mno");
        board.createdDate = rs.getDate("cdt");
        board.viewCount = rs.getInt("vw_cnt");

        list.add(board);
      }

      return list;
    }
  }
}
