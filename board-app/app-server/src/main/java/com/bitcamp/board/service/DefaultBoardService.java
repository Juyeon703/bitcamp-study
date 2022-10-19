package com.bitcamp.board.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;

@Service
//@Component를 DAO 역할을 수행하는 객체에 붙이는 애노테이션으로 변경한다.
//- 이 애노테이션을 붙이면 Spring IoC 컨테이너가 객체를 자동 생성한다.
//- 객체의 이름을 명시하지 않으면 클래스 이름(첫 알파벳은 소문자 예) "defaultBoardService")을 사용하여 저장한다.
//- 물론 생성자의 파라미터 값을 자동으로 주입한다.
//- 파라미터에 해당하는 객체가 없다면 생성 오류가 발생한다.
public class DefaultBoardService implements BoardService {

  @Autowired 
  //  @Qualifier("mybatisBoardDao") 
  BoardDao boardDao;

  @Transactional
  @Override 
  public void add(Board board) throws Exception {
    //
    //    // 트랜잭션 동작 방법을 정의한다.
    //    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    //    def.setName("tx1");
    //    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    //
    //    TransactionStatus status = txManager.getTransaction(def);
    //    try {
    // 1) 게시글 등록
    if (boardDao.insert(board) == 0) {
      throw new Exception("게시글 등록 실패!");
    }

    // 2) 첨부파일 등록
    if (board.getAttachedFiles().size() > 0) {
      boardDao.insertFiles(board);
    }
    //      txManager.commit(status);
    //
    //    } catch (Exception e) {
    //      txManager.rollback(status);
    //      throw e;
    //    }
  }

  @Transactional
  @Override
  public boolean update(Board board) throws Exception {
    // 1) 게시글 변경
    if (boardDao.update(board) == 0) {
      return false;
    }
    // 2) 첨부파일 추가
    if (board.getAttachedFiles().size() > 0) {
      boardDao.insertFiles(board);
    }
    return true;
  }

  @Override
  public Board get(int no) throws Exception {
    // 이 메서드의 경우 하는 일이 없다.
    // 그럼에도 불구하고 이렇게 하는 이유는 일관성을 위해서다.
    // 즉 Controller는 Service 객체를 사용하고 Service 객체는 DAO를 사용하는 형식을 
    // 지키기 위함이다.
    // 사용 규칙이 동일하면 프로그래밍을 이해하기 쉬워진다.

    //    // 방법1:
    //    return boardDao.findByNo(no); // select를 두 번 실행한다.

    //    //방법2:
    //    Board board = boardDao.findByNo2(no);
    //    List<AttachedFile> attachedFiles = boardDao.findFilesByBoard(no);
    //    board.setAttachedFiles(attachedFiles);
    //    return board;

    // 방법3:
    return boardDao.findByNo(no); // 첨부파일 데이터까지 조인하여 select를 한 번만 실행한다.
  }

  @Transactional
  @Override
  public boolean delete(int no) throws Exception {
    // 1) 첨부파일 삭제
    boardDao.deleteFiles(no);

    // 2) 게시글 삭제
    return boardDao.delete(no) > 0;
  }

  @Override
  public List<Board> list() throws Exception {
    return boardDao.findAll();
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return boardDao.findFileByNo(fileNo);
  }

  @Override
  public boolean deleteAttachedFile(int fileNo) throws Exception {
    return boardDao.deleteFile(fileNo) > 0;
  }
}








