

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
@WebServlet("/SessionManagement")
public abstract class SessionManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @param request セッション情報取得
	 * @return boolean ログイン情報を元にログインしている人を判別
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean loginManagement(HttpServletRequest request)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		MeetingRoom mr = (MeetingRoom) session.getAttribute("meetingRoom");
		if (mr != null && mr.getUser().getId().equals((String) session.getAttribute("userId"))) {
			session.setMaxInactiveInterval(3600); // 3600秒(1時間)
			return true;
		} else {
			return false;
		}
	}

}
