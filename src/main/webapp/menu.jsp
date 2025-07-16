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
<title>メニュー画面</title>
</head>
<body>
    <h1>会議室予約</h1>
    <hr>
    <h2>メニュー</h2>
    <form action="<%= request.getContextPath() %>/reserveInput.jsp" method="post">
        <input class="cursor" type="submit" value="会議室予約" name=""><br>    
    </form>
    
    <form action="<%= request.getContextPath() %>/cancelInput.jsp" method="post">
        <input class="cursor" type="submit" value="予約キャンセル" name=""><br>    
    </form>
    
    <form action="<%= request.getContextPath() %>/Logout" method="post">
        <input class="cursor" type="submit" value="ログアウト" name=""> 
    </form>
    

</body>
</html>