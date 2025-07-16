<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="reserve.bean.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="reserve.JSPLoginManagement" %>
<%
JSPLoginManagement LM = new JSPLoginManagement(request);
if (!(LM.isLogin())) {
	String nextpage = request.getContextPath() + "/login.jsp";
	response.sendRedirect(nextpage);
	return;
}
//セッションからMeetingRoomを取得
MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
//現在日付を取得
Calendar caldate = Calendar.getInstance();
Calendar beforedate = Calendar.getInstance();
// 日付時間のフォーマットを設定
SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
//MeetingRoomからDateを取得
String datevalue = meetingRoom.getDate();
// Dateが空の時現在日付をセット
if (datevalue == null) {
	meetingRoom.setDate(sdfdate.format(caldate.getTime()));
}
String error = "";
// 日付を１日前にずらす
beforedate.add(Calendar.DAY_OF_MONTH, -1);
java.util.Date bdate = beforedate.getTime();
if(sdfdate.parse(meetingRoom.getDate()).before(bdate)){
	error = "日付が過ぎているためキャンセルできません";
}
// MeetingRoomをセッションに格納
session.setAttribute("meetingRoom", meetingRoom);
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/meetingroom.css">
<meta charset="UTF-8">
<title>キャンセル入力画面</title>
</head>
<body>
	<h1>会議室予約キャンセル</h1>
	<hr>
	<h2>利用日</h2>
	<form action="<%=request.getContextPath()%>/ChangeDate" method="post">
		<label for="date"><input type="date" id="date" name="date"
			value="${meetingRoom.date}" required>
		</label><input type="hidden" name="page" value="cancelInput.jsp">
		<input class="cursor" type="submit" value="日付変更" name="">
	</form>
	<h2>
		キャンセル可能時間帯(<label for="name">${meetingRoom.user.name}</label>)
	</h2>
	<h2 class="error"><%= error %></h2>
	<div class="info">
	<input class='bcolor' type='submit' value='    ' disabled>未予約
	<input class='rcolor' type='submit' value='    ' disabled>キャンセル可
	<input class='ncolor' type='submit' value='    ' disabled>キャンセル不可
	</div>
	<table class="side" border="1">
		<%
		//リクエスト属性の取得
		String[] PERIOD = meetingRoom.getPeriod();
		RoomBean[] confrenceRoom = meetingRoom.getRooms();
		ReservationBean[][] rbs = meetingRoom.getReservations();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//表の1行目
		out.print("<tr class='first'>");
		out.print("<td class='calum'>" + "会議室名\\時間帯" + "</td>");
		for (int i = 0; i < PERIOD.length; i++) {
			out.print("<td class='calsize'>" + PERIOD[i] + "</td>");
		}
		out.print("</tr>");
		//項目時間
		out.print("<tr>");
		for (int j = 0; j < confrenceRoom.length; j++) {
			out.print("<td class='first'>");
			out.print(confrenceRoom[j].getName());
			out.print("</td>");
			for (int i = 0; i < PERIOD.length; i++) {
				out.print("<td class='second'>");
				out.print("<form action='" + request.getContextPath() + "/CancelCreate' method='post'>");
				java.util.Date date = sdf.parse(meetingRoom.getDate() + " " + PERIOD[i]);
				if (date.before(cal.getTime())) {
					// 日付が前のタグ
					out.print("<input class='ncolor' type='submit' value='    ' disabled>");
				} else {
					if (rbs[j][i] == null) {
						// 予約がない場合のタグ
						out.print("<input class='bcolor' type='submit' value='    ' disabled>");
					} else {
						if (rbs[j][i].getUserId().equals(meetingRoom.getUser().getId())) {
							// ログインしているユーザーのタグ
							out.print("<input class='rcolor re' type='submit' value='    '>");
						} else {
							out.print("<input class='bcolor' type='submit' value='    ' disabled>");
						}
					}
				}
				out.print("<input type='hidden' name='time' value='" + PERIOD[i] + "'>");
				out.print("<input type='hidden' name='roomId' value='" + confrenceRoom[j].getId() + "'>");
				out.print("</form>");
				out.print("</td>");
			}
			out.print("</tr>");
		}
		%>
	</table>
	<hr>
	<form action="<%=request.getContextPath()%>/menu.jsp" method="post">
		<input class="cursor" type="submit" value="戻る" name="">
	</form>
</body>
</html>