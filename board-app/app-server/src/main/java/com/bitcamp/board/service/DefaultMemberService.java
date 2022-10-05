package com.bitcamp.board.service;

import java.util.List;

import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

// 비지니스 로직을 수행하는 객체
// - 메서드의 이름은 업무와 관련된 이름을 사용한다.
public class DefaultMemberService implements MemberService {
  MemberDao memberDao;

  public DefaultMemberService(MemberDao memberDao) {
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

  @Override
  public boolean delete(int no) throws Exception {
    return memberDao.delete(no) > 0;
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }
}

