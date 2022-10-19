package dao2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bitcamp.board.domain.Member;

@Repository
//@Component를 DAO 역할을 수행하는 객체에 붙이는 애노테이션으로 변경한다.
public class MybatisMemberDao implements MemberDao {

  // @Autowired는 객체 생성시 알아서 주입해줌, setter메소드 없어도 됨. 생성자 만들어도 되고 이거 써도됨
  // @Autowired(required = false) 기본설정은 주입 필수이고 required를 false로 설정해주면 있으면 주입하고 없으면 주입하지 않음
  @Autowired SqlSessionFactory sqlSessionFactory;

  @Override
  public int insert(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("MemberDao.insert", member);
    }
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne(
          "MemberDao.findByNo" // sql문의 ID
          , no); // SQL문의 in-parameter(#{})에 들어갈 값을 담고 있는 객체 
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("MemberDao.update", member);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("MemberDao.delete", no);
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList(
          "MemberDao.findAll"); 
    }
  }

  @Override
  public Member findByEmailPassword(String email, String password) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("email", email);
      paramMap.put("password", password);
      return sqlSession.selectOne(
          "MemberDao.findByEmailPassword" // sql문의 ID
          , paramMap); // SQL문의 in-parameter(#{})에 들어갈 값을 담고 있는 객체 
    }
  }
}














