package com.bitcamp.board.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@Component  
//- 이 애노테이션을 붙이면 Spring IoC 컨테이너가 객체를 자동 생성한다.
//- 객체의 이름을 명시하지 않으면 클래스 이름(첫 알파벳은 소문자 예) "defaultMemberService")을 사용하여 저장한다.
//- 물론 생성자의 파라미터 값을 자동으로 주입한다.
//- 파라미터에 해당하는 객체가 없다면 생성 오류가 발생한다.
public class DefaultMemberService implements MemberService {
  MemberDao memberDao;

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

  @Override
  public boolean delete(int no) throws Exception {
    return memberDao.delete(no) > 0;
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }
}

