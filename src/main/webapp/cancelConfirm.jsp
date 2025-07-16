<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="JSPLoginManagement" %>
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
<title>キャンセル確認画面</title>
</head>
<body>
	<h1>会議室予約キャンセル</h1>
	<hr>
	<h2>キャンセル確認</h2>
	<dl class="side">
		<dt>予約日</dt>
		<dd>${ reservation.date }</dd>
		<dt>会議室</dt>
		<dd>${ room.name }</dd>
		<dt>予約時刻</dt>
		<dd>${ reservation.start }～${ reservation.end }</dd>
		<dt>予約者</dt>
		<dd>${ meetingRoom.user.name }</dd>
	</dl>
	<hr>
	<div class="side">
		<form action="<%=request.getContextPath()%>/cancelInput.jsp"
			method="post">
			<input class="cursor" type="submit" value="戻る">
		</form>

		<form action="<%=request.getContextPath()%>/Cancel" method="post">
			<input class="cursor" type="submit" value="決定">
		</form>
	</div>
</body>
</html>