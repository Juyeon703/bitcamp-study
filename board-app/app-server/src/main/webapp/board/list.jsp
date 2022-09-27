<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bitcamp</title>
<style>
tr:hover {
  background-color: navy;
  color: white;
}
</style>
</head>
<body>
  <h1>게시글(JSP + Servlet +EL + JSTL)</h1>
  <a href='form'>새 글</a>

  <table border='1'>
    <tr>
      <th>번호</th>
      <th>제목</th>
      <th>조회수</th>
      <th>작성자</th>
      <th>등록일</th>
    </tr>
   <!--
    List<Board> boards = (List<Board>) request.getAttribute("boards");
    for (Board board : boards) {
      pageContext.setAttribute("board", board); // built-in 객체, 보관소에 담아야만 EL로 꺼낼 수 있다.
      // JSP에서만 사용할 보관소라면 pageContext를 사용
    // ServletRequest보관소에서 members를 꺼내오고
    // member객체 하나하나 꺼내오기 위해 pageContext보관소에 member로 저장해서
    // EL로 꺼내온다.
    --> 
  <c:forEach items="${requestScope.boards}" var="board">
    
    <tr>
      <td>${board.no}</td> 
      <td><a href="detail?no=${board.no}">${board.title}</a></td>
      <td>${board.viewCount}</td>
      <td>${board.memberNo}</td>
      <td>${board.createdDate}</td>
    </tr>
  </c:forEach>
  </table>
  <p>
    <a href='../'>메인</a>
  </p>
</body>
</html>





