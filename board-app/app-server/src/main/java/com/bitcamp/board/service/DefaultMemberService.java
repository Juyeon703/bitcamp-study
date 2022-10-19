package com.bitcamp.board.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@Service
//@Component를 DAO 역할을 수행하는 객체에 붙이는 애노테이션으로 변경한다.
public class DefaultMemberService implements MemberService {

  @Autowired MemberDao memberDao;
  @Autowired BoardDao boardDao;

  public DefaultMemberService(MemberDao memberDao) {
    System.out.println("DefaultMemberService() 호출됨");
    this.memberDao = memberDao;
  }

  @Override
  public void add(Member member) throws Exception {
    //    if (memberDao.insert(member) == 0) {
    //      throw new Exception("게시글 등록 실패!");
    //    }
    memberDao.insert(member);
  }

  @Override
  public boolean update(Member member) throws Exception {
    return memberDao.update(member) > 0;
  }

  @Override
  public Member get(int no) throws Exception {
    // 이 메서드의 경우 하는 일이 없다.
    // 그럼에도 불구하고 이렇게 하는 이유는 일관성을 위해서다.
    // 즉, Controller는 Service 객체를 사용하고 Service 객체는 Dao를 사용하는 형식을 지키기 위함이다.
    // 사용 규칙이 동일하면 프로그래밍을 이해하기 쉬워진다.
    return memberDao.findByNo(no);
  }

  @Override
  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailPassword(email, password);
  }

  @Transactional
  @Override
  public boolean delete(int no) throws Exception {
    //    // 트랜잭션 동작 방법을 정의한다.
    //    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    //    def.setName("tx1");
    //    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    //    TransactionStatus status = txManager.getTransaction(def);
    //
    //    try {
    boardDao.deleteFilesByMemberBoards(no); //회원이 작성한 게시글의 모든 첨부파일 삭제
    boardDao.deleteByMember(no); // 회원이 작성한 게시글 삭제
    boolean result = memberDao.delete(no) > 0; //회원 삭제
    //      txManager.commit(status);
    return result;
    //    } catch (Exception e) {
    //      txManager.rollback(status);
    //      throw e;
    //    }
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }
}

