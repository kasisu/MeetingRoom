<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.isNew()){
	session.removeAttribute("errorReason");
}
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/meetingroom.css">
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<h1>会議室予約</h1>
<hr>
<h2>ログイン</h2>
<div class="error">
    ${errorReason}<br>
</div>
<form action="<%= request.getContextPath() %>/Login" method="post">
		<label class="log">利用者ID：</label><input type="text" name="userId" value=""><br>
		<label>パスワード：</label><input type="password" name="userPw" value=""><br>
		<input class="bottun cursor" type="submit" value="ログイン">
</form>
</body>
</html>