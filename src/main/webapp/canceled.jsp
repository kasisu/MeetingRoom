<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="reserve.JSPLoginManagement" %>
<%
JSPLoginManagement LM = new JSPLoginManagement(request);
if (!(LM.isLogin())) {
	String nextpage = request.getContextPath() + "/login.jsp";
	response.sendRedirect(nextpage);
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/meetingroom.css">
<meta charset="UTF-8">
<title>キャンセル完了画面</title>
</head>
<body>

<h1>会議室予約キャンセル</h1>
<hr>
<h2>キャンセル完了</h2>
<dl class="side">
	<dt>予約日</dt>
	<dd>${ reservation.date }</dd>
	<dt>会議室</dt>
	<dd>${ room.name }</dd>
	<dt>予約時刻</dt>
	<dd>${ reservation.start } ～${ reservation.end }</dd>
	<dt>予約者</dt>
	<dd>${ meetingRoom.user.name }</dd>
</dl>
<hr>
<form action="<%= request.getContextPath() %>/menu.jsp" method="post">
<input class="cursor" type="submit" value="完了">
</form>

</body>
</html>