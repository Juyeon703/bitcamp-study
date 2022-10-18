package com.bitcamp.board.dao;

import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;

@Repository
//@Component를 DAO 역할을 수행하는 객체에 붙이는 애노테이션으로 변경한다.
// - 이 애노테이션을 붙이면 Spring IoC 컨테이너가 객체를 자동 생성한다.
// - 객체의 이름을 명시하지 않으면 클래스 이름(첫 알파벳은 소문자 (예) "mariaDBboardDao")을 사용하여 저장한다.
// - 물론 생성자의 파라미터 값을 자동으로 주입한다.
// - 파라미터에 해당하는 객체가 없다면 생성 오류가 발생한다.
public class MybatisBoardDao implements BoardDao {
  @Autowired DataSource ds;
  @Autowired SqlSessionFactory sqlSessionFactory;

  @Override
  public int insert(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("BoardDao.insert", board);
    }
  }

  @Override
  public Board findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      // 게시글 가져오기
      Board board = sqlSession.selectOne("BoardDao.findByNo", no);
      // 게시글 첨부파일 가져오기
      List<AttachedFile> attachedFiles = sqlSession.selectList("BoardDao.findFilesByBoard", no);
      board.setAttachedFiles(attachedFiles);

      return board;
    }
  }

  @Override
  public Board findByNo2(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      // 게시글 가져오기
      return sqlSession.selectOne("BoardDao.findByNo", no);
    }
  }

  @Override
  public Board findByNo3(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("BoardDao.findByNo3", no);
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("BoardDao.update", board);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardDao.delete", no);
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList(
          "BoardDao.findAll"); 
    }
  }


  @Override
  public int insertFiles(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("BoardDao.insertFiles", board);
    }
  }

  @Override
  public AttachedFile findFileByNo(int fileNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("BoardDao.findFileByNo", fileNo);
    }
  }

  @Override
  public List<AttachedFile> findFilesByBoard(int boardNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardDao.findFilesByBoard", boardNo);
    }
  }

  @Override
  public int deleteFile(int fileNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardDao.deleteFile", fileNo);
    }
  }

  @Override
  public int deleteFiles(int boardNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardDao.deleteFiles", boardNo);
    }
  }
}














