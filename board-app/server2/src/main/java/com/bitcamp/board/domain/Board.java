package com.bitcamp.board.domain;

import java.sql.Date;

// java.io.Serializable 인터페이스
// - 인스턴스를 통째로 입출력할 수 있도록 표시하는 용도이다.
// - 인터페이스에 추상 메서드가 선언되어 있지 않기 때문에 따로 메서드를 구현할 필요는 없다.
// 
public class Board {
  // java.io.Serializable 인터페이스를 구현
  // - 즉 Board 클래스의 필드 값을 통째로 입출력 할 수 있다고 설정하는 용도이다.

  // serialVersionUID 필드 설정
  // - 인스턴스를 저장하고 읽을 때 클래스의 변화 여부를 검증하기 위해 버전 번호를 지정한다.
  // - 처음 버전 번호를 부여한 후 클래스의 필드를 추가하거나 삭제할 때 마다 버전 번호를 증가시킨다.

  public int no;
  public String title;
  public String content;
  public int memberNo;
  public String password;
  public int viewCount;
  public Date createdDate;

  @Override
  public String toString() {
    return "Board [no=" + no + ", title=" + title + ", content=" + content + ", memberNo="
        + memberNo + ", password=" + password + ", viewCount=" + viewCount + ", createdDate="
        + createdDate + "]";
  }
}
